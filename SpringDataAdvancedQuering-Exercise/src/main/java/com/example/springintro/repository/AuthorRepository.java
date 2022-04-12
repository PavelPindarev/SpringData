package com.example.springintro.repository;

import com.example.springintro.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a ORDER BY a.books.size DESC")
    List<Author> findAllByBooksSizeDESC();

    List<Author> findByFirstNameEndsWith(String endWith);

    @Query("SELECT SUM(b.copies) FROM Author as a" +
            " JOIN a.books as b" +
            " WHERE a.firstName = :firstName AND a.lastName = :lastName")
    int countBooksCopiesByAuthor(String firstName, String lastName);
}
