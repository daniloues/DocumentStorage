package com.mycompany.documientos.boundary.rest;

import com.mycompany.documientos.control.MetadatoBean;
import com.mycompany.documientos.control.TaxonomiaBean;
import com.mycompany.documientos.entity.Metadato;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

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
    

    
    
}
