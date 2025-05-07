package com.example.springbootcrudapp.controller;

import com.example.springbootcrudapp.dto.WriterDTO;
import com.example.springbootcrudapp.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/Writers")
public class WriterController {

    private final WriterService WriterService;

    @Autowired
    public WriterController(WriterService WriterService) {
        this.WriterService = WriterService;
    }

    // Display list of all Writers
    @GetMapping
    public String getAllWriters(Model model) {
        List<WriterDTO> Writers = WriterService.getAllWriters();
        model.addAttribute("Writers", Writers);
        return "Writer/list";
    }

    // Display form to create a new Writer
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("Writer", new WriterDTO());
        return "Writer/form";
    }

    // Handle Writer creation
    @PostMapping
    public String createWriter(@Valid @ModelAttribute("Writer") WriterDTO WriterDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "Writer/form";
        }

        try {
            WriterDTO createdWriter = WriterService.createWriter(WriterDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Writer created successfully!");
            return "redirect:/Writers";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating Writer: " + e.getMessage());
            return "redirect:/Writers/new";
        }
    }

    // Display form to edit an Writer
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            WriterDTO WriterDTO = WriterService.getWriterById(id);
            model.addAttribute("Writer", WriterDTO);
            return "Writer/form";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/Writers";
        }
    }

    // Handle Writer update
    @PostMapping("/update/{id}")
    public String updateWriter(@PathVariable Long id,
            @Valid @ModelAttribute("Writer") WriterDTO WriterDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "Writer/form";
        }

        try {
            WriterDTO updatedWriter = WriterService.updateWriter(id, WriterDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Writer updated successfully!");
            return "redirect:/Writers";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating Writer: " + e.getMessage());
            return "redirect:/Writers/edit/" + id;
        }
    }

    // Display Writer details
    @GetMapping("/{id}")
    public String getWriterById(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            WriterDTO WriterDTO = WriterService.getWriterById(id);
            model.addAttribute("Writer", WriterDTO);
            return "Writer/details";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/Writers";
        }
    }
}