package com.example.springdataintroexercise;

import com.example.springdataintroexercise.models.Author;
import com.example.springdataintroexercise.models.Book;
import com.example.springdataintroexercise.repositories.AuthorRepository;
import com.example.springdataintroexercise.repositories.BookRepository;
import com.example.springdataintroexercise.services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {

    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        this.seedService.seedAuthors();
//        this.seedService.seedCategories();
//        this.seedService.seedAll();

//        this._01_booksAfter2000();
//        this._02_allAuthorsWithBookBefore1990();
        this._03_allAuthorsOrderedByBookCount();

    }

    private final SeedService seedService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public ConsoleRunner(
            SeedService seedService,
            BookRepository bookRepository,
            AuthorRepository authorRepository) {
        this.seedService = seedService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    private void _01_booksAfter2000() {
        LocalDate year2000 = LocalDate.of(2000, 12, 31);

        List<Book> books = this.bookRepository.findByReleaseDateAfter(year2000);

        books.forEach(b -> System.out.println(b.getTitle()));

        System.out.println("Total count: " + books.size());
    }

    private void _02_allAuthorsWithBookBefore1990() {
        LocalDate year1990 = LocalDate.of(1990, 1, 1);

        List<Author> authors = this.authorRepository.findDistinctByBooksReleaseDateBefore(year1990);

        authors.forEach(a -> System.out.println(a.getFirstName() + " " + a.getLastName()));
    }

    private void _03_allAuthorsOrderedByBookCount() {
        List<Author> authors = this.authorRepository.findAll();

        authors
                .stream()
                .sorted((l, r) -> r.getBooks().size() - l.getBooks().size())
                .forEach(author ->
                        System.out.printf("%s %s -> %d%n",
                                author.getFirstName(),
                                author.getLastName(),
                                author.getBooks().size()));
    }

//    private void _04_GetAllBooksFromAuthorGeorgePowellOrdered() {
//        List<Author> authors = this.authorRepository.findByFirstNameAndLastName("George","Powell");
//       authors.stream()
//               .map(Author::getBooks)
//
//    }
}
