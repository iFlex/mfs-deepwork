package mfs.deepwork.auth.jwt.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import mfs.deepwork.auth.jwt.JWTAuthorisationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileBackedUserRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileBackedUserRepository.class);

    @Value("${users.location}")
    private String pathToUserStore;


    public ApplicationUser findByUsername(String username) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(pathToUserStore + "/" + username));
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonData, ApplicationUser.class);
        } catch(Exception e){
            LOGGER.error("Failed to retrieve password for " + username, e);
            return null;
        }
    }

    public void save(ApplicationUser user){
        //ToDo
    }
}