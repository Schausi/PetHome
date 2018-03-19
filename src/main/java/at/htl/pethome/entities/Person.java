package at.htl.pethome.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table
@DiscriminatorColumn(name = "Type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({@NamedQuery(name = "Person.findall",query = "select p from Person p")})
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "Darf nicht null sein!")
    String Name;
    @Min(value = 0)
    int Age;
   // @ManyToOne()
    //@Column
    //Pet p;

  /*  public Pet getP() {
        return p;
    }

    public void setP(Pet p) {
        this.p = p;
        this.p = p;
        if(!p.getPersons().contains(this))
            p.addPerson(this);
    }*/
    public int getId() {
        return id;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public Person(String name, int age) {
        Name = name;
        Age = age;
    }

    public Person() {
    }
}
