package JavaAPI_DUC_Company1.seeder;

import JavaAPI_DUC_Company1.model.Department;
import JavaAPI_DUC_Company1.repository.DepartmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DepartmentSeeder {
    @Bean
    CommandLineRunner seedDepartments(DepartmentRepository repo) {
        return args -> {

            // Avoid duplicate insert
            if (repo.count() == 0) {
                String[] englishNames={"Accounting","Customer Service","Directors",
                        "Finance","Human Resources","Information Technology",
                        "Legal", "Marketing","Operations",
                        "Research & Development","Sales", "Security"
                };
                String[] vietnameseNames={"Kế toán","Dịch vụ khách hàng","Ban Giám đốc" ,
                        "Tài chính","Nhân sự","Công nghệ thông tin",
                        "Pháp chế", "Tiếp thị","Vận hành",
                        "Nghiên cứu & Phát triển","Bán hàng", "Bảo vệ"
                };
                for (int i = 0; i <englishNames.length ; i++) {
                    repo.save(new Department(englishNames[i],vietnameseNames[i]));
                }
                System.out.println("Department data was inserted!");
            }
        };
    }
}


