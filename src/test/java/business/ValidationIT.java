package business;

import at.htl.pethome.business.PetFacade;
import at.htl.pethome.entities.Pet;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.persistence.Persistence;
import javax.validation.*;

import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by User on 07.12.2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ValidationIT {
   PetFacade pf=new PetFacade();
    private Validator validator;

    public ValidationIT() {
        pf.entityManager= Persistence.createEntityManagerFactory("myTestPU")
                .createEntityManager();
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();

    }
    @Test
    public void t010PetWithNulValues(){
        Pet p=new Pet(null,"jo",5);
        Set<ConstraintViolation<Pet>> violations = this.validator.validate(p);
        assertThat(violations.isEmpty(),is(false));
    }
    @Test
    public void t020PetWithNulValues(){
        Pet p=new Pet("haha",null,5);
        Set<ConstraintViolation<Pet>> violations = this.validator.validate(p);
        assertThat(violations.isEmpty(),is(false));
    }
    @Test
    public void t030PetWithNulValues(){
        Pet p=new Pet("haha","jo",-7);
        Set<ConstraintViolation<Pet>> violations = this.validator.validate(p);
        assertThat(violations.isEmpty(),is(false));
    }
}