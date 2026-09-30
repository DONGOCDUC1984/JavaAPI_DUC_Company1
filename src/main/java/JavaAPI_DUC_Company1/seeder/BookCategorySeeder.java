package JavaAPI_DUC_Company1.seeder;

import JavaAPI_DUC_Company1.model.BookCategory;
import JavaAPI_DUC_Company1.repository.BookCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookCategorySeeder {
    @Bean
    CommandLineRunner seedBookCategories(BookCategoryRepository repo) {
        return args -> {

            // Avoid duplicate insert
            if (repo.count() == 0) {
                String[] names={"English","Fiction","IT","Novel" ,"Psychology"  };
                for (int i = 0; i <names.length ; i++) {
                    repo.save(new BookCategory(names[i]));
                }
                System.out.println("BookCategory data was inserted!");
            }
        };
    }
}

