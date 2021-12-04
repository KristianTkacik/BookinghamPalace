package cz.muni.fi.pv217.service;

import cz.muni.fi.pv217.client.CatalogServiceClient;
import cz.muni.fi.pv217.client.CustomerServiceClient;
import cz.muni.fi.pv217.client.OrderServiceClient;
import cz.muni.fi.pv217.dto.BasketItemAddDTO;
import cz.muni.fi.pv217.dto.OrderAddressDTO;
import cz.muni.fi.pv217.entity.*;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.time.LocalDate;

@ApplicationScoped
public class BasketService {

    @Inject
    @RestClient
    CustomerServiceClient customerServiceClient;

    @Inject
    @RestClient
    CatalogServiceClient catalogServiceClient;

    @Inject
    @RestClient
    OrderServiceClient orderServiceClient;

    @Transactional
    public Basket getCustomerBasket(long customerId) {
        checkCustomerExist(customerId);

        Basket basket = Basket.find("customerId", customerId).firstResult();
        if (basket == null) {
            basket = new Basket();
            basket.customerId = customerId;
            basket.persist();
        }
        return basket;
    }

    @Transactional
    public Basket addItem(long customerId, BasketItemAddDTO itemAddDTO) {
        checkCustomerExist(customerId);

        Basket basket = getCustomerBasket(customerId);
        if (basket == null) {
            basket = new Basket();
            basket.customerId = customerId;
        }

        Book book = catalogServiceClient.getBook(itemAddDTO.bookId);
        if (book == null) {
            throw new NotFoundException(String.format("Book with id %d does not exist", itemAddDTO.bookId));
        }

        BasketItem item = new BasketItem();
        item.basketId = basket.id;
        item.bookId = book.id;
        item.bookTitle = book.title;
        item.unitPrice = book.price;
        item.quantity = itemAddDTO.quantity;

        if (basket.items.contains(item)) {
            BasketItem original = BasketItem.findById(item.id);
            original.merge(item);
            original.persist();
        } else {
            basket.items.add(item);
            item.persist();
        }

        basket.persist();
        return basket;
    }

    @Transactional
    public Basket removeItem(long customerId, long itemId) {
        checkCustomerExist(customerId);

        Basket basket = getCustomerBasket(customerId);
        if (basket == null) {
            throw new NotFoundException("Customer with id %d does not have a basket");
        }

        BasketItem item = BasketItem.findById(itemId);
        if (item == null) {
            throw new NotFoundException("Basket item with id %d does not exist");
        }

        BasketItem.deleteById(itemId);
        basket.items.remove(item);
        basket.persist();
        return basket;
    }

    @Transactional
    public Basket clear(long customerId) {
        checkCustomerExist(customerId);

        Basket basket = getCustomerBasket(customerId);
        if (basket == null) {
            throw new NotFoundException("Customer with id %d does not have a basket");
        }

        BasketItem.delete("basketId", basket.id);
        basket.items.clear();
        basket.persist();
        return basket;
    }

    @Transactional
    public Response checkout(long customerId, OrderAddressDTO addressDTO) {
        Customer customer = customerServiceClient.getCustomer(customerId);
        if (customer == null) {
            throw new NotFoundException(String.format("Customer with id %d does not exist", customerId));
        }

        Basket basket = getCustomerBasket(customerId);
        if (basket == null || basket.items.isEmpty()) {
            throw new BadRequestException(String.format("Basket of customer with id %d is empty", customerId));
        }

        Order order = new Order();
        order.customerId = customerId;
        order.customerName = customer.name;
        order.date = LocalDate.now();
        order.street = addressDTO.street;
        order.city = addressDTO.city;
        order.country = addressDTO.country;

        for (BasketItem basketItem : basket.items) {
            OrderItem orderItem = new OrderItem();
            orderItem.bookId = basketItem.bookId;
            orderItem.bookTitle = basketItem.bookTitle;
            orderItem.unitPrice = basketItem.unitPrice;
            orderItem.quantity = basketItem.quantity;
            order.items.add(orderItem);
        }

        clear(customerId);
        return orderServiceClient.createOrder(order);
    }

    private void checkCustomerExist(long customerId) {
        Customer customer = customerServiceClient.getCustomer(customerId);
        if (customer == null) {
            throw new NotFoundException(String.format("Customer with id %d does not exist", customerId));
        }
    }
}
