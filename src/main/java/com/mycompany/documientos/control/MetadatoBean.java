/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.documientos.control;

import com.mycompany.documientos.entity.Metadato;
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
public class MetadatoBean extends AccesoADatos<Metadato> implements Serializable{
    
    
  //  @PersistenceContext(unitName = "DocumientosPU")
    EntityManager em;
    
    
    @Override
    public String entityQuery() {
        return ("Metadato");
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public MetadatoBean() {
        super(Metadato.class);
    }
}
