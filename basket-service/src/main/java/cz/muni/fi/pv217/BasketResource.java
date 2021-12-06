package cz.muni.fi.pv217;

import cz.muni.fi.pv217.dto.BasketItemAddDTO;
import cz.muni.fi.pv217.dto.OrderAddressDTO;
import cz.muni.fi.pv217.entity.Basket;
import cz.muni.fi.pv217.service.BasketService;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customer")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class BasketResource {

    @Inject
    BasketService basketService;

    @GET
    @Path("/{customerId}/basket")
    @Counted(name = "customer.getBasket.counter")
    @Timed(name = "customer.getBasket.timer")
    public Basket getCustomerBasket(@PathParam long customerId) {
        return basketService.getCustomerBasket(customerId);
    }

    @PUT
    @Path("/{customerId}/basket/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Counted(name = "customer.addItemToBasket.counter")
    @Timed(name = "customer.addItemToBasket.timer")
    public Response addItem(@PathParam long customerId, BasketItemAddDTO itemAddDTO) {
        Basket basketWithItem = basketService.addItem(customerId, itemAddDTO);
        return Response.ok(basketWithItem).build();
    }

    @PUT
    @Path("/{customerId}/basket/remove/{itemId}")
    @Counted(name = "customer.removeItemFromBasket.counter")
    @Timed(name = "customer.removeItemFromBasket.timer")
    public Response removeItem(@PathParam long customerId, @PathParam long itemId) {
        Basket basket = basketService.removeItem(customerId, itemId);
        return Response.ok(basket).build();
    }

    @PUT
    @Path("/{customerId}/basket/checkout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Counted(name = "customer.checkout.counter")
    @Timed(name = "customer.checkout.timer")
    public Response checkout(@PathParam long customerId, OrderAddressDTO addressDTO) {
        return basketService.checkout(customerId, addressDTO);
    }

    @PUT
    @Path("/{customerId}/basket/clear")
    @Counted(name = "customer.clear.counter")
    @Timed(name = "customer.clear.timer")
    public Response clear(@PathParam long customerId) {
        Basket basket = basketService.clear(customerId);
        return Response.ok(basket).build();
    }
}