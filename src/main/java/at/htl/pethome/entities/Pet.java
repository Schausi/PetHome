package at.htl.pethome.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Pet.findall",query = "select p from Pet p"),
        @NamedQuery(name = "Pet.findbyName",query = "select p from Pet p where upper(p.Name) like upper(:NAME) ")
})
public class Pet {
    @NotNull(message = "Darf nicht Null sein")
    String Type;
    @NotNull(message = "Muss gesetzt werden!")
    String Name;

    @ManyToOne
    Storage storage;

    Date BuyDate,RegistrationDate;
    @Min(value = 0,message = "Bitte ein größeres Alter eingeben")
    int Age;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int Id;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
            @JoinTable(name = "Pet_Persons",joinColumns = @JoinColumn(name = "PersonID",referencedColumnName = "Id"),
                    inverseJoinColumns = @JoinColumn(name = "PetId",referencedColumnName = "Id"))
     List<Person> persons;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Pet_Diseases",joinColumns = @JoinColumn(name = "DiseasID",referencedColumnName = "Id"),
            inverseJoinColumns = @JoinColumn(name = "PetId",referencedColumnName = "Id"))
    List<Disease> diseases;

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public Pet(String type,String name,int age) {
        Type = type;
        Name = name;
        Age = age;
        persons =new LinkedList<>();
        diseases=new LinkedList<Disease>();
    }

    public Pet(String type,String name, Date registrationDate,int age) {
        Type = type;
        Name = name;
        Age = age;
        RegistrationDate = registrationDate;
        persons =new LinkedList<>();
       // owners=new LinkedList<>();
    }

    public Pet() {
        persons =new LinkedList<>();
      //  owners=new LinkedList<>();

    }


    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getBuyDate() {
        return BuyDate;
    }

    public void setBuyDate(Date buyDate) {
        BuyDate = buyDate;
    }

    public Date getRegistrationDate() {
        return RegistrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        RegistrationDate = registrationDate;
    }
    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
   /* public void addOwner(Person p){
        this.owners.add((Owner) p);
    }*/
    public void addPerson(Person p){
        if(p != null)
            this.persons.add(p);
      /*  if(p.getP()!=this)
            p.setP(this);*/
    }
    public void addDiseas(Disease p){
        if(p != null)
            this.diseases.add(p);
      /*  if(p.getP()!=this)
            p.setP(this);*/
    }
}
