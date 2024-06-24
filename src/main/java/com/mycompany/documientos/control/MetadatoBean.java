/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.documientos.control;

import com.mycompany.documientos.entity.Documento;
import com.mycompany.documientos.entity.Metadato;
import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniloues
 */
@Stateless
@Local
public class MetadatoBean extends AccesoADatos<Metadato> implements Serializable {

    @PersistenceContext(unitName = "DocumientosPU")
    EntityManager em;

    public Metadato findMetadatoByDocumentoExists(Integer idDocumento, Integer idMetadato) {

        EntityManager em = null;
        try {
            em = getEntityManager();

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        if (em != null) {
            try {
                Query q = em.createNamedQuery(entityQuery() + ".findMetadatoByDocumentoExists");
                q.setParameter("idMetadato", idMetadato);
                q.setParameter("idDocumento", idDocumento);
                return (Metadato) q.getSingleResult();

            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        throw new IllegalStateException();

    }
    
    public List<Documento> findAllDocumentoByAtributo(Integer idAtributo) {

        EntityManager em = null;
        try {
            em = getEntityManager();

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        if (em != null) {
            try {
                Query q = em.createNamedQuery(entityQuery() + ".findAllDocumentoByAtributo");
                q.setParameter("idAtributo", idAtributo);
                return q.getResultList();

            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        throw new IllegalStateException();

    }
    
    public List<Documento> findAllDocumentoByAtributoAndValue(Integer idAtributo, String valor){
        
        EntityManager em = null;
        try {
            em = getEntityManager();

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        if (em != null) {
            try {
                Query q = em.createNamedQuery(entityQuery() + ".findAllDocumentoByAtributoAndValor");
                q.setParameter("idAtributo", idAtributo);
                q.setParameter("valor", valor);                
                return q.getResultList();

            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        throw new IllegalStateException();
    }
    
    public List<Metadato> getMetadatoByIdDocumento(Integer idDocumento){
        EntityManager em = null;
        try {
            em = getEntityManager();

        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        if (em != null) {
            try {
                Query q = em.createNamedQuery(entityQuery() + ".findMetadatoByIdDocumento");
                q.setParameter("idDocumento", idDocumento);            
                return q.getResultList();

            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        throw new IllegalStateException();
    }

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
