package com.example.springbootcrudapp.service;

import com.example.springbootcrudapp.dto.BookDTO;
import com.example.springbootcrudapp.entity.Book;

import java.util.List;
import java.util.Map;

public interface BookService {

    List<BookDTO> getAllBooks();

    BookDTO getBookById(Long id);

    BookDTO createBook(BookDTO bookDTO);

    BookDTO updateBook(Long id, BookDTO bookDTO);

    boolean deleteBook(Long id);

    List<BookDTO> getBooksByWriterId(Long WriterId);

    List<BookDTO> getBooksByWriterLastName(String lastName);

    // Get books with Writers using inner join
    List<Map<String, Object>> getBooksWithWriters();

    // Convert entities to DTOs and vice versa
    BookDTO convertToDTO(Book book);

    Book convertToEntity(BookDTO bookDTO);
}