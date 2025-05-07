package com.example.springbootcrudapp.service.impl;

import com.example.springbootcrudapp.dto.WriterDTO;
import com.example.springbootcrudapp.entity.Writer;
import com.example.springbootcrudapp.repository.WriterRepository;
import com.example.springbootcrudapp.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WriterServiceImpl implements WriterService {

    private final WriterRepository WriterRepository;

    @Autowired
    public WriterServiceImpl(WriterRepository WriterRepository) {
        this.WriterRepository = WriterRepository;
    }

    @Override
    public List<WriterDTO> getAllWriters() {
        return WriterRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WriterDTO getWriterById(Long id) {
        Writer Writer = WriterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Writer not found with ID: " + id));
        return convertToDTO(Writer);
    }

    @Override
    public WriterDTO createWriter(WriterDTO WriterDTO) {
        Writer Writer = convertToEntity(WriterDTO);
        Writer savedWriter = WriterRepository.save(Writer);
        return convertToDTO(savedWriter);
    }

    @Override
    public WriterDTO updateWriter(Long id, WriterDTO WriterDTO) {
        Writer existingWriter = WriterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Writer not found with ID: " + id));

        // Update fields
        existingWriter.setFirstName(WriterDTO.getFirstName());
        existingWriter.setLastName(WriterDTO.getLastName());
        existingWriter.setEmail(WriterDTO.getEmail());
        existingWriter.setBiography(WriterDTO.getBiography());
        existingWriter.setBirthYear(WriterDTO.getBirthYear());

        Writer updatedWriter = WriterRepository.save(existingWriter);
        return convertToDTO(updatedWriter);
    }

    @Override
    public boolean deleteWriter(Long id) {
        if (WriterRepository.existsById(id)) {
            WriterRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public WriterDTO convertToDTO(Writer Writer) {
        WriterDTO WriterDTO = new WriterDTO();
        WriterDTO.setId(Writer.getId());
        WriterDTO.setFirstName(Writer.getFirstName());
        WriterDTO.setLastName(Writer.getLastName());
        WriterDTO.setEmail(Writer.getEmail());
        WriterDTO.setBiography(Writer.getBiography());
        WriterDTO.setBirthYear(Writer.getBirthYear());
        return WriterDTO;
    }

    @Override
    public Writer convertToEntity(WriterDTO WriterDTO) {
        Writer Writer = new Writer();
        Writer.setId(WriterDTO.getId());
        Writer.setFirstName(WriterDTO.getFirstName());
        Writer.setLastName(WriterDTO.getLastName());
        Writer.setEmail(WriterDTO.getEmail());
        Writer.setBiography(WriterDTO.getBiography());
        Writer.setBirthYear(WriterDTO.getBirthYear());
        return Writer;
    }
}