package cz.muni.fi.pv217;

import cz.muni.fi.pv217.dto.BasketItemAddDTO;
import cz.muni.fi.pv217.entity.Basket;
import cz.muni.fi.pv217.entity.BasketItem;
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
    public Response getCustomerBasket(@PathParam long customerId) {
        Basket basket = basketService.getCustomerBasket(customerId);
        return Response.ok(basket).build();
    }

    @PUT
    @Path("/{customerId}/basket/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Basket addItem(@PathParam long customerId, BasketItemAddDTO itemAddDTO) {
        return basketService.addItem(customerId, itemAddDTO);
    }

    @PUT
    @Path("/{customerId}/basket/remove")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Basket removeItem(@PathParam long customerId, long itemId) {
        return basketService.removeItem(customerId, itemId);
    }

    @PUT
    @Path("/{customerId}/basket/clear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Basket clear(@PathParam long customerId) {
        return basketService.clear(customerId);
    }
}