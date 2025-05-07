package com.example.springbootcrudapp.service;

import com.example.springbootcrudapp.dto.WriterDTO;
import com.example.springbootcrudapp.entity.Writer;
import com.example.springbootcrudapp.repository.WriterRepository;
import com.example.springbootcrudapp.service.impl.WriterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WriterServiceTest {

    @Mock
    private WriterRepository WriterRepository;

    @InjectMocks
    private WriterServiceImpl WriterService;

    private Writer Writer;
    private WriterDTO WriterDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        Writer = new Writer();
        Writer.setId(1L);
        Writer.setFirstName("Mark");
        Writer.setLastName("Twain");
        Writer.setEmail("mark.twain@example.com");
        Writer.setBiography("American writer and humorist.");
        Writer.setBirthYear(1835);

        WriterDTO = new WriterDTO();
        WriterDTO.setId(1L);
        WriterDTO.setFirstName("Mark");
        WriterDTO.setLastName("Twain");
        WriterDTO.setEmail("mark.twain@example.com");
        WriterDTO.setBiography("American writer and humorist.");
        WriterDTO.setBirthYear(1835);
    }

    @Test
    public void whenGetAllWriters_thenReturnWriterDTOList() {
        // given
        List<Writer> Writers = new ArrayList<>();
        Writers.add(Writer);

        when(WriterRepository.findAll()).thenReturn(Writers);

        // when
        List<WriterDTO> WriterDTOs = WriterService.getAllWriters();

        // then
        assertThat(WriterDTOs).hasSize(1);
        assertThat(WriterDTOs.get(0).getFirstName()).isEqualTo(Writer.getFirstName());
        verify(WriterRepository, times(1)).findAll();
    }

    @Test
    public void whenGetWriterById_thenReturnWriterDTO() {
        // given
        when(WriterRepository.findById(1L)).thenReturn(Optional.of(Writer));

        // when
        WriterDTO found = WriterService.getWriterById(1L);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getFirstName()).isEqualTo(Writer.getFirstName());
        verify(WriterRepository, times(1)).findById(1L);
    }

    @Test
    public void whenGetWriterByIdNotFound_thenThrowException() {
        // given
        when(WriterRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            WriterService.getWriterById(99L);
        });
        verify(WriterRepository, times(1)).findById(99L);
    }

    @Test
    public void whenCreateWriter_thenReturnWriterDTO() {
        // given
        when(WriterRepository.save(any(Writer.class))).thenReturn(Writer);

        // when
        WriterDTO created = WriterService.createWriter(WriterDTO);

        // then
        assertThat(created).isNotNull();
        assertThat(created.getFirstName()).isEqualTo(WriterDTO.getFirstName());
        verify(WriterRepository, times(1)).save(any(Writer.class));
    }

    @Test
    public void whenUpdateWriter_thenReturnUpdatedWriterDTO() {
        // given
        Writer existingWriter = new Writer();
        existingWriter.setId(1L);
        existingWriter.setFirstName("Old Name");
        existingWriter.setLastName("Old Last");
        existingWriter.setEmail("old.email@example.com");

        when(WriterRepository.findById(1L)).thenReturn(Optional.of(existingWriter));
        when(WriterRepository.save(any(Writer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        WriterDTO updated = WriterService.updateWriter(1L, WriterDTO);

        // then
        assertThat(updated).isNotNull();
        assertThat(updated.getFirstName()).isEqualTo(WriterDTO.getFirstName());
        assertThat(updated.getLastName()).isEqualTo(WriterDTO.getLastName());
        assertThat(updated.getEmail()).isEqualTo(WriterDTO.getEmail());
        verify(WriterRepository, times(1)).findById(1L);
        verify(WriterRepository, times(1)).save(any(Writer.class));
    }

    @Test
    public void whenUpdateWriterNotFound_thenThrowException() {
        // given
        when(WriterRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            WriterService.updateWriter(99L, WriterDTO);
        });
        verify(WriterRepository, times(1)).findById(99L);
        verify(WriterRepository, never()).save(any(Writer.class));
    }

    @Test
    public void whenDeleteWriter_thenReturnTrue() {
        // given
        when(WriterRepository.existsById(1L)).thenReturn(true);
        doNothing().when(WriterRepository).deleteById(1L);

        // when
        boolean result = WriterService.deleteWriter(1L);

        // then
        assertThat(result).isTrue();
        verify(WriterRepository, times(1)).existsById(1L);
        verify(WriterRepository, times(1)).deleteById(1L);
    }

    @Test
    public void whenDeleteWriterNotFound_thenReturnFalse() {
        // given
        when(WriterRepository.existsById(99L)).thenReturn(false);

        // when
        boolean result = WriterService.deleteWriter(99L);

        // then
        assertThat(result).isFalse();
        verify(WriterRepository, times(1)).existsById(99L);
        verify(WriterRepository, never()).deleteById(any());
    }
}