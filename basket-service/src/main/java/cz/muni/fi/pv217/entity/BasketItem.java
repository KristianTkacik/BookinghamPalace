package cz.muni.fi.pv217.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class BasketItem extends PanacheEntity {

    public long basketId;
    public long bookId;
    public String bookTitle;
    public BigDecimal unitPrice;
    public int quantity;

    public void merge(BasketItem update) {
        this.basketId = update.basketId;
        this.bookId = update.bookId;
        this.bookTitle = update.bookTitle == null ? this.bookTitle : update.bookTitle;
        this.unitPrice = update.unitPrice == null ? this.unitPrice : update.unitPrice;
        this.quantity = this.quantity + update.quantity;
    }

    @Override
    public String toString() {
        return "BasketItem{" +
                "basketId=" + basketId +
                ", bookId=" + bookId +
                ", bookTitle='" + bookTitle + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", id=" + id +
                '}';
    }
}
