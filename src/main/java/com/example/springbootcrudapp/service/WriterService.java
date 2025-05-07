package com.example.springbootcrudapp.service;

import com.example.springbootcrudapp.dto.WriterDTO;
import com.example.springbootcrudapp.entity.Writer;

import java.util.List;

public interface WriterService {

    List<WriterDTO> getAllWriters();

    WriterDTO getWriterById(Long id);

    WriterDTO createWriter(WriterDTO WriterDTO);

    WriterDTO updateWriter(Long id, WriterDTO WriterDTO);

    boolean deleteWriter(Long id);

    // Convert entities to DTOs and vice versa
    WriterDTO convertToDTO(Writer Writer);

    Writer convertToEntity(WriterDTO WriterDTO);
}