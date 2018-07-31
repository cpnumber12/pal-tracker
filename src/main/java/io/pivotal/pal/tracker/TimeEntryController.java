package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    //@RequestMapping(method = POST)
    @PostMapping()
    public ResponseEntity create(@RequestBody TimeEntry t) {
        TimeEntry te = timeEntryRepository.create(t);
        return ResponseEntity.status(HttpStatus.CREATED).body(te);
    }

    //@RequestMapping(method = GET, value="/{id}")
    @GetMapping("/{id}")
    public ResponseEntity read(@PathVariable("id") Long l) {
        TimeEntry te = timeEntryRepository.find(l);
        if (te == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(te);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(te);
        }
    }

    //@RequestMapping(method = GET)
    @GetMapping()
    public ResponseEntity list() {
        List l = timeEntryRepository.list();
        return ResponseEntity.status(HttpStatus.OK).body(l);
    }

    //@RequestMapping(method=PUT, value="/{id}")
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long l, @RequestBody TimeEntry t) {
        TimeEntry te = timeEntryRepository.update(l,t);
        if (te == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(te);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(te);
        }
    }

    //@RequestMapping(method=DELETE, value="/{id}")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long l) {
        timeEntryRepository.delete(l);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
