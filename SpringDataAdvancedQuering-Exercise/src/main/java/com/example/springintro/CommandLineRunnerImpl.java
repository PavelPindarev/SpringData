package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.management.InvalidAttributeValueException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter which exercise you want to check? ");
        int exerciseToCheck = 0;
        try {
            exerciseToCheck = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Exercise must be number(integer value)!");
        }
        switch (exerciseToCheck) {
            case 1:
                bookService._1_BooksTitlesByAgeRestriction(scanner.nextLine())
                        .forEach(System.out::println);
                break;
            case 2:
                bookService._2_GoldenBooks().forEach(System.out::println);
                break;
            case 3:
                bookService._3_BooksByPrice(BigDecimal.valueOf(Integer.parseInt(scanner.nextLine())),
                                BigDecimal.valueOf(Integer.parseInt(scanner.nextLine())))
                        .forEach(System.out::println);
                break;
            case 4:
                bookService._4_NotReleasedBooks(Integer.parseInt(scanner.nextLine()))
                        .forEach(System.out::println);
                break;
            case 5:
                bookService._5_BooksReleasedBeforeDate(scanner.nextLine())
                        .forEach(System.out::println);
                break;
            case 6:
                authorService._6_AuthorsSearch(scanner.nextLine())
                        .forEach(System.out::println);
                break;
            case 7:
                bookService._7_BooksSearch(scanner.nextLine())
                        .forEach(System.out::println);
                break;
            case 8:
                bookService._8_BookTitlesSearch(scanner.nextLine())
                        .forEach(System.out::println);
                break;
            case 9:
                System.out.println(bookService._9_CountBooks(Integer.parseInt(scanner.nextLine())));
                break;
            case 10:
                System.out.println(bookService._11_ReducedBook(scanner.nextLine()));
                break;
            case 11:
                System.out.println(authorService._10_TotalBookCopies(scanner.nextLine(), scanner.nextLine()));
                break;
            default:
                throw new Exception("Entered number should be positive!");
        }


    }
//        seedData();

    private void printAllBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
