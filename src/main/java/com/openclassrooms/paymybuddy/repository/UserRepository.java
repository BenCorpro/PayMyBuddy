package com.openclassrooms.paymybuddy.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.openclassrooms.paymybuddy.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  public Page<User> findByEmailContains(String email, Pageable pageable);
  
  public Optional<User> findByEmail(String email);
  
  @Query("SELECT CASE WHEN COUNT(user) > 0 THEN true ELSE false END FROM User user WHERE EXISTS (SELECT user FROM User user WHERE user.email= :email)")
  public boolean existsByEmail(@Param("email") String email);
  
  public void deleteByEmail(String email);
}
