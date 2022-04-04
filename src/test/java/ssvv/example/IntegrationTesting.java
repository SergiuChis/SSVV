package ssvv.example;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.time.LocalDate;

public class IntegrationTesting {
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
    void testShortName() {
        var student = new Student("1", "a", 932, "george@ubbcluj.ro");

        Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    void testIdNotNull() {
        var tema = new Tema(null, "descriere 2",5, 6);

        Assertions.assertThrows(ValidationException.class, () -> service.addTema(tema));
    }

    @Test
    void testGradeOutsideBoundaries() {
        LocalDate ld = LocalDate.of(2022, 1, 1);
        var grade = new Nota("455", "1003", "2", 15, ld);

        Assertions.assertThrows(ValidationException.class, () -> service.addNota(grade, "test"));
    }

    @Test
    void integrationTestStudentAssignmentGrade() {
        testShortName();
        testIdNotNull();
        testGradeOutsideBoundaries();
    }
}
