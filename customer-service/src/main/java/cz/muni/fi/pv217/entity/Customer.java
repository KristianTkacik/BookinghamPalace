package cz.muni.fi.pv217.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Customer extends PanacheEntity {

    public String name;
    public String email;

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
