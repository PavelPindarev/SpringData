package com.example.springdataintroexercise.repositories;

import com.example.springdataintroexercise.models.Author;
import com.example.springdataintroexercise.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findDistinctByBooksReleaseDateBefore(LocalDate year);





    List<Author> findByFirstNameAndLastName(String firstName, String lastName);


}
