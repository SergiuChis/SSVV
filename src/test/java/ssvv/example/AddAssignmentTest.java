package validation;

import domain.Student;
import domain.Tema;
import org.junit.After;
import org.junit.jupiter.api.Test;
import repository.StudentXMLRepo;
import repository.TemaFileRepository;
import repository.TemaXMLRepo;

import static org.junit.jupiter.api.Assertions.*;

public class AddAssignmentTest {
    private TemaXMLRepo temaRepository = new TemaXMLRepo("teme.xml");

    @Test
    void testValidEntity_shouldWork() {
        var tema = new Tema("1", "descriere",4, 2);

        assertDoesNotThrow(() -> temaRepository.save(tema));

        assertEquals(tema.getDescriere(), temaRepository.findOne("1").getDescriere());
        temaRepository.delete("1");
    }

    @Test
    void testValidEntity_shouldNotWork() {
        var tema = new Tema("2", "descriere 2",5, 6);

        assertDoesNotThrow(() -> temaRepository.save(tema));
        assertEquals(tema.getDescriere(), temaRepository.findOne("1").getDescriere());
    }
}