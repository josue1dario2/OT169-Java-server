package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.service.impl.ActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;


@RestController
@RequestMapping("/activities")
@CrossOrigin()
@Validated
public class ActivityController {

    private final ActivityServiceImpl service;
    @Autowired
    public ActivityController(ActivityServiceImpl service){
        this.service = service;
    }

    @PostMapping
    public ActivityDto addActivity(@Valid @RequestBody ActivityDto activityDto) {
        return service.addActivity(activityDto);
    }

    @PutMapping("/{id}")
    public ActivityDto updateActivity(@PathVariable("id")String id,
                                      @Valid @RequestBody ActivityDto activityDto) throws ResponseStatusException {
        try {
            return service.updateActivity(id, activityDto);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Activity with id %s not found", id));
        }

    }
}
