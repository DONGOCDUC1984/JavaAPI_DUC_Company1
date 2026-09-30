package JavaAPI_DUC_Company1.seeder;
import JavaAPI_DUC_Company1.model.ProductCategory;
import JavaAPI_DUC_Company1.repository.ProductCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductCategorySeeder {

    @Bean
    CommandLineRunner seedProductCategories(ProductCategoryRepository repo) {
        return args -> {

            // Avoid duplicate insert
            if (repo.count() == 0) {

                ProductCategory p1 = new ProductCategory();
                p1.setEnglishName("Fruit and vegetable");
                p1.setVietnameseName("Rau,quả");

                ProductCategory p2 = new ProductCategory();
                p2.setEnglishName("Bread and cake");
                p2.setVietnameseName("Bánh");

                ProductCategory p3 = new ProductCategory();
                p3.setEnglishName("Milk");
                p3.setVietnameseName("Sữa");

                repo.save(p1);
                repo.save(p2);
                repo.save(p3);

                System.out.println("ProductCategory data was inserted!");
            }
        };
    }
}