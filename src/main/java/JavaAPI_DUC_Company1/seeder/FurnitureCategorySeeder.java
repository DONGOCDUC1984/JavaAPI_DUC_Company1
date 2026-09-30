package JavaAPI_DUC_Company1.seeder;

import JavaAPI_DUC_Company1.model.FurnitureCategory;
import JavaAPI_DUC_Company1.repository.FurnitureCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FurnitureCategorySeeder {
    @Bean
    CommandLineRunner seedFurnitureCategories(FurnitureCategoryRepository repo) {
        return args -> {

            // Avoid duplicate insert
            if (repo.count() == 0) {
                String[] names=
                        {"Armchair","Bedside Table" ,"Bookshelf","Display cabinet",
                        "Dresser", "Settee","Shoe Rack" ,"Stool" ,"TV Stand", "Wardrobe"};
                for (int i = 0; i <names.length ; i++) {
                    repo.save(new FurnitureCategory(names[i]));
                }
                System.out.println("FurnitureCategory data was inserted!");
            }
        };
    }
}


