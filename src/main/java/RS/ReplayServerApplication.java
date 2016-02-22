package RS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import RS.profiles.DevProfile;
import RS.profiles.ProdProfile;

@SpringBootApplication
@Import({DevProfile.class, ProdProfile.class})
public class ReplayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReplayServerApplication.class, args);
    }
}
