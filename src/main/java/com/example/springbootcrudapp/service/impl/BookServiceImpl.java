package com.example.springbootcrudapp.service.impl;

import com.example.springbootcrudapp.dto.BookDTO;
import com.example.springbootcrudapp.entity.Writer;
import com.example.springbootcrudapp.entity.Book;
import com.example.springbootcrudapp.repository.WriterRepository;
import com.example.springbootcrudapp.repository.BookRepository;
import com.example.springbootcrudapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final WriterRepository WriterRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, WriterRepository WriterRepository) {
        this.bookRepository = bookRepository;
        this.WriterRepository = WriterRepository;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + id));
        return convertToDTO(book);
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        return convertToDTO(savedBook);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ID: " + id));

        Writer Writer = WriterRepository.findById(bookDTO.getWriterId())
                .orElseThrow(() -> new EntityNotFoundException("Writer not found with ID: " + bookDTO.getWriterId()));

        // Update fields
        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setIsbn(bookDTO.getIsbn());
        existingBook.setPublicationDate(bookDTO.getPublicationDate());
        existingBook.setPrice(bookDTO.getPrice());
        existingBook.setGenre(bookDTO.getGenre());
        existingBook.setDescription(bookDTO.getDescription());
        existingBook.setWriter(Writer);

        Book updatedBook = bookRepository.save(existingBook);
        return convertToDTO(updatedBook);
    }

    @Override
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<BookDTO> getBooksByWriterId(Long WriterId) {
        return bookRepository.findByWriterId(WriterId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBooksByWriterLastName(String lastName) {
        return bookRepository.findBooksByWriterLastName(lastName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getBooksWithWriters() {
        List<Object[]> results = bookRepository.findBooksWithWriters();
        List<Map<String, Object>> booksWithWriters = new ArrayList<>();

        for (Object[] result : results) {
            Book book = (Book) result[0];
            Writer Writer = (Writer) result[1];

            Map<String, Object> bookWithWriter = new HashMap<>();
            bookWithWriter.put("bookId", book.getId());
            bookWithWriter.put("bookTitle", book.getTitle());
            bookWithWriter.put("isbn", book.getIsbn());
            bookWithWriter.put("genre", book.getGenre());
            bookWithWriter.put("WriterId", Writer.getId());
            bookWithWriter.put("WriterName", Writer.getFirstName() + " " + Writer.getLastName());

            booksWithWriters.add(bookWithWriter);
        }

        return booksWithWriters;
    }

    @Override
    public BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setPublicationDate(book.getPublicationDate());
        bookDTO.setPrice(book.getPrice());
        bookDTO.setGenre(book.getGenre());
        bookDTO.setDescription(book.getDescription());

        if (book.getWriter() != null) {
            bookDTO.setWriterId(book.getWriter().getId());
            bookDTO.setWriterName(book.getWriter().getFirstName() + " " + book.getWriter().getLastName());
        }

        return bookDTO;
    }

    @Override
    public Book convertToEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setIsbn(bookDTO.getIsbn());
        book.setPublicationDate(bookDTO.getPublicationDate());
        book.setPrice(bookDTO.getPrice());
        book.setGenre(bookDTO.getGenre());
        book.setDescription(bookDTO.getDescription());

        if (bookDTO.getWriterId() != null) {
            Writer Writer = WriterRepository.findById(bookDTO.getWriterId())
                    .orElseThrow(
                            () -> new EntityNotFoundException("Writer not found with ID: " + bookDTO.getWriterId()));
            book.setWriter(Writer);
        }

        return book;
    }
}