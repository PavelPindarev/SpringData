package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }


    @Override
    public List<String> _1_BooksTitlesByAgeRestriction(String ageRestriction) {
        AgeRestriction restriction = AgeRestriction.valueOf(ageRestriction.toUpperCase());

        return bookRepository.findBooksByAgeRestriction(restriction)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> _2_GoldenBooks() {
        return bookRepository.findBookByEditionTypeEqualsAndCopiesLessThan(EditionType.GOLD, 5000)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> _3_BooksByPrice(BigDecimal lowerThan, BigDecimal higherThan) {
        return bookRepository.findBooksByPriceLessThanOrPriceGreaterThan(lowerThan, higherThan)
                .stream()
                .map(book -> book.getTitle() + " - $" + book.getPrice())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> _4_NotReleasedBooks(int year) {
        LocalDate date = LocalDate.of(year, 1, 1);
        return bookRepository.findBooksByReleaseDateIsNot(date)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> _5_BooksReleasedBeforeDate(String notFormattedDate) {
        //12-04-1992
        String[] tokens = notFormattedDate.split("-");
        int year = Integer.parseInt(tokens[2]);
        int month = Integer.parseInt(tokens[1]);
        int day = Integer.parseInt(tokens[0]);
        LocalDate date = LocalDate.of(year, month, day);

        return bookRepository.findAllByReleaseDateBefore(date)
                .stream()
                .map(b -> b.getTitle() + " " + b.getEditionType() + " " + b.getPrice())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> _7_BooksSearch(String symbols) {
        return bookRepository.findByTitleContaining(symbols)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> _8_BookTitlesSearch(String authorLastNameStartsWith) {
        return bookRepository.findByAuthorLastNameStartsWith(authorLastNameStartsWith)
                .stream()
                .map(b -> b.getTitle() + " " +
                        "(" + b.getAuthor().getFirstName() + b.getAuthor().getLastName() + ")")
                .collect(Collectors.toList());
    }

    @Override
    public int _9_CountBooks(int symbolsMoreThan) {
        return bookRepository.countBooksByTitleSizeMoreThan(symbolsMoreThan);
    }

    @Override
    public String _11_ReducedBook(String title) {
        Book book = bookRepository.findBookByTitleEquals(title);
        return String.format("%s %s %s %.2f",
                book.getTitle(),
                book.getEditionType(),
                book.getAgeRestriction(),
                book.getPrice()
        );

    }


    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
        return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }


    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
