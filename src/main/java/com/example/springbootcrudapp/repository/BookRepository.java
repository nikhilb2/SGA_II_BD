package com.example.springbootcrudapp.repository;

import com.example.springbootcrudapp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Find books by title containing a keyword
    List<Book> findByTitleContaining(String keyword);

    // Find books by genre
    List<Book> findByGenre(String genre);

    // Find books by Writer id
    List<Book> findByWriterId(Long WriterId);

    // Custom query to perform inner join between Book and Writer
    @Query("SELECT b FROM Book b INNER JOIN b.Writer a WHERE a.lastName = ?1")
    List<Book> findBooksByWriterLastName(String lastName);

    // Get books and Writers together (inner join)
    @Query("SELECT b, a FROM Book b INNER JOIN b.Writer a")
    List<Object[]> findBooksWithWriters();
}