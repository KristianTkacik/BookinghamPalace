package cz.muni.fi.pv217.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class OrderItem extends PanacheEntity {

    public Long orderId;
    public long bookId;
    public String bookTitle;
    public BigDecimal unitPrice;
    public int quantity;

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderId=" + orderId +
                ", bookId=" + bookId +
                ", bookTitle='" + bookTitle + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", id=" + id +
                '}';
    }
}
