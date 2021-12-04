package cz.muni.fi.pv217.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "eshopOrder")
public class Order extends PanacheEntity {

    public long customerId;
    public String customerName;
    public String street;
    public String city;
    public String country;
    public LocalDate date;
    @OneToMany(mappedBy = "orderId", fetch = FetchType.EAGER)
    public List<OrderItem> items = new ArrayList<>();

    public void merge(Order order) {
        this.customerId = order.customerId;
        if (order.customerName != null) {
            this.customerName = order.customerName;
        }
        if (order.street != null) {
            this.street = order.street;
        }
        if (order.city != null) {
            this.city = order.city;
        }
        if (order.country != null) {
            this.country = order.country;
        }
        if (order.date != null) {
            this.date = order.date;
        }
        this.items = order.items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", date=" + date +
                ", items=" + items +
                ", id=" + id +
                '}';
    }
}
