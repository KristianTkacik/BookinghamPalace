package cz.muni.fi.pv217;

import cz.muni.fi.pv217.entity.Order;
import cz.muni.fi.pv217.service.OrderService;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/order")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @GET
    @Counted(name = "order.getAll.counter")
    @Timed(name = "order.getAll.timer")
    public List<Order> getOrders() {
        return Order.listAll();
    }

    @GET
    @Path("/{id}")
    @Counted(name = "order.getOne.counter")
    @Timed(name = "order.getOne.timer")
    public Response getOrder(@PathParam long id) {
        Order order = Order.findById(id);
        if (order == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(String.format("Order for id %d not found.", id))
                    .build();
        }
        return Response.ok(order).build();
    }

    @POST
    @Path("/create")
    @Counted(name = "order.create.counter")
    @Timed(name = "order.create.timer")
    public Response createOrder(Order order) {
        Order created;
        try {
            created = orderService.createOrder(order);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}/update")
    @Counted(name = "order.update.counter")
    @Timed(name = "order.update.timer")
    public Order updateOrder(@PathParam long id, Order update) {
        return orderService.updateOrder(id, update);
    }

    @DELETE
    @Path("/{id}/delete")
    @Counted(name = "order.delete.counter")
    @Timed(name = "order.delete.timer")
    public Response deleteOrder(@PathParam long id) {
        Order deleted = orderService.deleteOrder(id);
        return Response.ok(deleted).build();
    }
}
