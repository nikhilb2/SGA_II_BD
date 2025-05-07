package com.example.springbootcrudapp.repository;

import com.example.springbootcrudapp.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WriterRepository extends JpaRepository<Writer, Long> {

    // Find Writers by last name
    List<Writer> findByLastName(String lastName);

    // Find Writers by email
    Writer findByEmail(String email);

    // Find Writers born after a certain year
    List<Writer> findByBirthYearGreaterThan(Integer year);
}