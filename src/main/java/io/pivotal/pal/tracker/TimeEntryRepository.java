package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {

    public TimeEntry create(TimeEntry t);

    public TimeEntry find(Long l);

    public List<TimeEntry> list();

    public TimeEntry update(Long l, TimeEntry t );

    public void delete(Long l);
}
