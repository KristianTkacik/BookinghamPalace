package cz.muni.fi.pv217.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
public class Customer extends PanacheEntity {

    @NotEmpty
    public String name;

    @Email
    @Column(unique = true)
    public String email;

    @NotEmpty
    public String passwordHash;

    public void merge(Customer update) {
        if (update.name != null) {
            this.name = update.name;
        }
        if (update.email != null) {
            this.email = update.email;
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }
}
