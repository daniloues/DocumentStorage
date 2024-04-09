/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.documientos.control;

import com.mycompany.documientos.entity.Taxonomia;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniloues
 */
@Stateless
@Local
public class TaxonomiaBean extends AccesoADatos<Taxonomia>{
    
    
    @PersistenceContext(unitName = "DocumientosPU")
    EntityManager em;
    
    @Inject
    DocumentoBean dBean;
    
        public Long getTipoDocumentoByIdDocumento(Long idDocumento) {

        EntityManager em = null;
        try {
            em = getEntityManager();

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        if (em != null) {
            try {
                Query q = em.createNamedQuery(entityQuery()+".findTipoDocumentoByDocumento");
                
                q.setParameter("idDocumento", dBean.findById(idDocumento));
                return ( (Long) Long.valueOf(q.getSingleResult().toString()));
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        throw new IllegalStateException();

    }
    
    
        
        
        
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
