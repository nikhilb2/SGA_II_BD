package com.example.springbootcrudapp.repository;

import com.example.springbootcrudapp.entity.Writer;
import com.example.springbootcrudapp.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void whenFindByTitleContaining_thenReturnBook() {
        // given
        Writer Writer = new Writer();
        Writer.setFirstName("Stephen");
        Writer.setLastName("King");
        Writer.setEmail("stephen.king@example.com");
        entityManager.persist(Writer);

        Book book = new Book();
        book.setTitle("The Shining");
        book.setIsbn("1234567890");
        book.setGenre("Horror");
        book.setWriter(Writer);
        entityManager.persist(book);
        entityManager.flush();

        // when
        List<Book> found = bookRepository.findByTitleContaining("Shining");

        // then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    public void whenFindByGenre_thenReturnBooks() {
        // given
        Writer Writer = new Writer();
        Writer.setFirstName("J.R.R.");
        Writer.setLastName("Tolkien");
        Writer.setEmail("jrr.tolkien@example.com");
        entityManager.persist(Writer);

        Book book1 = new Book();
        book1.setTitle("The Hobbit");
        book1.setIsbn("1234567891");
        book1.setGenre("Fantasy");
        book1.setWriter(Writer);
        entityManager.persist(book1);

        Book book2 = new Book();
        book2.setTitle("The Lord of the Rings");
        book2.setIsbn("1234567892");
        book2.setGenre("Fantasy");
        book2.setWriter(Writer);
        entityManager.persist(book2);

        Book book3 = new Book();
        book3.setTitle("On Writing");
        book3.setIsbn("1234567893");
        book3.setGenre("Non-Fiction");
        book3.setWriter(Writer);
        entityManager.persist(book3);

        entityManager.flush();

        // when
        List<Book> found = bookRepository.findByGenre("Fantasy");

        // then
        assertThat(found).hasSize(2);
        assertThat(found.get(0).getGenre()).isEqualTo("Fantasy");
        assertThat(found.get(1).getGenre()).isEqualTo("Fantasy");
    }

    @Test
    public void whenFindByWriterId_thenReturnBooks() {
        // given
        Writer Writer1 = new Writer();
        Writer1.setFirstName("George");
        Writer1.setLastName("Orwell");
        Writer1.setEmail("george.orwell@example.com");
        entityManager.persist(Writer1);

        Writer Writer2 = new Writer();
        Writer2.setFirstName("Jane");
        Writer2.setLastName("Austen");
        Writer2.setEmail("jane.austen@example.com");
        entityManager.persist(Writer2);

        Book book1 = new Book();
        book1.setTitle("1984");
        book1.setIsbn("1234567894");
        book1.setGenre("Dystopian");
        book1.setWriter(Writer1);
        entityManager.persist(book1);

        Book book2 = new Book();
        book2.setTitle("Animal Farm");
        book2.setIsbn("1234567895");
        book2.setGenre("Political Satire");
        book2.setWriter(Writer1);
        entityManager.persist(book2);

        Book book3 = new Book();
        book3.setTitle("Pride and Prejudice");
        book3.setIsbn("1234567896");
        book3.setGenre("Romance");
        book3.setWriter(Writer2);
        entityManager.persist(book3);

        entityManager.flush();

        // when
        List<Book> found = bookRepository.findByWriterId(Writer1.getId());

        // then
        assertThat(found).hasSize(2);
        assertThat(found.get(0).getWriter().getId()).isEqualTo(Writer1.getId());
        assertThat(found.get(1).getWriter().getId()).isEqualTo(Writer1.getId());
    }

    @Test
    public void whenFindBooksByWriterLastName_thenReturnBooks() {
        // given
        Writer Writer = new Writer();
        Writer.setFirstName("Agatha");
        Writer.setLastName("Christie");
        Writer.setEmail("agatha.christie@example.com");
        entityManager.persist(Writer);

        Book book1 = new Book();
        book1.setTitle("Murder on the Orient Express");
        book1.setIsbn("1234567897");
        book1.setGenre("Mystery");
        book1.setWriter(Writer);
        entityManager.persist(book1);

        Book book2 = new Book();
        book2.setTitle("Death on the Nile");
        book2.setIsbn("1234567898");
        book2.setGenre("Mystery");
        book2.setWriter(Writer);
        entityManager.persist(book2);

        entityManager.flush();

        // when
        List<Book> found = bookRepository.findBooksByWriterLastName("Christie");

        // then
        assertThat(found).hasSize(2);
        assertThat(found.get(0).getWriter().getLastName()).isEqualTo("Christie");
        assertThat(found.get(1).getWriter().getLastName()).isEqualTo("Christie");
    }

    @Test
    public void whenFindBooksWithWriters_thenReturnBooksAndWriters() {
        // given
        Writer Writer = new Writer();
        Writer.setFirstName("Ernest");
        Writer.setLastName("Hemingway");
        Writer.setEmail("ernest.hemingway@example.com");
        entityManager.persist(Writer);

        Book book = new Book();
        book.setTitle("The Old Man and the Sea");
        book.setIsbn("1234567899");
        book.setGenre("Literary Fiction");
        book.setPublicationDate(LocalDate.of(1952, 9, 1));
        book.setPrice(new BigDecimal("10.99"));
        book.setWriter(Writer);
        entityManager.persist(book);

        entityManager.flush();

        // when
        List<Object[]> results = bookRepository.findBooksWithWriters();

        // then
        assertThat(results).isNotEmpty();

        Object[] result = results.get(0);
        Book foundBook = (Book) result[0];
        Writer foundWriter = (Writer) result[1];

        assertThat(foundBook.getTitle()).isEqualTo(book.getTitle());
        assertThat(foundWriter.getLastName()).isEqualTo(Writer.getLastName());
    }
}