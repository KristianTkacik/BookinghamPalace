package cz.muni.fi.pv217.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Basket extends PanacheEntity {

    @Column(unique = true)
    public long customerId;

    @OneToMany(mappedBy = "basketId", fetch = FetchType.EAGER)
    public List<BasketItem> items = new ArrayList<>();

    @Override
    public String toString() {
        return "Basket{" +
                "customerId=" + customerId +
                ", items=" + items +
                ", id=" + id +
                '}';
    }
}
