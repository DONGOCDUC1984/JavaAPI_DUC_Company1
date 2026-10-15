package JavaAPI_DUC_Company1.seeder;

import JavaAPI_DUC_Company1.model.auth.Role;
import JavaAPI_DUC_Company1.repository.auth.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class RoleSeeder {
    @Bean
    @Order(8)
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
