package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    HashMap<Long, TimeEntry> entryMap = new HashMap<Long, TimeEntry>();
    long counter = 0l;

    @Override
    public TimeEntry create(TimeEntry t) {
        counter++;
        t.setId(counter);
        entryMap.put(t.getId(), t);
        return t;
    }

    @Override
    public TimeEntry find(Long l) {
        return entryMap.get(l);
    }

    @Override
    public List<TimeEntry> list() {
        List<TimeEntry> l = new ArrayList<>(entryMap.values());
        return l;
    }

    @Override
    public TimeEntry update(Long l, TimeEntry t) {
        if (find(l) == null) {
            return null;
        } else {
            t.setId(l);
            entryMap.put(l, t);
            return t;
        }
    }

    @Override
    public void delete(Long l) {
        if(find(l) != null) {
            entryMap.remove(l);
        }
    }
}
