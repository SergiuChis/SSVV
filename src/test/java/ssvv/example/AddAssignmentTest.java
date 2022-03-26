package ssvv.example;

import domain.Student;
import domain.Tema;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaFileRepository;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

public class AddAssignmentTest {
    StudentValidator studentValidator = new StudentValidator();
    TemaValidator temaValidator = new TemaValidator();
    String filenameStudent = "Studenti.xml";
    String filenameTema = "Teme.xml";
    String filenameNota = "Note.xml";
    StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
    TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
    NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
    NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
    Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);

    @Test
    void testValidEntity_shouldWork() {
        var tema = new Tema("1", "descriere",4, 2);

        assertDoesNotThrow(() -> service.addTema(tema));

        assertEquals(tema.getDescriere(), service.findTema("1").getDescriere());
        service.deleteTema("1");
    }

    @Test
    void testIdNotNull() {
        var tema = new Tema(null, "descriere 2",5, 6);

        Assertions.assertThrows(ValidationException.class, () -> service.addTema(tema));
    }

    @Test
    void testIdEmptyId() {
        var tema = new Tema("", "descriere",5, 6);

        Assertions.assertThrows(ValidationException.class, () -> service.addTema(tema));
    }

    @Test
    void testDescriptionNotEmpty() {
        var tema = new Tema("1", "",5, 6);

        Assertions.assertThrows(ValidationException.class, () -> service.addTema(tema));
    }

    @Test
    void testDeadlineNotBelow1() {
        var tema = new Tema("1", "descriere",0, 6);

        Assertions.assertThrows(ValidationException.class, () -> service.addTema(tema));
    }

    @Test
    void testDeadlineNotAbove14() {
        var tema = new Tema("1", "descriere",15, 6);

        Assertions.assertThrows(ValidationException.class, () -> service.addTema(tema));
    }

    @Test
    void testPrimireNotBelow1() {
        var tema = new Tema("1", "descriere",5, 0);

        Assertions.assertThrows(ValidationException.class, () -> service.addTema(tema));
    }

    @Test
    void testPrimireNotAbove14() {
        var tema = new Tema("1", "descriere",6, 18);

        Assertions.assertThrows(ValidationException.class, () -> service.addTema(tema));
    }

}