/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.documientos.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author daniloues
 */
@Entity
@Table(name = "documento")
@NamedQueries({
    @NamedQuery(name = "Documento.findAll", query = "SELECT d FROM Documento d"),
    @NamedQuery(name = "Documento.findByIdDocumento", query = "SELECT d FROM Documento d WHERE d.idDocumento = :idDocumento"),
    @NamedQuery(name = "Documento.findByNombre", query = "SELECT d FROM Documento d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "Documento.findByReferenciaExterna", query = "SELECT d FROM Documento d WHERE d.referenciaExterna = :referenciaExterna"),
    @NamedQuery(name = "Documento.findByUbicacionFisica", query = "SELECT d FROM Documento d WHERE d.ubicacionFisica = :ubicacionFisica"),
    @NamedQuery(name = "Documento.findByUri", query = "SELECT d FROM Documento d WHERE d.url = :url"),
    @NamedQuery(name = "Documento.findByFechaCreacion", query = "SELECT d FROM Documento d WHERE d.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Documento.findByCreadoPor", query = "SELECT d FROM Documento d WHERE d.creadoPor = :creadoPor"),
    @NamedQuery(name = "Documento.findByComentarios", query = "SELECT d FROM Documento d WHERE d.comentarios = :comentarios"),
    @NamedQuery(name = "Documento.findLastId", query = "SELECT MAX(d.idDocumento) FROM Documento d")})
public class Documento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_documento")
    private Long idDocumento;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "referencia_externa")
    private String referenciaExterna;
    @Column(name = "ubicacion_fisica")
    private String ubicacionFisica;
    @Column(name = "url")
    private String url;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "creado_por")
    private String creadoPor;
    @Column(name = "comentarios")
    private String comentarios;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDocumento")
    private Collection<Metadato> metadatoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDocumento")
    private Collection<Taxonomia> taxonomiaCollection;

    public Documento() {
    }

    public Documento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getReferenciaExterna() {
        return referenciaExterna;
    }

    public void setReferenciaExterna(String referenciaExterna) {
        this.referenciaExterna = referenciaExterna;
    }

    public String getUbicacionFisica() {
        return ubicacionFisica;
    }

    public void setUbicacionFisica(String ubicacionFisica) {
        this.ubicacionFisica = ubicacionFisica;
    }

    public String getUri() {
        return url;
    }

    public void setUri(String url) {
        this.url = url;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Collection<Metadato> getMetadatoCollection() {
        return metadatoCollection;
    }

    public void setMetadatoCollection(Collection<Metadato> metadatoCollection) {
        this.metadatoCollection = metadatoCollection;
    }

    public Collection<Taxonomia> getTaxonomiaCollection() {
        return taxonomiaCollection;
    }

    public void setTaxonomiaCollection(Collection<Taxonomia> taxonomiaCollection) {
        this.taxonomiaCollection = taxonomiaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocumento != null ? idDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Documento)) {
            return false;
        }
        Documento other = (Documento) object;
        if ((this.idDocumento == null && other.idDocumento != null) || (this.idDocumento != null && !this.idDocumento.equals(other.idDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.documientos.entity.Documento[ idDocumento=" + idDocumento + " ]";
    }
    
}
