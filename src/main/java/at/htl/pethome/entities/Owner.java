package at.htl.pethome.entities;

import javax.persistence.Entity;

@Entity
public class Owner extends Person {
    public Owner(String name, int age) {
        super(name, age);
    }

    public Owner() {
    }
}
