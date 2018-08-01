package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class JdbcTimeEntryRepository implements TimeEntryRepository {
    private JdbcTemplate jdbcTemplate;
    public JdbcTimeEntryRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry t) {
        KeyHolder key = new GeneratedKeyHolder();
        String sqlStr = "Insert into time_entries (project_id, user_id, date,hours) " +
                        " values (?,?,?,?)";

        jdbcTemplate.update( connection -> {
                    PreparedStatement statement = connection.prepareStatement(sqlStr, RETURN_GENERATED_KEYS);
                 statement.setLong(1,t.getProjectId());
                 statement.setLong(2,t.getUserId());
                 statement.setDate(3, Date.valueOf(t.getDate()));
                 statement.setInt(4,t.getHours());
                 return statement;
                },key
        );

        return (find(key.getKey().longValue()));
    }

    @Override
    public TimeEntry find(Long l) {

        Map<String, Object> foundEntry;

        try {
            foundEntry = jdbcTemplate.queryForMap("Select * from time_entries where id = ?", l);
        } catch (Exception e) {
            return null;
        }

        return mapResult(foundEntry);
    }

    @Override
    public List<TimeEntry> list() {
        List<Map<String, Object>> foundEntries;
        List<TimeEntry> list = new ArrayList<>();

        foundEntries = jdbcTemplate.queryForList("SELECT * FROM time_entries");
        for( Map foundEntry: foundEntries) {
            list.add(mapResult(foundEntry));
        }
        return list;
    }

    @Override
    public TimeEntry update(Long l, TimeEntry t) {
        //KeyHolder key = new GeneratedKeyHolder();
        String sqlStr = "UPDATE time_entries set project_id = ?," +
                        " user_id = ?, date = ? ,hours = ? WHERE id = ?";


        jdbcTemplate.update( connection -> {
                    PreparedStatement statement = connection.prepareStatement(sqlStr);
                    statement.setLong(1,t.getProjectId());
                    statement.setLong(2,t.getUserId());
                    statement.setDate(3, Date.valueOf(t.getDate()));
                    statement.setInt(4,t.getHours());
                    statement.setLong(5, l);
                    return statement;
                }
        );

        return (find(l));
    }

    @Override
    public void delete(Long l) {
        //KeyHolder key = new GeneratedKeyHolder();
        String sqlStr = "DELETE FROM time_entries WHERE id = ?";


        jdbcTemplate.update( connection -> {
                    PreparedStatement statement = connection.prepareStatement(sqlStr);
                    statement.setLong(1,l);
                    return statement;
                }
        );
    }

    private TimeEntry mapResult(Map<String, Object> m) {
        TimeEntry t = new TimeEntry();

        t.setId((long)m.get("id"));
        t.setProjectId((long)m.get("project_id"));
        t.setUserId((long)m.get("user_id"));
        t.setDate(((Date)m.get("date")).toLocalDate());
        t.setHours((int)m.get("hours"));

        return t;
    }
}
