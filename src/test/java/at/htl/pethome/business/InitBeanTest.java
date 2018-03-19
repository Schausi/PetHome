package at.htl.pethome.business;

import at.htl.pethome.entities.*;
import org.junit.Test;

import javax.persistence.Persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class InitBeanTest
{
    PetFacade pf=new PetFacade();
    StorageFacade storageFacade=new StorageFacade();
    public InitBeanTest() {
        pf.entityManager= Persistence.createEntityManagerFactory("myTestPU")
                .createEntityManager();
        storageFacade.entityManager=Persistence.createEntityManagerFactory("myTestPU")
                .createEntityManager();
    }

    @Test
    public void t001CreatePets() throws Exception {
        pf.entityManager.getTransaction().begin();
        for (int i=0;i<=10;i++){
            Disease d=new Disease();
            Storage s=new Storage("Iron Cage"+i);
            Owner o=new Owner("Bill"+i,22+i);
            Staff a=new Staff("Joe"+i,22+i);
            try {
                d=new Disease("Hundolitis"+i,
                        true,
                        new java.sql.Date((new SimpleDateFormat("dd.MM.yyyy").parse("22.01.2018").getTime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }


            Pet p=new Pet("Cat","Minsey"+i,2+i);
            Pet p1=new Pet("Parrot","Hans"+i,1+i);
            Pet p2=new Pet("Dog","Dogmar"+i,new Date(),3+i);

            p1.addDiseas(d);
            p1.setStorage(s);
            storageFacade.saveStorage(s);

            p1.addPerson(o);
            p.addPerson(a);
            p.addPerson(o);

            pf.save(p);
            pf.save(p1);
            pf.save(p2);
            pf.entityManager.getTransaction().commit();
            System.out.println(pf.entityManager.createNativeQuery("SELECT p.NAME from PET p;").getFirstResult());
        }
    }
}