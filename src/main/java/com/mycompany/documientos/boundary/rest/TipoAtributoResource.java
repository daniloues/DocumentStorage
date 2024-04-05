

import com.mycompany.documientos.control.TipoAtributoBean;
import com.mycompany.documientos.entity.TipoAtributo;
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
import sv.edu.ues.occ.ingenieria.tpi135.documientos.boundary.rest.RestResourceHeaderPattern;

/**
 *
 * @author daniloues
 */
@Path("TipoAtributo")
public class TipoAtributoResource implements Serializable {

    // SE DEBE COMPROBAR QUE SE HAGA REFERENCIA A IDS PADRE QUE EXISTAN O SEAN NULOS *PENDIENTE
    
    
    @Inject
    TipoAtributoBean taBean;
    

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<TipoAtributo> findRange(
            @QueryParam(value = "first")
            @DefaultValue(value = "0") int first,
            @QueryParam(value = "page_size")
            @DefaultValue(value = "100000") int pageSize) {

        return taBean.findRange(first, pageSize);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Response findById(
            @PathParam("id")
            final Integer idTipoAtributo) {
        if (idTipoAtributo != null) {
            TipoAtributo findById = taBean.findById(idTipoAtributo);
            if (findById != null) {
                return Response.status(Response.Status.OK).entity(findById).build();
            }
            return Response.status(Response.Status.NOT_FOUND).header("not-found", idTipoAtributo).build();
        }
        return Response.status(422).
                header("missing-parameter", "id").
                build();
    }

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTipoAtributo(TipoAtributo nuevo) {
        try {
            taBean.create(nuevo);
            // Return 201 Created with Location header if creation is successful
            return Response.status(Response.Status.CREATED)
                           .header("Location", "/tipoatributo/" + nuevo.getIdTipoAtributo())
                           .build();
        } catch (IllegalStateException | IllegalArgumentException e) {
            // Handle exceptions thrown by create method
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                           .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, e.getMessage())
                           .build();
        }
    }
    
    
    
//    @POST
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response create(TipoAtributo registro,
//            @Context UriInfo info
//    ) {
//        if (registro != null && registro.getIdTipoAtributo() != null && registro.getNombre() != null) {
//            try {
//                taBean.create(registro);
//                URI requestUri = info.getAbsolutePath();
//                return Response.status(Response.Status.CREATED).header("location", requestUri.toString()
//                        + "/" + registro.getIdTipoAtributo()
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
    public Response replace(TipoAtributo registro,
            @Context UriInfo info
    ) {
        if (registro != null && registro.getIdTipoAtributo() != null && registro.getNombre() != null) {
            try {
                taBean.modify(registro);
                URI requestUri = info.getAbsolutePath();
                return Response.status(Response.Status.CREATED).header("location", requestUri.toString()
                        + "/" + registro.getIdTipoAtributo()
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
    public Response delete(TipoAtributo registro,
            @Context UriInfo info
    ) {
        if (registro != null && registro.getIdTipoAtributo() != null && registro.getNombre() != null) {
            try {
                taBean.delete(registro);
                URI requestUri = info.getAbsolutePath();
                return Response.status(Response.Status.CREATED).header("location", requestUri.toString()
                        + "/" + registro.getIdTipoAtributo()
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
