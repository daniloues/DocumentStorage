package com.mycompany.documientos.boundary.rest;

import com.mycompany.documientos.control.AtributoBean;
import com.mycompany.documientos.entity.Atributo;
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
@Path("tipodocumento/{idTipoDocumento}/atributo")
public class AtributoResource implements Serializable {

    // SE DEBE COMPROBAR QUE SE HAGA REFERENCIA A IDS PADRE QUE EXISTAN O SEAN NULOS *PENDIENTE
    @Inject
    AtributoBean aBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAllByTipoDocumento(
            @PathParam("idTipoDocumento") final Integer idTipoDocumento) {
        if (idTipoDocumento != null) {
            List<Atributo> registros = aBean.findByTipoDocumento(idTipoDocumento);
            return Response.status(Response.Status.OK)
                    .entity(registros)
                    .build();
        }
        return Response.status(422)
                .header("missing-parameter", "idTipoDocumento")
                .entity("TipoDocumento ID must be provided")
                .build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{idAtributo}")
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
    public Response createAtributo(Atributo nuevo) {

        if (nuevo != null && nuevo.getNombre() != null && nuevo.getIdTipoDocumento() != null && nuevo.getIdTipoDocumento() != null) {
            try {
                aBean.create(nuevo);
                return Response.status(Response.Status.CREATED)
                        .header("Location", "/atributo/" + nuevo.getIdAtributo())
                        .build();
            } catch (Exception ex) {
                Logger.getLogger(ex.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, nuevo.toString())
                .build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response replace(Atributo registro,
            @Context UriInfo info
    ) {
        if (registro != null && registro.getIdAtributo() != null && registro.getNombre() != null) {
            try {
                aBean.modify(registro);
                URI requestUri = info.getAbsolutePath();
                return Response.status(Response.Status.CREATED).header("location", requestUri.toString()
                        + "/" + registro.getIdAtributo()
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
    public Response delete(Atributo registro,
            @Context UriInfo info
    ) {
        if (registro != null && registro.getIdAtributo() != null && registro.getNombre() != null) {
            try {
                aBean.delete(registro);
                URI requestUri = info.getAbsolutePath();
                return Response.status(Response.Status.CREATED).header("location", requestUri.toString()
                        + "/" + registro.getIdAtributo()
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
