package com.mycompany.documientos.boundary.rest;

import com.mycompany.documientos.control.AtributoBean;
import com.mycompany.documientos.control.TipoDocumentoBean;
import com.mycompany.documientos.entity.Atributo;
import com.mycompany.documientos.entity.TipoDocumento;
import com.mycompany.documientos.resources.RestResourceHeaderPattern;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniloues
 */
@Path("tipodocumento")
public class TipoDocumentoResource implements Serializable {

    // SE DEBE COMPROBAR QUE SE HAGA REFERENCIA A IDS PADRE QUE EXISTAN O SEAN NULOS *PENDIENTE
    @Inject
    TipoDocumentoBean tdBean;

    @Inject
    AtributoBean aBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<TipoDocumento> findRange(
            @QueryParam(value = "first")
            @DefaultValue(value = "0") int first,
            @QueryParam(value = "page_size")
            @DefaultValue(value = "100000") int pageSize) {

        return tdBean.findRange(first, pageSize);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Response findById(
            @PathParam("id")
            final Integer idTipoDocumento) {
        if (idTipoDocumento != null) {
            TipoDocumento findById = tdBean.findById(idTipoDocumento);
            if (findById != null) {
                return Response.status(Response.Status.OK).entity(findById).build();
            }
            return Response.status(Response.Status.NOT_FOUND).header("not-found", idTipoDocumento).build();
        }
        return Response.status(422).
                header("missing-parameter", "id").
                build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{idTipoDocumento}/atributo/{idAtributo}")
    public Response findAtributoById(
            @PathParam("idTipoDocumento") final Integer idTipoDocumento,
            @PathParam("idAtributo") final Integer idAtributo) {
        if (idTipoDocumento != null && idAtributo != null) {
            // You may need to adjust this based on your implementation
            Atributo atributoExists = aBean.findAtributoByTipoDocumentoExists(idTipoDocumento, idAtributo);
            if (atributoExists != null) {
                return Response.status(Response.Status.OK).entity(atributoExists).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .header("not-found", "Attribute not found for TipoDocumento ID: " + idTipoDocumento + " and Atributo ID: " + idAtributo)
                    .build();
        }
        return Response.status(422)
                .header("missing-parameter", "id")
                .entity("Both TipoDocumento ID and Atributo ID must be provided")
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTipoDocumento(TipoDocumento nuevo) {

        if (nuevo != null && nuevo.getNombre() != null && nuevo.getActivo() != null) {
            try {
                tdBean.create(nuevo);
                Long nuevoIdDocumento = tdBean.getNewestId();
                return Response.status(Response.Status.CREATED)
                        .header("Location", "/tipodocumento/" + nuevoIdDocumento)
                        .build();
            } catch (Exception ex) {
                Logger.getLogger(ex.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, nuevo.toString())
                .build();
    }

    // POST DE ATRIBUTOS:
    @POST
    @Path("/{idTipoDocumento}/atributo")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAtributo(
            @PathParam("idTipoDocumento") Integer idTipoDocumento,
            Atributo nuevo) {

        if (idTipoDocumento == null || nuevo == null) {
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "idTipoDocumento or nuevo is null")
                    .build();
        }

        // Validate the attributes of the new Atributo object
        if (nuevo.getNombre() == null || nuevo.getNombrePantalla() == null || nuevo.getIdTipoAtributo() == null) {
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, nuevo.toString())
                    .build();
        }

        // Assuming the validation is successful, proceed with creating the Atributo
        // Logic to create the Atributo goes here
        aBean.create(nuevo);
        Long nuevoIdAtributo = aBean.getNewestId();
        // Return a success response with the location of the created resource
        return Response.status(Response.Status.CREATED)
                .header("Location", "/tipodocumento/" + idTipoDocumento + "/atributo/" + nuevoIdAtributo)
                .build();
    }

//    @POST
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response create(TipoDocumento registro,
//            @Context UriInfo info
//    ) {
//        if (registro != null && registro.getIdTipoDocumento() != null && registro.getNombre() != null) {
//            try {
//                tdBean.create(registro);
//                URI requestUri = info.getAbsolutePath();
//                return Response.status(Response.Status.CREATED).header("location", requestUri.toString()
//                        + "/" + registro.getIdTipoDocumento()
//                ).build();
//            } catch (Exception ex) {
//                Logger.getLogger(ex.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
//            }
//            return Response.status(500).header("create-exception", registro.toString()).build();
//        }
//        return Response.status(422).
//                header("missing-parameter", "id").
//                build();
//    }
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response replace(TipoDocumento registro,
            @Context UriInfo info
    ) {
        if (registro != null && registro.getIdTipoDocumento() != null && registro.getNombre() != null) {
            try {
                tdBean.modify(registro);
                URI requestUri = info.getAbsolutePath();
                return Response.status(Response.Status.CREATED).header("location", requestUri.toString()
                        + "/" + registro.getIdTipoDocumento()
                ).build();
            } catch (Exception ex) {
                Logger.getLogger(ex.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            return Response.status(500).header("create-exception", registro.toString()).build();
        }
        return Response.status(422).
                header("missing-parameter", "id").
                build();
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response delete(TipoDocumento registro,
            @Context UriInfo info
    ) {
        if (registro != null && registro.getIdTipoDocumento() != null && registro.getNombre() != null) {
            try {
                tdBean.delete(registro);
                URI requestUri = info.getAbsolutePath();
                return Response.status(Response.Status.CREATED).header("location", requestUri.toString()
                        + "/" + registro.getIdTipoDocumento()
                ).build();
            } catch (Exception ex) {
                Logger.getLogger(ex.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            return Response.status(500).header("create-exception", registro.toString()).build();
        }
        return Response.status(422).
                header("missing-parameter", "id").
                build();
    }

}
