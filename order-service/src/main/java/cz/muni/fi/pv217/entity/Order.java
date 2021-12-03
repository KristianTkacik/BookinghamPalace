package cz.muni.fi.pv217.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Order extends PanacheEntity {

    public String address;
    public LocalDate date;

    public void merge(Order order) {
        if (order.address != null) {
            this.address = order.address;
        }
        if (order.date != null) {
            this.date = order.date;
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "address='" + address + '\'' +
                ", date=" + date +
                '}';
    }
}
