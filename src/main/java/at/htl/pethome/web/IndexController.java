package at.htl.pethome.web;

import at.htl.pethome.business.PersonFacade;
import at.htl.pethome.business.PetFacade;
import at.htl.pethome.entities.Owner;
import at.htl.pethome.entities.Person;
import at.htl.pethome.entities.Pet;
import org.primefaces.event.SelectEvent;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.List;

@Model
public class IndexController {

    Pet currPet;

    @Inject
    PersonFacade personFacade;
    @Inject
    PetFacade petFacade;

    public Pet getCurrPet() {
        return currPet;
    }

    public void setCurrPet(Pet currPet) {
        this.currPet = currPet;
    }

    public IndexController() {
        currPet=new Pet();
    }
    public List<Pet> getPets(){
        return petFacade.findAll();
    }
    public void onRowSelect(SelectEvent e){
        FacesMessage msg = new FacesMessage("Pet Selected", ((Pet) e.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        currPet=(Pet) e.getObject();
      //  evId=currPet.getId();
        System.out.println("Event:" + currPet.getName());
    }
    public void onRowUnselect(SelectEvent e){
        FacesMessage msg = new FacesMessage("Pet Unselected", ((Pet) e.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        currPet=new Pet();
    }
    public String getOwner(){
        if (currPet.getPersons().size()>0){
            for (Person p:currPet.getPersons()
                 ) {
                if (Owner.class.isInstance(p)){
                    return p.getName();
                }
            }
        }
        return "noOwner";
    }
}
