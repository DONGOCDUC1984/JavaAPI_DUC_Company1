package JavaAPI_DUC_Company1.seeder;

import JavaAPI_DUC_Company1.model.*;
import JavaAPI_DUC_Company1.repository.PositionRepository;
import JavaAPI_DUC_Company1.repository.ProvinceCityRepository;
import JavaAPI_DUC_Company1.repository.StaffRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class LeaderSeeder implements CommandLineRunner {
    private final StaffRepository staffRepository;
    private final PositionRepository positionRepository;
    private final ProvinceCityRepository provinceCityRepository;

    public LeaderSeeder(
            StaffRepository staffRepository,
            PositionRepository positionRepository,
            ProvinceCityRepository provinceCityRepository) {

        this.staffRepository = staffRepository;
        this.positionRepository = positionRepository;
        this.provinceCityRepository = provinceCityRepository;
    }

    @Override
    public void run(String... args) {

        /*
         * Equivalent to:
         *
         * if (await context.Staffs
         *     .Where(x => x.Position.PositionCategory.Id == 2)
         *     .AnyAsync())
         *     return;
         */

        if (staffRepository.existsByPosition_PositionCategoryId(2)) {
            System.out.println(
                    "Leader staff already seeded. Skipping."
            );
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

        String[] middleNames = {
                "Minh",
                "Ngoc",
                "Thanh",
                "Van",
                "Xuan",""
        };

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
         * Load all ProvinceCity records.
         */
        List<ProvinceCity> provinceCities = provinceCityRepository.findAll();

        if (provinceCities.isEmpty()) {
            throw new RuntimeException(
                    "No ProvinceCity records found."
            );
        }

        /*
         * Load all leader positions.
         *
         * PositionCategory.Id = 2
         */
        boolean ExistLeaders = positionRepository.existsByPositionCategoryId(2);

        if (!ExistLeaders) {
            System.out.println("No leader positions found. " + "LeaderSeeder skipped."
            );
            return;
        }

        /*
         * Create one Staff for each leader position.
         */
        List<Staff> staffList = new ArrayList<>();
        List<Position> positions = positionRepository.findByPositionCategoryId(2);
        for (Position position : positions) {

            String firstName =
                    firstNames[random.nextInt(firstNames.length)];

            String middleName =
                    middleNames[random.nextInt(middleNames.length)];

            String lastName =
                    lastNames[random.nextInt(lastNames.length)];

            String street =
                    streets[random.nextInt(streets.length)];

            String address = random.nextInt(1000) + " " + street;

            ProvinceCity provinceCity =
                    provinceCities.get(random.nextInt(provinceCities.size()));

            Department department = position.getDepartment();

            Staff staff = new Staff();

            staff.setFirstName(firstName);
            staff.setMiddleName(middleName);
            staff.setLastName(lastName);
            staff.setAddress(address);
            staff.setTel("0"+ (random.nextInt(9000000)+1000000));

            staff.setEmail(firstName + middleName + lastName
                    + random.nextInt(2000) + 1 + "@example.com");

            /*
             * C#:
             *
             * Salary = random.Next(30, 61)
             *
             * Java nextInt(31) gives 0-30,
             * therefore +30 gives 30-60.
             */
            staff.setSalary(random.nextInt(31) + 30);

            staff.setCitizenIDNumber(
                    String.valueOf(random.nextInt(90000000) + 10000000)
            );

            staff.setGender(genders[random.nextInt(genders.length)]
            );

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
         * Equivalent to the C#:
         *
         * const int batchSize = 5000;
         */
        final int batchSize = 5000;

        for (int i = 0;
             i < staffList.size();
             i += batchSize) {

            int end =
                    Math.min(i + batchSize, staffList.size());
            List<Staff> batch = staffList.subList(i, end);
            staffRepository.saveAll(batch);
        }

        System.out.println("Leader staff seeded successfully. Total = "
                        + staffList.size());
    }


    /*
     * Equivalent to:
     *
     * RandomDay(random, 2020)
     *
     * C# starts at:
     * January 1, 2020
     *
     * and chooses a random day until today.
     */
    private LocalDate randomHireDate(Random random, int year) {

        LocalDate start = LocalDate.of(year, 1, 1);

        long range = java.time.temporal.ChronoUnit.DAYS.between(start, LocalDate.now());

        return start.plusDays(random.nextLong(range)
        );
    }


    /*
     * Equivalent to:
     *
     * RandomBirthDay(random)
     *
     * Range:
     * 1970-01-01
     * through
     * 1979-12-31
     */
    private LocalDate randomBirthDate(
            Random random) {

        LocalDate start =
                LocalDate.of(1970, 1, 1);

        LocalDate end =
                LocalDate.of(1980, 1, 1);

        long range =
                java.time.temporal.ChronoUnit.DAYS.between(start, end);

        return start.plusDays(
                random.nextLong(range)
        );
    }
}

