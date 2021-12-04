package cz.muni.fi.pv217.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    public long customerId;
    public String customerName;
    public String street;
    public String city;
    public String country;
    public LocalDate date;
    public List<OrderItem> items = new ArrayList<>();
}
