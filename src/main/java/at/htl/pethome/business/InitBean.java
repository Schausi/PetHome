package at.htl.pethome.business;

import at.htl.pethome.entities.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Startup
@Singleton
public class InitBean {
    @Inject
    PetFacade petFacade;

    @Inject
    PersonFacade personFacade;

    @Inject
    StorageFacade storageFacade;

    public InitBean() {
    }

    @PostConstruct
    private void init(){
        Disease d=new Disease();
        Storage s=new Storage("Iron Cage");
        Owner o=new Owner("Bill",22);
        Staff a=new Staff("Joe",22);
        //personFacade.savePerson(a);

        Staff b=new Staff("Bobi",22);
        try {
            d=new Disease("Hundolitis",
                    true,
                    new java.sql.Date((new SimpleDateFormat("dd.MM.yyyy").parse("22.01.2018").getTime())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Pet p=new Pet("Cat","Minsey",2);
        Pet p1=new Pet("Parrot","Hans",1);
        Pet p2=new Pet("Dog","Dogmar",new Date(),3);

        p1.addDiseas(d);
        //TODO FIX detached entitiy save
        p1.setStorage(s);
        storageFacade.saveStorage(s);

        p1.addPerson(o);
        p.addPerson(a);
        p.addPerson(b);



        petFacade.save(p);
        petFacade.save(p1);
        petFacade.save(p2);

    }
}
