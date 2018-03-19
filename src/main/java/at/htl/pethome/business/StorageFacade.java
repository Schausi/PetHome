package at.htl.pethome.business;

import at.htl.pethome.entities.Storage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Stateless
public class StorageFacade {
    @PersistenceContext
    EntityManager entityManager;

    public void saveStorage(Storage storage){
        entityManager.persist(storage);
    }
}
