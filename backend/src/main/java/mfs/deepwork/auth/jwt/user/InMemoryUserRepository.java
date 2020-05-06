package mfs.deepwork.auth.jwt.user;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserRepository {
    Map<String, ApplicationUser> inMemRepo = new HashMap<>();

    public InMemoryUserRepository(){
        //temporary hardcode
        ApplicationUser user = new ApplicationUser();
        user.setUsername("Liviu");
        user.setPassword((new BCryptPasswordEncoder()).encode("savedemceieseb00s"));
        save(user);
    }
    public ApplicationUser findByUsername(String username) {
        return inMemRepo.get(username);
    }

    public void save(ApplicationUser user){
        inMemRepo.put(user.getUsername(), user);
    }
}