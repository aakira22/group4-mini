package grp4.gcash.mini.activityservice.repository;

import grp4.gcash.mini.activityservice.model.Activity;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
}
