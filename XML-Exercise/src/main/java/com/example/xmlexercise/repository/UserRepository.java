package com.example.xmlexercise.repository;

import com.example.xmlexercise.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("""
            SELECT u FROM users as u
            WHERE
            (SELECT COUNT(p)
            FROM products as p
            WHERE p.seller = u AND p.buyer IS NOT NULL) > 0
            ORDER BY u.lastName, u.firstName
                      """)
    List<User> findUsersWithSoldProductsAndProductsWithBuyer();


}
