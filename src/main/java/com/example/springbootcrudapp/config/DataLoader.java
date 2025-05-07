package com.example.springbootcrudapp.config;

import com.example.springbootcrudapp.entity.Writer;
import com.example.springbootcrudapp.entity.Book;
import com.example.springbootcrudapp.repository.WriterRepository;
import com.example.springbootcrudapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final WriterRepository writerRepository;
    private final BookRepository bookRepository;

    @Autowired
    public DataLoader(WriterRepository writerRepository, BookRepository bookRepository) {
        this.writerRepository = writerRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        // Check if the database is already populated
        if (writerRepository.count() > 0 || bookRepository.count() > 0) {
            return;
        }

        // Create and save Writers
        List<Writer> writers = createWriters();
        List<Writer> savedWriters = writerRepository.saveAll(writers);

        // Create and save books
        List<Book> books = createBooks(savedWriters);
        bookRepository.saveAll(books);

        System.out.println("Sample data loaded successfully.");
    }

    private List<Writer> createWriters() {
        List<Writer> writers = new ArrayList<>();

        // Writer 1
        Writer writer1 = new Writer();
        writer1.setFirstName("Alice");
        writer1.setLastName("Johnson");
        writer1.setEmail("alice.johnson@example.com");
        writer1.setBiography("Alice Johnson is a contemporary author known for her thrilling mystery novels.");
        writer1.setBirthYear(1980);
        writers.add(writer1);

        // Writer 2
        Writer writer2 = new Writer();
        writer2.setFirstName("Robert");
        writer2.setLastName("Smith");
        writer2.setEmail("robert.smith@example.com");
        writer2.setBiography("Robert Smith is a science fiction writer whose works explore futuristic worlds.");
        writer2.setBirthYear(1975);
        writers.add(writer2);

        // Writer 3
        Writer writer3 = new Writer();
        writer3.setFirstName("Emily");
        writer3.setLastName("Davis");
        writer3.setEmail("emily.davis@example.com");
        writer3.setBiography("Emily Davis is a celebrated romance novelist with a passion for storytelling.");
        writer3.setBirthYear(1990);
        writers.add(writer3);

        return writers;
    }

    private List<Book> createBooks(List<Writer> writers) {
        List<Book> books = new ArrayList<>();

        // Books by Alice Johnson
        Book book1 = new Book();
        book1.setTitle("The Silent Witness");
        book1.setIsbn("9781234567890");
        book1.setGenre("Mystery");
        book1.setPublicationDate(LocalDate.of(2020, 5, 15));
        book1.setPrice(new BigDecimal("15.99"));
        book1.setDescription("A gripping mystery novel that keeps readers on the edge of their seats.");
        book1.setWriter(writers.get(0));
        books.add(book1);

        // Books by Robert Smith
        Book book2 = new Book();
        book2.setTitle("Beyond the Stars");
        book2.setIsbn("9780987654321");
        book2.setGenre("Science Fiction");
        book2.setPublicationDate(LocalDate.of(2018, 8, 10));
        book2.setPrice(new BigDecimal("18.99"));
        book2.setDescription("An epic journey through space and time.");
        book2.setWriter(writers.get(1));
        books.add(book2);

        // Books by Emily Davis
        Book book3 = new Book();
        book3.setTitle("Love in the Rain");
        book3.setIsbn("9781122334455");
        book3.setGenre("Romance");
        book3.setPublicationDate(LocalDate.of(2021, 2, 14));
        book3.setPrice(new BigDecimal("12.99"));
        book3.setDescription("A heartwarming tale of love and resilience.");
        book3.setWriter(writers.get(2));
        books.add(book3);

        return books;
    }
}
