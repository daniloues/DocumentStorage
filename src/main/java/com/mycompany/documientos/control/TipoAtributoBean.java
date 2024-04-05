/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.documientos.control;

import com.mycompany.documientos.entity.TipoAtributo;
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
public class TipoAtributoBean extends AccesoADatos<TipoAtributo> implements Serializable {
    
    
    @PersistenceContext(unitName = "DocumientosPU")
    EntityManager em;
    
    
    @Override
    public String entityQuery() {
        return ("TipoAtributo");
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public TipoAtributoBean() {
        super(TipoAtributo.class);
    }
}
