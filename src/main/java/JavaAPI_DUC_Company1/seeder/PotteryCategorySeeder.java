package JavaAPI_DUC_Company1.seeder;

import JavaAPI_DUC_Company1.model.PotteryCategory;
import JavaAPI_DUC_Company1.repository.PotteryCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PotteryCategorySeeder {
    @Bean
    CommandLineRunner seedPotteryCategories(PotteryCategoryRepository repo) {
        return args -> {

            // Avoid duplicate insert
            if (repo.count() == 0) {
                String[] names={"Vase","Bowl","Dish","Ashtray" ,"Mug"  };
                for (int i = 0; i <names.length ; i++) {
                    repo.save(new PotteryCategory(names[i]));
                }
                System.out.println("PotteryCategory data was inserted!");
            }
        };
    }
}


