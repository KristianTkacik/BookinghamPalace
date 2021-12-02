package cz.muni.fi.pv217.service;

import cz.muni.fi.pv217.dto.BasketItemAddDTO;
import cz.muni.fi.pv217.entity.Basket;
import cz.muni.fi.pv217.entity.BasketItem;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class BasketService {

    @Transactional
    public Basket getCustomerBasket(long customerId) {
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
        Basket basket = getCustomerBasket(customerId);
        if (basket == null) {
            basket = new Basket();
            basket.customerId = customerId;
        }

        BasketItem item = new BasketItem();
        item.basketId = basket.id;
        item.bookId = itemAddDTO.bookId;
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
        Basket basket = getCustomerBasket(customerId);
        if (basket == null) {
            throw new NotFoundException("Customer with id %d does not have a basket");
        }

        BasketItem item = BasketItem.findById(itemId);
        if (item == null) {
            throw new NotFoundException("Item with id %d does not exist");
        }

        BasketItem.deleteById(itemId);
        basket.items.remove(item);
        basket.persist();
        return basket;
    }

    @Transactional
    public Basket clear(long customerId) {
        Basket basket = getCustomerBasket(customerId);
        if (basket == null) {
            throw new NotFoundException("Customer with id %d does not have a basket");
        }

        BasketItem.delete("basketId", basket.id);
        basket.items.clear();
        basket.persist();
        return basket;
    }
}
