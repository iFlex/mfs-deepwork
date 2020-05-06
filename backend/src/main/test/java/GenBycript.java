import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RunWith(BlockJUnit4ClassRunner.class)
public class GenBycript {
    @Test
    public void genBycript(){
        System.out.println("SALTED");
        System.out.println(new BCryptPasswordEncoder().encode("savedem"));
    }
}
