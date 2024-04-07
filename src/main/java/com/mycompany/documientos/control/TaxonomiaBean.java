/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.documientos.control;

import com.mycompany.documientos.entity.Taxonomia;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author daniloues
 */
@Stateless
@Local
public class TaxonomiaBean extends AccesoADatos<Taxonomia>{
    
    
    @PersistenceContext(unitName = "DocumientosPU")
    EntityManager em;
    
    
    @Override
    public String entityQuery() {
        return ("Taxonomia");
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public TaxonomiaBean() {
        super(Taxonomia.class);
    }
}
