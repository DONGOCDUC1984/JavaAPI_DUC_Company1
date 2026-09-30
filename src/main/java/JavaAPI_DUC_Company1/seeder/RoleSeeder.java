package JavaAPI_DUC_Company1.seeder;

import JavaAPI_DUC_Company1.model.auth.Role;
import JavaAPI_DUC_Company1.repository.auth.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleSeeder {
    @Bean
    CommandLineRunner seedRoles(RoleRepository repo) {
        return args -> {

            // Avoid duplicate insert
            if (repo.count() == 0) {
                repo.save(new Role("ROLE_ADMIN"));
                repo.save(new Role("ROLE_USER"));
                System.out.println("Role data was inserted!");
            }
        };
    }
}
