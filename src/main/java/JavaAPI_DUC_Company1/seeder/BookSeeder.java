package JavaAPI_DUC_Company1.seeder;

import JavaAPI_DUC_Company1.model.Book;
import JavaAPI_DUC_Company1.model.BookCategory;
import JavaAPI_DUC_Company1.repository.BookCategoryRepository;
import JavaAPI_DUC_Company1.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
// It takes approximately 2.5 minutes to seed 100500 books into the database
@Component
public class BookSeeder {

    private final BookRepository bookRepository;
    private final BookCategoryRepository bookCategoryRepository;

    public BookSeeder(
            BookRepository bookRepository,
            BookCategoryRepository bookCategoryRepository) {

        this.bookRepository = bookRepository;
        this.bookCategoryRepository = bookCategoryRepository;
    }

    private static final String[] WORDS =  {
            "Accomplishment","Incongruous","Grateful","Incompatible","Hideous","Deity","Intense","Toll","Pristine",
            "Cesspool","Sewer","Pit","Meanwhile","Allocate","Consortium","Petroleum","Creditor","Debtor","Principal",
            "Interest","Labourer","Frontier","Outpost","Inhabit","Wolf","Eagle","Lion","Tiger","Bear","Fox",
            "Canyon","Valley","Cliff","Abyss","Sapper","Castle","Knight","Dragon","Sword","Shield",
            "Dagger","Whisper","Echo","Path","Trail","Martyr","Glacier","Crown","Throne","Temple"
    };

    private static final String[] COLOURS = {
            "Blue",
            "Green",
            "Red"
    };

    @PostConstruct
    @Transactional
    public void seedBooks() {

        if (bookRepository.count() > 0)
            return;

        List<BookCategory> categories =
                bookCategoryRepository.findAll();

        if (categories.isEmpty()) {

            System.out.println("No BookCategory found.");
            return;
        }

        Random random = new Random();

        int totalBooks = 100500;

        int batchSize = 1000;

        List<Book> books =
                new ArrayList<>(batchSize);

        long start = System.currentTimeMillis();

        for (int i = 1; i <= totalBooks; i++) {

            Book book = new Book();

            book.setName(
                    randomBookName(random));

            book.setColour(
                    COLOURS[random.nextInt(COLOURS.length)]);

            book.setIsMadeInVietnam(
                    random.nextBoolean());

            book.setPublishedDay(
                    randomDate(random));

            book.setBookCategory(
                    categories.get(
                            random.nextInt(categories.size())));

            books.add(book);

            if (books.size() == batchSize) {

                bookRepository.saveAll(books);

                books.clear();

                System.out.println(
                        i + " books inserted...");
            }
        }

        if (!books.isEmpty()) {

            bookRepository.saveAll(books);
        }

        long end = System.currentTimeMillis();

        System.out.println("--------------------------------");
        System.out.println("Finished.");
        System.out.println("Inserted : " + totalBooks);
        System.out.println("Time     : " + (end - start) + " ms");
        System.out.println("--------------------------------");
    }

    private String randomBookName(Random random) {

        int wordCount =
                random.nextBoolean() ? 2 : 3;

        StringBuilder sb =
                new StringBuilder();

        for (int i = 0; i < wordCount; i++) {

            if (i > 0)
                sb.append(" ");

            sb.append(
                    WORDS[random.nextInt(WORDS.length)]);
        }

        return sb.toString();
    }

    private LocalDate randomDate(Random random) {

        int year =
                2020 + random.nextInt(7);

        int month =
                1 + random.nextInt(12);

        int day =
                1 + random.nextInt(28);

        return LocalDate.of(
                year,
                month,
                day);
    }
}