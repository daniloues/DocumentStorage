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
import jakarta.persistence.Query;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniloues
 */
@Stateless
@Local
public class AtributoBean extends AccesoADatos<Atributo> implements Serializable{
    
    
    @PersistenceContext(unitName = "DocumientosPU")
    EntityManager em;
    
     public Long getTipoDocumentoById(Long idAtributo) {

        EntityManager em = null;
        try {
            em = getEntityManager();

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        if (em != null) {
            try {
                Query q = em.createNamedQuery(entityQuery()+".findTipoDocumentobyId");
                q.setParameter("idAtributo", idAtributo);
                return ( (Long) Long.valueOf(q.getSingleResult().toString()));
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        throw new IllegalStateException();

    }
    
     public Atributo findAtributoByTipoDocumentoExists(Integer idTipoDocumento, Integer idAtributo) {

        EntityManager em = null;
        try {
            em = getEntityManager();

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        if (em != null) {
            try {
                Query q = em.createNamedQuery(entityQuery()+".findAtributoByTipoDocumentoExists");
                q.setParameter("idAtributo", idAtributo);
                q.setParameter("idTipoDocumento", idTipoDocumento);
                return (Atributo) q.getSingleResult();
                
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        throw new IllegalStateException();

    }
    
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
