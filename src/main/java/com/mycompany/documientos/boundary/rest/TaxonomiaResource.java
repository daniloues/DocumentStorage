package com.mycompany.documientos.boundary.rest;

import com.mycompany.documientos.control.AtributoBean;
import com.mycompany.documientos.control.DocumentoBean;
import com.mycompany.documientos.control.MetadatoBean;
import com.mycompany.documientos.control.TaxonomiaBean;
import com.mycompany.documientos.entity.Metadato;
import com.mycompany.documientos.entity.Taxonomia;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author daniloues
 */
@Path("taxonomia/")
public class TaxonomiaResource implements Serializable{
    

    @Inject
    TaxonomiaBean tBean;


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("documento/{idDocumento}")
    public Long findTaxonomiaById(
            @PathParam("idDocumento") final Integer idDocumento) {
        if (idDocumento != null) {
            // You may need to adjust this based on your implementation
            return tBean.getTipoDocumentoByIdDocumento(idDocumento.longValue());
           
        }
        return null;
    }   
}