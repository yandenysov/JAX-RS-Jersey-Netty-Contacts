package org.example.app.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.app.domain.contact.Contact;
import org.example.app.network.ResponseEntity;
import org.example.app.network.ResponseEntityList;
import org.example.app.service.impl.ContactService;
import org.example.app.utils.Constants;

import java.util.Collections;
import java.util.List;

// REST-controller, який надає клієнтам REST API
// для доступу до ресурсу.
@Path("/api/v1/contacts")
@Produces({MediaType.APPLICATION_JSON})
public class ContactController {

    ContactService service = new ContactService();

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(Contact contact) {
        Contact contactCreated = service.create(contact);
        if (contactCreated != null) {
            return Response.ok(new ResponseEntity<>(201, "Created",
                            true, contactCreated).toString())
                    .status(Response.Status.CREATED).build();
        } else {
            return Response.ok(new ResponseEntity<>(204, "No Content",
                            false, Constants.TEXT_NO_CONTENT).toString())
                    .status(Response.Status.NO_CONTENT).build();
        }
    }

    @GET
    public Response fetchAll() {
        List<Contact> list = service.fetchAll();
        if (!list.isEmpty()) {
            return Response.ok(new ResponseEntityList<>(200, "OK",
                            true, list).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntityList<>(404, "Not Found",
                            false, Collections.emptyList()).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }


    // ---- Path Params ----------------------

    @GET
    @Path("{id: [0-9]+}")
    public Response fetchById(@PathParam("id") Long id) {
        Contact contact = service.fetchById(id);
        if (contact != null) {
            return Response.ok(new ResponseEntity<>(200, "OK",
                            true, contact.toString()).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntity<>(404, "Not Found",
                            true, Constants.TEXT_NOT_FOUND).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{id: [0-9]+}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(@PathParam("id") Long id, Contact contact) {
        Contact contactToUpdate = service.fetchById(id);
        if (contactToUpdate != null) {
            Contact contactUpdated = service.update(id, contact);
            return Response.ok(new ResponseEntity<>(200, "OK",
                            true, contactUpdated).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntity<>(404, "Not Found",
                            true, Constants.TEXT_NOT_FOUND).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id: [0-9]+}")
    public Response delete(@PathParam("id") Long id) {
        if (service.delete(id)) {
            return Response.ok(new ResponseEntity<>(200, "OK",
                            true, Constants.TEXT_DELETED).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntity<>(404, "Not Found",
                            true, Constants.TEXT_NOT_FOUND).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }


    // ---- Query Params ----------------------

    /*
        http://localhost:8081/api/v1/contacts/query-by-firstname?firstName=Tom
        If firstName does not exist
        http://localhost:8081/api/v1/contacts/query-by-firstname?firstName=Tomas
    */
    @GET
    @Path("/query-by-firstname")
    public Response fetchByFirstName(@QueryParam("firstName") String firstName) {
        List<Contact> list = service.fetchByFirstName(firstName);
        if (!list.isEmpty()) {
            return Response.ok(new ResponseEntityList<>(200, "OK",
                            true, list).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntityList<>(404, "Not Found",
                            false, Collections.emptyList()).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    /*
        http://localhost:8081/api/v1/contacts/query-by-lastname?lastName=Bright
        http://localhost:8081/api/v1/contacts/query-by-lastname?lastName=Terra
        If lastName does not exist
        http://localhost:8081/api/v1/contacts/query-by-lastname?lastName=Mars
    */
    @GET
    @Path("/query-by-lastname")
    public Response fetchByLastName(@QueryParam("lastName") String lastName) {
        List<Contact> list = service.fetchByLastName(lastName);
        if (!list.isEmpty()) {
            return Response.ok(new ResponseEntityList<>(200, "OK",
                            true, list).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntityList<>(404, "Not Found",
                            false, Collections.emptyList()).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    /*
        http://localhost:8081/api/v1/contacts/query-order-by?orderBy=firstName
        http://localhost:8081/api/v1/contacts/query-order-by?orderBy=lastName
    */
    @GET
    @Path("/query-order-by")
    public Response fetchAllOrderBy(@QueryParam("orderBy") String orderBy) {
        List<Contact> list = service.fetchAllOrderBy(orderBy);
        if (!list.isEmpty()) {
            return Response.ok(new ResponseEntityList<>(200, "OK",
                            true, list).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntityList<>(404, "Not Found",
                            false, Collections.emptyList()).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }


    /*
        http://localhost:8081/api/v1/contacts/query-by-lastname-order-by?lastName=Bright&orderBy=firstName
        http://localhost:8081/api/v1/contacts/query-by-lastname-order-by?lastName=Bright&orderBy=phone
        If lastName does not exist
        http://localhost:8081/api/v1/contacts/query-by-lastname-order-by?lastName=Mars&orderBy=firstName
    */
    @GET
    @Path("/query-by-lastname-order-by")
    public Response fetchByLastNameOrderBy(
            @QueryParam("lastName") String lastName,
            @QueryParam("orderBy") String orderBy
    ) {
        List<Contact> list =
                service.fetchByLastNameOrderBy(lastName, orderBy);
        if (!list.isEmpty()) {
            return Response.ok(new ResponseEntityList<>(200, "OK",
                            true, list).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntityList<>(404, "Not Found",
                            false, Collections.emptyList()).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    /*
        http://localhost:8081/api/v1/contacts/query-between-ids?from=3&to=6
        If ids do not exist
        http://localhost:8081/api/v1/contacts/query-between-ids?from=9&to=12
    */
    @GET
    @Path("/query-between-ids")
    public Response fetchBetweenIds(
            @QueryParam("from") int from,
            @QueryParam("to") int to
    ) {
        List<Contact> list = service.fetchBetweenIds(from, to);
        if (!list.isEmpty()) {
            return Response.ok(new ResponseEntityList<>(200, "OK",
                            true, list).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntityList<>(404, "Not Found",
                            false, Collections.emptyList()).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }

    /*
        http://localhost:8081/api/v1/contacts/query-lastname-in?lastName1=Terra&lastName2=Bright
        If lastName1 does not exist
        http://localhost:8081/api/v1/contacts/query-lastname-in?lastName1=Mars&lastName2=Bright
        If lastName2 does not exist
        http://localhost:8081/api/v1/contacts/query-lastname-in?lastName1=Terra&lastName2=Forest
        If lastName1 and lastName2 do not exist
        http://localhost:8081/api/v1/contacts/query-lastname-in?lastName1=Mars&lastName2=Forest
    */
    @GET
    @Path("/query-lastname-in")
    public Response fetchLastNameIn(
            @QueryParam("lastName1") String lastName1,
            @QueryParam("lastName2") String lastName2
    ) {
        List<Contact> list =
                service.fetchLastNameIn(lastName1, lastName2);
        if (!list.isEmpty()) {
            return Response.ok(new ResponseEntityList<>(200, "OK",
                            true, list).toString())
                    .status(Response.Status.OK).build();
        } else {
            return Response.ok(new ResponseEntityList<>(404, "Not Found",
                            false, Collections.emptyList()).toString())
                    .status(Response.Status.NOT_FOUND).build();
        }
    }
}
