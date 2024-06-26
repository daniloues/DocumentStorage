package com.mycompany.documientos.boundary.rest;

import com.mycompany.documientos.control.AtributoBean;
import com.mycompany.documientos.control.DocumentoBean;
import com.mycompany.documientos.control.MetadatoBean;
import com.mycompany.documientos.control.TaxonomiaBean;
import com.mycompany.documientos.entity.Atributo;
import com.mycompany.documientos.entity.Documento;
import com.mycompany.documientos.entity.Metadato;
import com.mycompany.documientos.entity.Taxonomia;
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
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniloues
 */
@Path("documento")
public class DocumentoResource implements Serializable {

    // SE DEBE COMPROBAR QUE SE HAGA REFERENCIA A IDS PADRE QUE EXISTAN O SEAN NULOS *PENDIENTE
    @Inject
    DocumentoBean dBean;

    @Inject
    TaxonomiaBean tBean;

    @Inject
    MetadatoBean mBean;

    @Inject
    AtributoBean aBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Documento> findRange(
            @QueryParam(value = "first")
            @DefaultValue(value = "0") int first,
            @QueryParam(value = "page_size")
            @DefaultValue(value = "100000") int pageSize) {

        return dBean.findRange(first, pageSize);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{idDocumento}")
    public Response findById(
            @PathParam("idDocumento")
            final Integer idDocumento) {
        if (idDocumento != null) {

            Documento findById = dBean.findById(Long.valueOf(idDocumento.toString()));
            if (findById != null) {
                return Response.status(Response.Status.OK).entity(findById).build();
            }
            return Response.status(Response.Status.NOT_FOUND).header("not-found", idDocumento).build();
        }
        return Response.status(422).
                header("missing-parameter", "id").
                build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/atributo/{idAtributo}/")
    public List<Documento> findByAtributo(
            @PathParam("idAtributo")
            final Integer idAtributo) {
        // Add logging to trace the request
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Received request with idAtributo: {0}", idAtributo);

        if (idAtributo != null) {
            // Add logging to trace the method execution
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Processing idAtributo: {0}", idAtributo);

            // Assuming mBean is correctly initialized and findAllDocumentoByAtributo is implemented
            return mBean.findAllDocumentoByAtributo(idAtributo);
        }

        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "idAtributo is null");
        return null;
    }

        @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/atributo/{idAtributo}/{valor}")
    public List<Documento> findByAtributoAndValor(
            @PathParam("idAtributo")
            final Integer idAtributo,
            @PathParam("valor")
            final String valor) {
        // Add logging to trace the request
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Received request with idAtributo: {0}", idAtributo);

        if (idAtributo != null) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Processing idAtributo: {0}", idAtributo);

            return mBean.findAllDocumentoByAtributoAndValue(idAtributo, valor);
        }

        Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "idAtributo is null");
        return null;
    }
    
   
                    
                    

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{idDocumento}/taxonomia/{idTaxonomia}")
    public Response findMetadatoById(
            @PathParam("idDocumento") final Integer idDocumento,
            @PathParam("idTaxonomia") final Integer idTaxonomia) {
        if (idDocumento != null && idTaxonomia != null) {
            // You may need to adjust this based on your implementation
            Taxonomia taxonomiaExists = tBean.findTaxonomiaByDocumentoExists(idDocumento, idTaxonomia);
            if (taxonomiaExists != null) {
                return Response.status(Response.Status.OK).entity(taxonomiaExists).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .header("not-found", "Attribute not found for TipoDocumento ID: " + idDocumento + " and Atributo ID: " + idTaxonomia)
                    .build();
        }
        return Response.status(422)
                .header("missing-parameter", "id")
                .entity("Both TipoDocumento ID and Atributo ID must be provided")
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDocumento(Documento nuevo) {

        if (nuevo != null && nuevo.getNombre() != null && nuevo.getCreadoPor() != null && nuevo.getUbicacionFisica() != null) {
            try {
                
                dBean.create(nuevo);
                return Response.status(Response.Status.CREATED)
                        .header("Location", "/documento/" + nuevo.getIdDocumento())
                        .build();
            } catch (Exception ex) {
                Logger.getLogger(ex.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, nuevo.toString())
                .build();
    }

    // POST DE TAXONOMIA:
    @POST
    @Path("/{idDocumento}/taxonomia")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTaxonomia(
            @PathParam("idDocumento") Integer idTipoDocumento,
            Taxonomia nuevo) {

        if (idTipoDocumento == null || nuevo == null) {
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "idTipoDocumento or nuevo is null")
                    .build();
        }

        // Validate the attributes of the new Atributo object
        if (nuevo.getIdTipoDocumento() == null || nuevo.getIdDocumento() == null) {
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, nuevo.toString())
                    .build();
        }

        // Assuming the validation is successful, proceed with creating the Atributo
        // Logic to create the Atributo goes here
        tBean.create(nuevo);
        Long nuevoIdTaxonomia = tBean.getNewestId();
        // Return a success response with the location of the created resource
        return Response.status(Response.Status.CREATED)
                .header("Location", "/tipodocumento/" + idTipoDocumento + "/atributo/" + nuevoIdTaxonomia)
                .build();
    }


//    @POST
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response create(Documento registro,
//            @Context UriInfo info
//    ) {
//        if (registro != null && registro.getIdDocumento() != null && registro.getNombre() != null) {
//            try {
//                dBean.create(registro);
//                URI requestUri = info.getAbsolutePath();
//                return Response.status(Response.Status.CREATED).header("location", requestUri.toString()
//                        + "/" + registro.getIdDocumento()
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
    public Response replace(Documento registro,
            @Context UriInfo info
    ) {
        if (registro != null && registro.getIdDocumento() != null && registro.getNombre() != null) {
            try {
                dBean.modify(registro);
                URI requestUri = info.getAbsolutePath();
                return Response.status(Response.Status.CREATED).header("location", requestUri.toString()
                        + "/" + registro.getIdDocumento()
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
    public Response delete(Documento registro,
            @Context UriInfo info
    ) {
        if (registro != null && registro.getIdDocumento() != null && registro.getNombre() != null) {
            try {
                dBean.delete(registro);
                URI requestUri = info.getAbsolutePath();
                return Response.status(Response.Status.CREATED).header("location", requestUri.toString()
                        + "/" + registro.getIdDocumento()
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
