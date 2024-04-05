/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.documientos.control;

import com.mycompany.documientos.entity.Atributo;
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
public class AtributoBean extends AccesoADatos<Atributo> implements Serializable{
    
    
   // @PersistenceContext(unitName = "DocumientosPU")
    EntityManager em;
    
    
    @Override
    public String entityQuery() {
        return ("Atributo");
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public AtributoBean() {
        super(Atributo.class);
    }
}
