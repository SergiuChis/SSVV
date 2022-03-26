package ssvv.example;

import domain.Student;
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

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class AddStudentTest {
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
        var student = new Student("1", "George", 932, "george@ubbcluj.ro");

        assertDoesNotThrow(() -> service.addStudent(student));

        assertEquals(student, service.findStudent(student.getID()));
        service.deleteStudent(student.getID());
    }

    @Test
    void testAddAlreadyExistingEntity() {
        var student = new Student("1", "George", 932, "george@ubbcluj.ro");

        service.addStudent(student);
        Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
        service.deleteStudent(student.getID());
    }

    @Test
    void testEmptyId() {
        var student = new Student("", "George", 932, "george@ubbcluj.ro");

        Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    void testNullId() {
        var student = new Student(null, "George", 932, "george@ubbcluj.ro");

        Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    void testLongName() {
        char[] charArray = new char[101];
        Arrays.fill(charArray, 'a');
        String name = new String(charArray);
        var student = new Student("1", name, 932, "george@ubbcluj.ro");

        Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
        service.deleteStudent(student.getID());
    }

    @Test
    void testShortName() {
        var student = new Student("1", "a", 932, "george@ubbcluj.ro");

        Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    void testNumberAsName() {
        var student = new Student("1", "123", 932, "george@ubbcluj.ro");

        Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    void testNegativeGroupNumber() {
        var student = new Student("1", "123", -100, "george@ubbcluj.ro");

        Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    void testNullEmail() {
        var student = new Student("1", "123", 932, null);

        Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    void testEmptyEmail() {
        var student = new Student("1", "123", 932, "");

        Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    void testBVAGroup1() {
        var student = new Student("1", "123", -1, "");

        Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    void testBVAGroup2() {
        var student = new Student("1", "123", 0, "");

        Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    void testBVAGroup3() {
        var student = new Student("1", "123", 1, "");

        Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    void testBVAGroup4() {
        var student = new Student("1", "123", Integer.MAX_VALUE - 1, "");

        Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    void testBVAGroup5() {
        var student = new Student("1", "123", Integer.MAX_VALUE, "");

        Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

    @Test
    void testBVAGroup6() {
        var student = new Student("1", "123", Integer.MAX_VALUE + 1, "");

        Assertions.assertThrows(ValidationException.class, () -> service.addStudent(student));
    }

}