package com.example.springbootcrudapp.repository;

import com.example.springbootcrudapp.entity.Writer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class WriterRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WriterRepository WriterRepository;

    @Test
    public void whenFindByLastName_thenReturnWriter() {
        // given
        Writer Writer = new Writer();
        Writer.setFirstName("John");
        Writer.setLastName("Doe");
        Writer.setEmail("john.doe@example.com");
        Writer.setBirthYear(1980);
        entityManager.persist(Writer);
        entityManager.flush();

        // when
        List<Writer> found = WriterRepository.findByLastName("Doe");

        // then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getFirstName()).isEqualTo(Writer.getFirstName());
        assertThat(found.get(0).getLastName()).isEqualTo(Writer.getLastName());
    }

    @Test
    public void whenFindByEmail_thenReturnWriter() {
        // given
        Writer Writer = new Writer();
        Writer.setFirstName("Jane");
        Writer.setLastName("Smith");
        Writer.setEmail("jane.smith@example.com");
        Writer.setBirthYear(1985);
        entityManager.persist(Writer);
        entityManager.flush();

        // when
        Writer found = WriterRepository.findByEmail("jane.smith@example.com");

        // then
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo(Writer.getEmail());
    }

    @Test
    public void whenFindByBirthYearGreaterThan_thenReturnWriters() {
        // given
        Writer Writer1 = new Writer();
        Writer1.setFirstName("Bob");
        Writer1.setLastName("Johnson");
        Writer1.setEmail("bob.johnson@example.com");
        Writer1.setBirthYear(1990);
        entityManager.persist(Writer1);

        Writer Writer2 = new Writer();
        Writer2.setFirstName("Alice");
        Writer2.setLastName("Brown");
        Writer2.setEmail("alice.brown@example.com");
        Writer2.setBirthYear(1970);
        entityManager.persist(Writer2);

        entityManager.flush();

        // when
        List<Writer> found = WriterRepository.findByBirthYearGreaterThan(1980);

        // then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getFirstName()).isEqualTo(Writer1.getFirstName());
    }
}