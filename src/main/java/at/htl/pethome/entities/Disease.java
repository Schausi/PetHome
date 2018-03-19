package at.htl.pethome.entities;

import javax.persistence.*;
import java.sql.Date;

@Entity
@NamedQueries({@NamedQuery(name = "Disease.findall",query = "select d from Disease d")})
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String name;
    Boolean infecteous;
    Date illSince;
    public Disease(String name,boolean infecteous,Date illSince) {
        setName(name);
        setInfecteous(infecteous);
        setIllSince(illSince);
    }

    public Disease() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getInfecteous() {
        return infecteous;
    }

    public void setInfecteous(Boolean infecteous) {
        this.infecteous = infecteous;
    }

    public Date getIllSince() {
        return illSince;
    }

    public void setIllSince(Date illSince) {
        this.illSince = illSince;
    }
}
