package JavaAPI_DUC_Company1.seeder;

import JavaAPI_DUC_Company1.model.Pottery;
import JavaAPI_DUC_Company1.model.PotteryCategory;
import JavaAPI_DUC_Company1.repository.PotteryCategoryRepository;
import JavaAPI_DUC_Company1.repository.PotteryRepository;
import JavaAPI_DUC_Company1.service.business.interfaces.IPotteryService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// It takes approximately 21 minutes to seed 1000500 potteries into the database.
@Component
public class PotterySeeder {

    private final PotteryRepository potteryRepository;
    private final PotteryCategoryRepository potteryCategoryRepository;
    private final IPotteryService potteryService;

    public PotterySeeder(
            PotteryRepository potteryRepository,
            PotteryCategoryRepository potteryCategoryRepository,
            IPotteryService potteryService) {

        this.potteryRepository = potteryRepository;
        this.potteryCategoryRepository = potteryCategoryRepository;
        this.potteryService = potteryService;
    }

    private static final String[] words = {
            "Accomplishment", "Incongruous", "Grateful", "Incompatible", "Hideous",
            "Deity", "Intense", "Toll", "Pristine", "Cesspool", "Sewer", "Pit",
            "Meanwhile", "Allocate", "Consortium", "Petroleum", "Creditor",
            "Debtor", "Principal", "Interest", "Labourer", "Frontier", "Outpost",
            "Inhabit", "Infidel", "Corps", "Capitulate", "Thereby", "Preliminary",
            "Incur", "Canyon", "Valley", "Cliff", "Abyss", "Sapper", "Castle",
            "Knight", "Dragon", "Sword", "Shield", "Dagger", "Whisper", "Echo",
            "Path", "Trail", "Martyr", "Glacier", "Crown", "Throne", "Temple"
    };

    private static final String[] colours = {"Blue", "Green", "Red"};
//    For a large seeder,@EventListener(ApplicationReadyEvent.class) should be utilized
//   instead of @PostConstruct
    @EventListener(ApplicationReadyEvent.class)
    public void seedPotteries() {
        if (potteryRepository.count() > 0) {
            System.out.println("Pottery table is not empty. Seeding skipped.");
            return;
        }

        List<PotteryCategory> categories = potteryCategoryRepository.findAll();

        if (categories.isEmpty()) {
            System.out.println("No PotteryCategory found.");
            return;
        }

        Random random = new Random();
        int totalPotteries = 1000500;
        int batchSize = 5000;
        long start = System.currentTimeMillis();
        List<Pottery> potteries = new ArrayList<>(batchSize);

        for (int i = 1; i <= totalPotteries; i++) {
            Pottery pottery = new Pottery();
            pottery.setName(randomPotteryName(random));
            pottery.setColour(colours[random.nextInt(colours.length)]);
            pottery.setIsMadeInVietnam(random.nextBoolean());

            // Random price from 1.01 to 9.99 ,with 2 digits after decimal point
            pottery.setPrice(BigDecimal.valueOf(101 + random.nextInt(899))
                    .movePointLeft(2));

            pottery.setPotteryCategory(categories.get(random.nextInt(categories.size())));
            pottery.setManufacturingDate(randomDate(random));
            potteries.add(pottery);

            if (potteries.size() == batchSize) {
                potteryService.saveBatch(potteries);
                potteries.clear();
                System.out.println(i + " potteries inserted..."
                );
            }
        }

        // Save remaining records
        if (!potteries.isEmpty()) {
            potteryService.saveBatch(potteries);
            System.out.println(totalPotteries + " potteries inserted..."
            );
        }

        long end = System.currentTimeMillis();

        System.out.println("--------------------------------");
        System.out.println("Finished.");
        System.out.println("Inserted : " + totalPotteries);
        System.out.println("Time     : " + (end - start) + " ms"
        );
        System.out.println("--------------------------------");
    }

    private String randomPotteryName(Random random) {
        int wordCount = random.nextBoolean() ? 2 : 3;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            if (i > 0) {
                sb.append(" ");
            }

            sb.append(words[random.nextInt(words.length)]);
        }

        return sb.toString();
    }

    private LocalDate randomDate(Random random) {
        int year = 2020 + random.nextInt(7);
        int month = 1 + random.nextInt(12);
        int day = 1 + random.nextInt(28);
        return LocalDate.of(year, month, day);
    }
}

