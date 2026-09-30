package JavaAPI_DUC_Company1.seeder;

import JavaAPI_DUC_Company1.model.PositionCategory;
import JavaAPI_DUC_Company1.repository.PositionCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PositionCategorySeeder {
    @Bean
    CommandLineRunner seedPositionCategories(PositionCategoryRepository repo) {
        return args -> {

            // Avoid duplicate insert
            if (repo.count() == 0) {
                String[] englishNames={"Employee","Leader"};
                String[] vietnameseNames={"Nhân viên","Lãnh đạo" };
                for (int i = 0; i <englishNames.length ; i++) {
                    repo.save(new PositionCategory(englishNames[i],vietnameseNames[i]));
                }
                System.out.println("PositionCategory data was inserted!");
            }
        };
    }
}



