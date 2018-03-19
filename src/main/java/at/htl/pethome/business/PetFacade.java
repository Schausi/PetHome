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
        return entityManager.createNamedQuery("Pet.findall",Pet.class).getResultList();
    }

}
