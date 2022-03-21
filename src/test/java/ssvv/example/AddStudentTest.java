package validation;

import domain.Student;
import org.junit.jupiter.api.Test;
import repository.StudentXMLRepo;

import static org.junit.jupiter.api.Assertions.*;

public class AddStudentTest {
    private StudentXMLRepo studentRepository = new StudentXMLRepo("studenti.xml");

    @Test
    void testValidEntity_shouldWork() {
        var student = new Student("", "George", 932, "george@ubbcluj.ro", "Marcel");

        assertDoesNotThrow(() -> studentRepository.save(student));

        assertEquals(1, studentRepository.findAll().spliterator().estimateSize());
    }
}