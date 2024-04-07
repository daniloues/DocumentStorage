/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.documientos.control;

import com.mycompany.documientos.entity.Documento;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;

/**
 *
 * @author daniloues
 */
@Stateless
@Local
public class DocumentoBean extends AccesoADatos<Documento> implements Serializable{
    
    
    @PersistenceContext(unitName = "DocumientosPU")
    EntityManager em;
    
    
    @Override
    public String entityQuery() {
        return ("Documento");
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public DocumentoBean() {
        super(Documento.class);
    }
}
