package at.htl.pethome.entities;

import javax.persistence.*;

@Entity
@NamedQueries({@NamedQuery(name = "Storage.findall",query = "select s from Storage s")})
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String description;

    public Storage(String description) {
        this.description = description;
    }

    public Storage() {
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
