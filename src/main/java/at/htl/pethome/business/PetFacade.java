package at.htl.pethome.business;

import at.htl.pethome.entities.Pet;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PetFacade {
    @PersistenceContext
   public EntityManager entityManager;

    public List<Pet> findByName(String name){

        return entityManager.createNamedQuery("Pet.findbyName",Pet.class).
                setParameter("NAME",name).getResultList();
    }

    public void save(Pet v){
        entityManager.merge(v);
    }

    public List<Pet> findAll(){
        List<Pet> p= entityManager.createNamedQuery("Pet.findall",Pet.class).getResultList();
        for (Pet e:p
             ) {
            System.out.println(e.getPersons().size()+" , "+e.getStorage()+" , "+e.getDiseases().size()+" , "+e.getRegistrationDate());
        }
        return p;
    }

}
