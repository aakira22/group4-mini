package grp4.gcash.mini.activityservice.controller;

import grp4.gcash.mini.activityservice.model.Activity;
import grp4.gcash.mini.activityservice.payload.GetAllActivitiesResponse;
import grp4.gcash.mini.activityservice.payload.LogActivity;
import grp4.gcash.mini.activityservice.repository.ActivityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("activity")
public class ActivityController {

    private final ActivityRepository activityRepository;

    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void log(@Valid @RequestBody LogActivity request) {
        Activity activity = new Activity(request.getAction(), request.getData(), request.getIdentity());
        activityRepository.save(activity);
    }

    @GetMapping("all")
    public GetAllActivitiesResponse getAllActivities() {
        GetAllActivitiesResponse response = new GetAllActivitiesResponse(activityRepository.count(), new ArrayList<>());
        activityRepository.findAll().forEach(activity -> response.getActivities().add(new LogActivity(activity.getAction(), activity.getInformation(), activity.getIdentity())));
        return response;
    }
}