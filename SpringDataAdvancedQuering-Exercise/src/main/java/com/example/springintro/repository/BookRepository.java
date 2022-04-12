package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findBooksByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findBookByEditionTypeEqualsAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findBooksByPriceLessThanOrPriceGreaterThan(BigDecimal lowerThan, BigDecimal higherThan);

    List<Book> findBooksByReleaseDateIsNot(LocalDate date);

    List<Book> findBooksByReleaseDateBefore(LocalDate date);

    List<Book> findByTitleContaining(String symbols);

    @Query("SELECT b FROM Book as b" +
            " JOIN b.author as a" +
            " WHERE a.lastName LIKE :symbols% ")
    List<Book> findByAuthorLastNameStartsWith(String symbols);

    @Query("SELECT COUNT(b) FROM Book b" +
            " WHERE length(b.title) > :symbolsMoreThan")
    int countBooksByTitleSizeMoreThan(int symbolsMoreThan);

    Book findBookByTitleEquals(String title);
}
