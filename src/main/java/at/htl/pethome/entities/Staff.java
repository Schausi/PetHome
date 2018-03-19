package at.htl.pethome.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Staff extends Person {

    public Staff(String name, int age) {
        super(name, age);
    }

    public Staff() {
    }
}
