package cz.muni.fi.pv217;

import cz.muni.fi.pv217.dto.BasketItemAddDTO;
import cz.muni.fi.pv217.dto.OrderAddressDTO;
import cz.muni.fi.pv217.entity.Basket;
import cz.muni.fi.pv217.service.BasketService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customer")
@ApplicationScoped
public class BasketResource {

    @Inject
    BasketService basketService;

    @GET
    @Path("/{customerId}/basket")
    @Produces(MediaType.APPLICATION_JSON)
    public Basket getCustomerBasket(@PathParam long customerId) {
        return basketService.getCustomerBasket(customerId);
    }

    @PUT
    @Path("/{customerId}/basket/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItem(@PathParam long customerId, BasketItemAddDTO itemAddDTO) {
        Basket basketWithItem = basketService.addItem(customerId, itemAddDTO);
        return Response.ok(basketWithItem).build();
    }

    @PUT
    @Path("/{customerId}/basket/remove/{itemId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeItem(@PathParam long customerId, @PathParam long itemId) {
        Basket basket = basketService.removeItem(customerId, itemId);
        return Response.ok(basket).build();
    }

    @PUT
    @Path("/{customerId}/basket/checkout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkout(@PathParam long customerId, OrderAddressDTO addressDTO) {
        return basketService.checkout(customerId, addressDTO);
    }

    @PUT
    @Path("/{customerId}/basket/clear")
    @Produces(MediaType.APPLICATION_JSON)
    public Response clear(@PathParam long customerId) {
        Basket basket = basketService.clear(customerId);
        return Response.ok(basket).build();
    }
}