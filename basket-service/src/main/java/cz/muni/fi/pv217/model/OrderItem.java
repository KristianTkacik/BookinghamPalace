package cz.muni.fi.pv217.model;

import java.math.BigDecimal;

public class OrderItem {

    public Long orderId = null;
    public long bookId;
    public String bookTitle;
    public BigDecimal unitPrice;
    public int quantity;
}
