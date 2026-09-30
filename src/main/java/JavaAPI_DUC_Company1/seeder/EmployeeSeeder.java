package JavaAPI_DUC_Company1.seeder;

import JavaAPI_DUC_Company1.model.*;
import JavaAPI_DUC_Company1.repository.PositionRepository;
import JavaAPI_DUC_Company1.repository.ProvinceCityRepository;
import JavaAPI_DUC_Company1.repository.StaffRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// It takes nearly 2.5 minutes to seed 100500 staffs (employees) into the database.
@Component
public class EmployeeSeeder implements CommandLineRunner {
    private final StaffRepository staffRepository;
    private final PositionRepository positionRepository;
    private final ProvinceCityRepository provinceCityRepository;

    public EmployeeSeeder(
            StaffRepository staffRepository,
            PositionRepository positionRepository,
            ProvinceCityRepository provinceCityRepository) {
        this.staffRepository = staffRepository;
        this.positionRepository = positionRepository;
        this.provinceCityRepository = provinceCityRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        //        To count time
        long t1 = System.currentTimeMillis();
        /*
         * Equivalent to:
         *
         * if (await context.Staffs
         *     .Where(x => x.Position.PositionCategory.Id == 1)
         *     .AnyAsync())
         *     return;
         */

        if (staffRepository.existsByPosition_PositionCategoryId(1)) {
            System.out.println("Employee staff already seeded. Skipping.");
            return;
        }

        Random random = new Random();

        Gender[] genders = {Gender.Male, Gender.Female};

        String[] firstNames = {
                "An", "Anh", "Bao", "Bich", "Chi", "Chau",
                "Dung", "Duy", "Dat", "Duc", "Giang", "Ha",
                "Hai", "Han", "Hieu", "Hoa", "Hung", "Huong",
                "Huy", "Huyen", "Khanh", "Khang", "Kien", "Lan",
                "Linh", "Loan", "Long", "Mai", "Minh", "My",
                "Nam", "Nga", "Ngan", "Ngoc", "Nghi", "Nghia",
                "Nhi", "Nhien", "Nhat", "Phong", "Phuc", "Phuong",
                "Quan", "Quang", "Quyen", "Son", "Thai", "Thao",
                "Thang", "Thinh", "Thu", "Thuy", "Tien", "Trang",
                "Trinh", "Trung", "Tu", "Tung", "Tuyet", "Vy",
                "Yen"
        };

        String[] middleNames = {"Minh", "Ngoc", "Thanh", "Van", "Xuan",""};

        String[] lastNames = {
                "Bui", "Cao", "Chu", "Dang", "Dinh", "Do",
                "Ho", "Hoang", "Huynh", "Lam", "Le", "Ly",
                "Luu", "Ngo", "Nguyen", "Phan", "Pham", "Ta",
                "Ton", "Tran", "Trinh", "Truong", "Vu",
                "Vuong", "Vo"
        };

        String[] streets = {
                "Au Co", "Ba Trieu","Bach Dang","Bui Thi Xuan", "Cao Ba Quat",
                "Da Tuong", "Duong Dinh Nghe", "Hai Ba Trung", "Hoang Dieu",
                "Hoang Hoa Tham", "Hoang Van Thu","Le Hoan", "Le Lai",
                "Le Loi", "Le Thanh Ton", "Lac Long Quan","Ly Nam De",
                "Ly Thuong Kiet", "Mac Dinh Chi","Mai Hac De", "Ngo Quyen",
                "Nguyen Hue", "Nguyen Binh Khiem", "Nguyen Dinh Chieu",
                "Nguyen Trai", "Pasteur", "Pham Ngu Lao", "Phan Boi Chau",
                "Phan Chau Trinh", "Quang Trung", "Thi Sach", "Tran Binh Trong",
                "Tran Hung Dao", "Tran Quang Dieu", "Tran Nguyen Han",
                "Tran Nhan Tong" , "Tran Quoc Toan", "Tran Thu Do",
                "Trieu Viet Vuong" , "Truong Dinh","Yet Kieu"
        };

        /*
         * Load ProvinceCity records once.
         */
        List<ProvinceCity> provinceCities = provinceCityRepository.findAll();

        if (provinceCities.isEmpty()) {
            throw new RuntimeException("No ProvinceCity records found.");
        }

        /*
         * Load employee positions.
         *
         * PositionCategory.id = 1
         */
        List<Position> positions = positionRepository.findByPositionCategoryId(1);

        if (positions.isEmpty())
        {
            throw new RuntimeException("No employee positions found. EmployeeSeeder skipped.");
        }

        System.out.println("Employee positions found: " + positions.size()
        );

        /*
         * Create 100,500 employees.
         */
        final int totalEmployees = 100500;

        List<Staff> staffList = new ArrayList<>(totalEmployees);

        for (int i = 0; i < totalEmployees; i++) {
            String firstName = firstNames[random.nextInt(firstNames.length)];
            String middleName = middleNames[random.nextInt(middleNames.length)];
            String lastName = lastNames[random.nextInt(lastNames.length)];
            String street = streets[random.nextInt(streets.length)];
            String address = random.nextInt(1000) + " " + street;
            ProvinceCity provinceCity = provinceCities.get(random.nextInt(provinceCities.size()));

            /*
             * Choose a random employee position.
             */
            Position position = positions.get(random.nextInt(positions.size()));

            /*
             * The department comes from the selected position.
             */
            Department department = position.getDepartment();

            Staff staff = new Staff();

            staff.setFirstName(firstName);
            staff.setMiddleName(middleName);
            staff.setLastName(lastName);
            staff.setAddress(address);

            staff.setTel("0" + (random.nextInt(9000000) + 1000000));

            staff.setEmail(firstName + middleName + lastName
                            + (random.nextInt(2000) + 1)
                            + "@example.com"
            );

            /*
             * C#:
             *
             * Salary = random.Next(10, 31)
             *
             * C# generates 10 - 30.
             *
             * Java:
             * random.nextInt(21) + 10
             * also generates 10 - 30.
             */
            staff.setSalary(random.nextInt(21) + 10);

            /*
             * 8-digit Citizen ID.
             */
            staff.setCitizenIDNumber(
                    String.valueOf(random.nextInt(90000000) + 10000000));

            /*
             * Gender enum.
             */
            staff.setGender(genders[random.nextInt(genders.length)]);
            staff.setProvinceCity(provinceCity);
            staff.setDepartment(department);
            staff.setPosition(position);
            staff.setHireDate(randomHireDate(random, 2020));
            staff.setBirthDate(randomBirthDate(random));
            staffList.add(staff);
        }

        /*
         * Bulk insert in chunks.
         *
         * 100,500 employees
         * divided into batches of 5,000.
         */
        final int batchSize = 5000;

        for (int i = 0; i < staffList.size(); i += batchSize) {
            int end = Math.min(i + batchSize, staffList.size());
            List<Staff> batch = staffList.subList(i, end);
            staffRepository.saveAll(batch);

            /*
             * Helpful for monitoring the seeding process.
             */
            System.out.println("Inserted employees: " + end + " / " + staffList.size()
            );
        }

        //        To count time
        long t2 = System.currentTimeMillis();
        System.out.println("It takes " +(t2-t1) + " milliseconds to seed "
                + staffList.size() +" employees successfully" );
    }

    /*
     * Equivalent to:
     *
     * RandomDay(random, 2020)
     *
     * From:
     * 2020-01-01
     *
     * Until:
     * today
     */
    private LocalDate randomHireDate(Random random, int year)
    {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate today = LocalDate.now();
        long range = ChronoUnit.DAYS.between(start, today);
        return start.plusDays(random.nextLong(range));
    }

    /*
     * Equivalent to:
     *
     * RandomBirthDay(random)
     *
     * From:
     * 1980-01-01
     *
     * Until:
     * 2002-12-31
     *
     * because the C# code uses:
     *
     * new DateTime(2003, 1, 1)
     */
    private LocalDate randomBirthDate(Random random) {
        LocalDate start = LocalDate.of(1980, 1, 1);
        LocalDate end = LocalDate.of(2003, 1, 1);
        long range = ChronoUnit.DAYS.between(start, end);
        return start.plusDays(random.nextLong(range)
        );
    }
}
