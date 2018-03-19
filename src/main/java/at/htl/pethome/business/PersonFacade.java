package at.htl.pethome.business;

import at.htl.pethome.entities.Person;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PersonFacade {
    @PersistenceContext
    EntityManager entityManager;

    public void savePerson(Person p){
        entityManager.merge(p);
    }
    public List<Person> findPersons(){
        List<Person> people=entityManager.createNamedQuery("Person.findall",Person.class).getResultList();
        return people;
    }
}
