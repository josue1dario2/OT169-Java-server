package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.entity.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.IActivityService;
import com.alkemy.ong.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements IActivityService {

    private final ActivityRepository repository;

    @Autowired
    public ActivityServiceImpl(ActivityRepository repository) {
        this.repository = repository;
    }

    @Override
    public ActivityDto addActivity(ActivityDto activityDto) {
        Activity activity = repository.save(Mapper.mapFromDto(activityDto, new Activity()));
        return Mapper.mapToDto(activity, activityDto);
    }

    @Override
    public ActivityDto updateActivity(String id, ActivityDto activityDto) {
        Activity updatedActivity = Mapper.mapFromDto(activityDto, repository.findById(id).get());
        repository.save(updatedActivity);
        return Mapper.mapToDto(updatedActivity, activityDto);
    }

}
