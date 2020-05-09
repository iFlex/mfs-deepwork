package mfs.deepwork.activities;

import com.fasterxml.jackson.databind.ObjectMapper;
import mfs.deepwork.db.reporting.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ActivitiesProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiesProvider.class);
    private LRUCache<String, BaseActivities> cache = new LRUCache(10);

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

    public BaseActivities loadFromFile(String userId) throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get(pathToActivityConfigFile + "/" + userId));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonData, BaseActivities.class);
    }

    public BaseActivities fetchActivities(String userId){
        BaseActivities ba = cache.get(userId);
        if(ba == null) {
            try {
                return loadFromFile(userId);
            } catch(IOException e){
                return null;
            }
        }
        return ba;
    }

    public Map<String, Activity> getActivityesByUser(String userId){
        Map<String, Activity> userActivities = new HashMap<>();
        BaseActivities baseActivities = fetchActivities(userId);

        if(baseActivities == null) {
            return userActivities;
        }

        List<Activity> base = baseActivities.getActivities();
        for(Activity activity: base){
            userActivities.put(activity.getId(), activity.clone());
        }
        return userActivities;
    }
}
