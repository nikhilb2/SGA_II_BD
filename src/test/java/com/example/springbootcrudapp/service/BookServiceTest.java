package com.example.springbootcrudapp.service;

import com.example.springbootcrudapp.dto.BookDTO;
import com.example.springbootcrudapp.entity.Writer;
import com.example.springbootcrudapp.entity.Book;
import com.example.springbootcrudapp.repository.WriterRepository;
import com.example.springbootcrudapp.repository.BookRepository;
import com.example.springbootcrudapp.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private WriterRepository WriterRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Writer Writer;
    private Book book;
    private BookDTO bookDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        Writer = new Writer();
        Writer.setId(1L);
        Writer.setFirstName("J.K.");
        Writer.setLastName("Rowling");
        Writer.setEmail("jk.rowling@example.com");

        book = new Book();
        book.setId(1L);
        book.setTitle("Harry Potter and the Philosopher's Stone");
        book.setIsbn("9780747532743");
        book.setGenre("Fantasy");
        book.setPublicationDate(LocalDate.of(1997, 6, 26));
        book.setPrice(new BigDecimal("19.99"));
        book.setDescription("First book in the Harry Potter series.");
        book.setWriter(Writer);

        bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Harry Potter and the Philosopher's Stone");
        bookDTO.setIsbn("9780747532743");
        bookDTO.setGenre("Fantasy");
        bookDTO.setPublicationDate(LocalDate.of(1997, 6, 26));
        bookDTO.setPrice(new BigDecimal("19.99"));
        bookDTO.setDescription("First book in the Harry Potter series.");
        bookDTO.setWriterId(1L);
        bookDTO.setWriterName("J.K. Rowling");
    }

    @Test
    public void whenGetAllBooks_thenReturnBookDTOList() {
        // given
        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookRepository.findAll()).thenReturn(books);

        // when
        List<BookDTO> bookDTOs = bookService.getAllBooks();

        // then
        assertThat(bookDTOs).hasSize(1);
        assertThat(bookDTOs.get(0).getTitle()).isEqualTo(book.getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void whenGetBookById_thenReturnBookDTO() {
        // given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // when
        BookDTO found = bookService.getBookById(1L);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getTitle()).isEqualTo(book.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void whenGetBookByIdNotFound_thenThrowException() {
        // given
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            bookService.getBookById(99L);
        });
        verify(bookRepository, times(1)).findById(99L);
    }

    @Test
    public void whenCreateBook_thenReturnBookDTO() {
        // given
        when(WriterRepository.findById(1L)).thenReturn(Optional.of(Writer));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // when
        BookDTO created = bookService.createBook(bookDTO);

        // then
        assertThat(created).isNotNull();
        assertThat(created.getTitle()).isEqualTo(bookDTO.getTitle());
        verify(WriterRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void whenUpdateBook_thenReturnUpdatedBookDTO() {
        // given
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Old Title");
        existingBook.setIsbn("1234567890");
        existingBook.setWriter(Writer);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(WriterRepository.findById(1L)).thenReturn(Optional.of(Writer));
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        BookDTO updated = bookService.updateBook(1L, bookDTO);

        // then
        assertThat(updated).isNotNull();
        assertThat(updated.getTitle()).isEqualTo(bookDTO.getTitle());
        assertThat(updated.getIsbn()).isEqualTo(bookDTO.getIsbn());
        verify(bookRepository, times(1)).findById(1L);
        verify(WriterRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void whenUpdateBookNotFound_thenThrowException() {
        // given
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            bookService.updateBook(99L, bookDTO);
        });
        verify(bookRepository, times(1)).findById(99L);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    public void whenGetBooksByWriterId_thenReturnBookDTOList() {
        // given
        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookRepository.findByWriterId(1L)).thenReturn(books);

        // when
        List<BookDTO> bookDTOs = bookService.getBooksByWriterId(1L);

        // then
        assertThat(bookDTOs).hasSize(1);
        assertThat(bookDTOs.get(0).getWriterId()).isEqualTo(1L);
        verify(bookRepository, times(1)).findByWriterId(1L);
    }

    @Test
    public void whenGetBooksByWriterLastName_thenReturnBookDTOList() {
        // given
        List<Book> books = new ArrayList<>();
        books.add(book);

        when(bookRepository.findBooksByWriterLastName("Rowling")).thenReturn(books);

        // when
        List<BookDTO> bookDTOs = bookService.getBooksByWriterLastName("Rowling");

        // then
        assertThat(bookDTOs).hasSize(1);
        assertThat(bookDTOs.get(0).getWriterName()).contains("Rowling");
        verify(bookRepository, times(1)).findBooksByWriterLastName("Rowling");
    }

    @Test
    public void whenGetBooksWithWriters_thenReturnMappedResults() {
        // given
        List<Object[]> results = new ArrayList<>();
        Object[] result = new Object[] { book, Writer };
        results.add(result);

        when(bookRepository.findBooksWithWriters()).thenReturn(results);

        // when
        List<Map<String, Object>> booksWithWriters = bookService.getBooksWithWriters();

        // then
        assertThat(booksWithWriters).hasSize(1);
        assertThat(booksWithWriters.get(0).get("bookTitle")).isEqualTo(book.getTitle());
        assertThat(booksWithWriters.get(0).get("WriterName"))
                .isEqualTo(Writer.getFirstName() + " " + Writer.getLastName());
        verify(bookRepository, times(1)).findBooksWithWriters();
    }
}