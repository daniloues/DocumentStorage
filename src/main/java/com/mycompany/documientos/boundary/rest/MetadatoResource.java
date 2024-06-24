package com.mycompany.documientos.boundary.rest;

import com.mycompany.documientos.control.AtributoBean;
import com.mycompany.documientos.control.MetadatoBean;
import com.mycompany.documientos.control.TaxonomiaBean;
import com.mycompany.documientos.entity.Metadato;
import com.mycompany.documientos.resources.RestResourceHeaderPattern;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author daniloues
 */
@Path("documento/{idDocumento}/metadato")
public class MetadatoResource implements Serializable{
    
    @Inject
    AtributoBean aBean;
    

    @Inject
    TaxonomiaBean tBean;

    @Inject
    MetadatoBean mBean;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{idMetadato}")
    public Response findTaxonomiaById(
            @PathParam("idDocumento") final Integer idDocumento,
            @PathParam("idMetadato") final Integer idMetadato) {
        if (idDocumento != null && idMetadato != null) {
            // You may need to adjust this based on your implementation
            Metadato metadatoExists = mBean.findMetadatoByDocumentoExists(idDocumento, idMetadato);
            if (metadatoExists != null) {
                return Response.status(Response.Status.OK).entity(metadatoExists).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .header("not-found", "Attribute not found for TipoDocumento ID: " + idDocumento + " and Atributo ID: " + idMetadato)
                    .build();
        }
        return Response.status(422)
                .header("missing-parameter", "id")
                .entity("Both TipoDocumento ID and Atributo ID must be provided")
                .build();
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Metadato> findMetadatoByIdDocumento(
            @PathParam("idDocumento") final Integer idDocumento) {
        if (idDocumento != null) {
            // You may need to adjust this based on your implementation
            return mBean.getMetadatoByIdDocumento(idDocumento);
            
        }
        return null;
    }
    

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMetadato(
            @PathParam("idDocumento") Integer idTipoDocumento,
            Metadato nuevo) {

        if (nuevo == null || idTipoDocumento == null) {
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, "idTipoDocumento or nuevo is null")
                    .build();
        }

        // Validate the attributes of the new Atributo object
        if (nuevo.getIdAtributo() == null || nuevo.getIdDocumento() == null) {
            return Response.status(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO)
                    .header(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO, nuevo.toString())
                    .build();
        }

        if (!Objects.equals(tBean.getTipoDocumentoByIdDocumento(Long.valueOf(nuevo.getIdDocumento().getIdDocumento().toString())), aBean.getTipoDocumentoById(Long.valueOf(nuevo.getIdAtributo().getIdAtributo().toString())))) {

            return Response.status(405)
                    .header("METODO-NO-POSIBLE", nuevo.toString())
                    .build();

        }

        mBean.create(nuevo);
        Long nuevoIdMetadato = tBean.getNewestId();
        // Return a success response with the location of the created resource
        return Response.status(Response.Status.CREATED)
                .header("Location", "/tipodocumento/" + idTipoDocumento + "/atributo/" + nuevoIdMetadato)
                .build();
    }

    
    
    
}
