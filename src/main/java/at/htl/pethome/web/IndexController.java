package at.htl.pethome.web;

import at.htl.pethome.business.PersonFacade;
import at.htl.pethome.business.PetFacade;
import at.htl.pethome.entities.Pet;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

@Model
public class IndexController {

    @Inject
    PersonFacade personFacade;
    @Inject
    PetFacade petFacade;

    public IndexController() {
    }
    public List<Pet> getPets(){
        //System.out.println(petFacade.findAll());
        return petFacade.findAll();
    }
}
