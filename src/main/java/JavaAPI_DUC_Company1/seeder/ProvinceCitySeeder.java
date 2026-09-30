package JavaAPI_DUC_Company1.seeder;

import JavaAPI_DUC_Company1.model.ProvinceCity;
import JavaAPI_DUC_Company1.repository.ProvinceCityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProvinceCitySeeder {
    @Bean
    CommandLineRunner seedProvinceCities(ProvinceCityRepository repo) {
        return args -> {

            // Avoid duplicate insert
            if (repo.count() == 0) {
                String[] names={"Ha Noi","Sai Gon","Hai Phong","An Giang",
                        "Bac Ninh","Ca Mau","Can Tho","Cao Bang",
                        "Da Nang","Dak Lak" };
                for (int i = 0; i <names.length ; i++) {
                    repo.save(new ProvinceCity(names[i]));
                }
                System.out.println("ProvinceCity data was inserted!");
            }
        };
    }
}
