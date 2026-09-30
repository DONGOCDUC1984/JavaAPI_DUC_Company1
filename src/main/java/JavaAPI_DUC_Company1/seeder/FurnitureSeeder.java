package JavaAPI_DUC_Company1.seeder;

import JavaAPI_DUC_Company1.model.Furniture;
import JavaAPI_DUC_Company1.model.FurnitureCategory;
import JavaAPI_DUC_Company1.repository.FurnitureCategoryRepository;
import JavaAPI_DUC_Company1.repository.FurnitureRepository;
import JavaAPI_DUC_Company1.service.business.interfaces.IFurnitureService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// It takes approximately 21 minutes to seed 1000500 furnitures into the database.
@Component
public class FurnitureSeeder {

    private final FurnitureRepository furnitureRepository;
    private final FurnitureCategoryRepository furnitureCategoryRepository;
    private final IFurnitureService furnitureService;

    public FurnitureSeeder(
            FurnitureRepository furnitureRepository,
            FurnitureCategoryRepository furnitureCategoryRepository,
            IFurnitureService furnitureService) {
        this.furnitureRepository = furnitureRepository;
        this.furnitureCategoryRepository = furnitureCategoryRepository;
        this.furnitureService = furnitureService;
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
    public void seedfurnitures() {
        if (furnitureRepository.count() > 0) {
            System.out.println("Furniture table is not empty. Seeding skipped.");
            return;
        }

        List<FurnitureCategory> categories = furnitureCategoryRepository.findAll();

        if (categories.isEmpty()) {
            System.out.println("No FurnitureCategory found.");
            return;
        }

        Random random = new Random();
        int totalFurnitures = 1000500;
        int batchSize = 5000;
        long start = System.currentTimeMillis();
        List<Furniture> furnitures = new ArrayList<>(batchSize);

        for (int i = 1; i <= totalFurnitures; i++) {
            Furniture furniture = new Furniture();
            furniture.setName(randomFurnitureName(random));
            furniture.setColour(colours[random.nextInt(colours.length)]);
            furniture.setIsMadeInVietnam(random.nextBoolean());

            // Random price from 1.01 to 9.99 ,with 2 digits after decimal point
            furniture.setPrice(BigDecimal.valueOf(101 + random.nextInt(899))
                    .movePointLeft(2));

            furniture.setFurnitureCategory(categories
                    .get(random.nextInt(categories.size())));
            furniture.setManufacturingDate(randomDate(random));
            furnitures.add(furniture);

            if (furnitures.size() == batchSize) {
                furnitureService.saveBatch(furnitures);
                furnitures.clear();
                System.out.println(i + " furnitures inserted..."
                );
            }
        }

        // Save remaining records
        if (!furnitures.isEmpty()) {
            furnitureService.saveBatch(furnitures);
            System.out.println(totalFurnitures + " furnitures inserted..."
            );
        }

        long end = System.currentTimeMillis();

        System.out.println("--------------------------------");
        System.out.println("Finished.");
        System.out.println("Inserted : " + totalFurnitures);
        System.out.println("Time     : " + (end - start) + " ms"
        );
        System.out.println("--------------------------------");
    }

    private String randomFurnitureName(Random random) {
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


