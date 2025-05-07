package com.example.springbootcrudapp.controller;

import com.example.springbootcrudapp.dto.BookDTO;
import com.example.springbootcrudapp.service.WriterService;
import com.example.springbootcrudapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final WriterService WriterService;

    @Autowired
    public BookController(BookService bookService, WriterService WriterService) {
        this.bookService = bookService;
        this.WriterService = WriterService;
    }

    // Display list of all books
    @GetMapping
    public String getAllBooks(Model model) {
        List<BookDTO> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "book/list";
    }

    // Display form to create a new book
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new BookDTO());
        model.addAttribute("Writers", WriterService.getAllWriters());
        return "book/form";
    }

    // Handle book creation
    @PostMapping
    public String createBook(@Valid @ModelAttribute("book") BookDTO bookDTO,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("Writers", WriterService.getAllWriters());
            return "book/form";
        }

        try {
            BookDTO createdBook = bookService.createBook(bookDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Book created successfully!");
            return "redirect:/books";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating book: " + e.getMessage());
            return "redirect:/books/new";
        }
    }

    // Display form to edit a book
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            BookDTO bookDTO = bookService.getBookById(id);
            model.addAttribute("book", bookDTO);
            model.addAttribute("Writers", WriterService.getAllWriters());
            return "book/form";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/books";
        }
    }

    // Handle book update
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id,
            @Valid @ModelAttribute("book") BookDTO bookDTO,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("Writers", WriterService.getAllWriters());
            return "book/form";
        }

        try {
            BookDTO updatedBook = bookService.updateBook(id, bookDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully!");
            return "redirect:/books";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating book: " + e.getMessage());
            return "redirect:/books/edit/" + id;
        }
    }

    // Display book details
    @GetMapping("/{id}")
    public String getBookById(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            BookDTO bookDTO = bookService.getBookById(id);
            model.addAttribute("book", bookDTO);
            return "book/details";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/books";
        }
    }

    // Display books by Writer
    @GetMapping("/Writer/{WriterId}")
    public String getBooksByWriter(@PathVariable Long WriterId, Model model) {
        List<BookDTO> books = bookService.getBooksByWriterId(WriterId);
        model.addAttribute("books", books);
        model.addAttribute("WriterName", books.isEmpty() ? "Unknown" : books.get(0).getWriterName());
        return "book/list";
    }

    // Display books with Writers (using the inner join query)
    @GetMapping("/with-Writers")
    public String getBooksWithWriters(Model model) {
        List<Map<String, Object>> booksWithWriters = bookService.getBooksWithWriters();
        model.addAttribute("booksWithWriters", booksWithWriters);
        return "book/books-with-Writers";
    }
}