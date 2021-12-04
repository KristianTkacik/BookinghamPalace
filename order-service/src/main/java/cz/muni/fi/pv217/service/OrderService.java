package cz.muni.fi.pv217.service;

import cz.muni.fi.pv217.entity.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.Objects;

@ApplicationScoped
public class OrderService {

    @Transactional
    public Order createOrder(Order order) {
        order.persist();
        return order;
    }

    @Transactional
    public Order updateOrder(long id, Order update) {
        Order order = Order.findById(id);

        if (Objects.isNull(order)) {
            throw new NotFoundException(String.format("Order with id: %d not found", id));
        }

        order.merge(update);
        order.persist();
        return order;
    }

    @Transactional
    public Order deleteOrder(long id) {
        Order order = Order.findById(id);

        if (Objects.isNull(order)) {
            throw new NotFoundException(String.format("Order with id: %d not found", id));
        }

        boolean deleted = Order.deleteById(id);
        return deleted ? order : null;
    }
}
