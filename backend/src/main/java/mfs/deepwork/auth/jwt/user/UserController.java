package mfs.deepwork.auth.jwt.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private InMemoryUserRepository inMemoryUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(@Autowired InMemoryUserRepository inMemoryUserRepository,
                          @Autowired BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.inMemoryUserRepository = inMemoryUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        inMemoryUserRepository.save(user);
    }
}

