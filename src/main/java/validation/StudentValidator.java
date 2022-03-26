package validation;

import domain.Student;
import org.junit.platform.commons.util.StringUtils;

import java.util.regex.Pattern;

public class StudentValidator implements Validator<Student> {

    /**
     * Valideaza un student
     * @param entity - studentul pe care il valideaza
     * @throws ValidationException - daca studentul nu e valid
     */
    @Override
    public void validate(Student entity) throws ValidationException {
        if(entity.getID() == null){
            throw new ValidationException("Id incorect!");
        }
        if(entity.getID().equals("")){
            throw new ValidationException("Id incorect!");
        }
        if(entity.getNume().equals("")){
            throw new ValidationException("Nume incorect!");
        }
        if(entity.getNume().length() > 100){
            throw new ValidationException("Nume prea lung!");
        }
        if(entity.getNume().length() < 2){
            throw new ValidationException("Nume prea scurt!");
        }
        if (Pattern.compile(".*[0-9].*").matcher(entity.getNume()).matches()) {
            throw new ValidationException("Nume nu poate contine numere!");
        }
        if(entity.getGrupa() < 0) {
            throw new ValidationException("Grupa incorecta!");
        }
        if(entity.getEmail() == null){
            throw new ValidationException("Email incorect!");
        }
        if(entity.getNume() == null){
            throw new ValidationException("Nume incorect!");
        }
        if(entity.getEmail().equals("")){
            throw new ValidationException("Email incorect!");
        }
    }
}
