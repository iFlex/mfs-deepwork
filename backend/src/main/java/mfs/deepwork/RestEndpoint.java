package mfs.deepwork;

import mfs.deepwork.activities.ActivitiesProvider;
import mfs.deepwork.db.reporting.Activity;
import mfs.deepwork.db.reporting.InfluxFeeder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class RestEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestEndpoint.class);

    @Autowired
    private ActivitiesProvider activityProvider;

    @Autowired
    private InfluxFeeder influxFeeder;

    @GetMapping("/activities")
    public List<Activity> getActivities() {
        LOGGER.info("Request for /activities for user: to be continued");
        Map<String, Activity> activities =  activityProvider.getActivityesByUser(null);
        return activities.values().stream().collect(Collectors.toList());
    }

    @PostMapping("/activity/toggle/{user_id}/{activity_id}")
    public List<Activity> toggleActivity(@PathVariable(value="user_id") String userId, @PathVariable(name="activity_id") String activityId) {
        LOGGER.info("Activity toggle request " + activityId+" for user:" + userId);
        Map<String, Activity> activities =  activityProvider.getActivityesByUser(null);
        Activity activity = activities.get(activityId);

        if(activity != null){
            activity.toggle();
            if(activity.isFinished()) {
                influxFeeder.writeActivity(activity, userId);
            }
        }

        List<Activity> result = new LinkedList<>();
        result.add(activity);
        return result;
    }
}
