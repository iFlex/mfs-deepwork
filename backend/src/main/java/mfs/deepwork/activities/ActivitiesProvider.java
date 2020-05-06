package mfs.deepwork.activities;

import com.fasterxml.jackson.databind.ObjectMapper;
import mfs.deepwork.db.reporting.Activity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ActivitiesProvider {

    private static class BaseActivities {
        private List<Activity> activities;

        public List<Activity> getActivities() {
            return activities;
        }

        public void setActivities(List<Activity> activities) {
            this.activities = activities;
        }
    }

    @Value("${activities}")
    private String pathToActivityConfigFile;

    private Map<String, Map<String, Activity>> perUserActivityProvider = new HashMap<>();
    private BaseActivities baseActivities;

    @PostConstruct
    public void init() throws IOException {
        //read json file data to String
        byte[] jsonData = Files.readAllBytes(Paths.get(pathToActivityConfigFile));
        ObjectMapper objectMapper = new ObjectMapper();
        baseActivities = objectMapper.readValue(jsonData, BaseActivities.class);
    }

    public Map<String, Activity> getActivityesByUser(String userId){
        if(perUserActivityProvider.containsKey(userId)){
            return perUserActivityProvider.get(userId);
        }

        Map<String, Activity> userActivities = new HashMap<String, Activity>();
        perUserActivityProvider.put(userId, userActivities);
        List<Activity> base = baseActivities.getActivities();

        for(Activity activity: base){
            userActivities.put(activity.getId(), activity.clone());
        }

        return userActivities;
    }
}
