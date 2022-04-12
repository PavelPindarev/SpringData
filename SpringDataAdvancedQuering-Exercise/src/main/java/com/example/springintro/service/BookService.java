package com.example.springintro.service;

import com.example.springintro.model.entity.Book;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> _1_BooksTitlesByAgeRestriction(String ageRestriction);

    List<String> _2_GoldenBooks();

    List<String> _3_BooksByPrice(BigDecimal lowerThan, BigDecimal higherThan);

    List<String> _4_NotReleasedBooks(int year);

    List<String> _5_BooksReleasedBeforeDate(String date);

    List<String> _7_BooksSearch(String symbols);

    List<String> _8_BookTitlesSearch(String authorLastNameStartsWith);

    int _9_CountBooks(int symbolsMoreThan);

    String _11_ReducedBook(String title);
}
