/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.documientos.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author daniloues
 */
@Entity
@Table(name = "atributo")
@NamedQueries({
    @NamedQuery(name = "Atributo.findAtributosByTipoDocumentoId", query = "SELECT a FROM Atributo a WHERE a.idTipoDocumento.idTipoDocumento = :idTipoDocumento"),
    @NamedQuery(name = "Atributo.findAll", query = "SELECT a FROM Atributo a"),
    @NamedQuery(name = "Atributo.findAtributoByTipoDocumentoExists", query = "SELECT a FROM Atributo a JOIN a.idTipoDocumento td WHERE td.idTipoDocumento = :idTipoDocumento AND a.idAtributo = :idAtributo"),
    @NamedQuery(name = "Atributo.findTipoDocumentobyId", query = "SELECT a.idTipoDocumento.idTipoDocumento FROM Atributo a WHERE a.idAtributo = :idAtributo"),
    @NamedQuery(name = "Atributo.findByIdAtributo", query = "SELECT a FROM Atributo a WHERE a.idAtributo = :idAtributo"),
    @NamedQuery(name = "Atributo.findByNombre", query = "SELECT a FROM Atributo a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Atributo.findByNombrePantalla", query = "SELECT a FROM Atributo a WHERE a.nombrePantalla = :nombrePantalla"),
    @NamedQuery(name = "Atributo.findByIndicacionesPantalla", query = "SELECT a FROM Atributo a WHERE a.indicacionesPantalla = :indicacionesPantalla"),
    @NamedQuery(name = "Atributo.findByObligatorio", query = "SELECT a FROM Atributo a WHERE a.obligatorio = :obligatorio"),
    @NamedQuery(name = "Atributo.findLastId", query = "SELECT MAX(a.idAtributo) FROM Atributo a")})
public class Atributo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_atributo")
    private Long idAtributo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "nombre_pantalla")
    private String nombrePantalla;
    @Column(name = "indicaciones_pantalla")
    private String indicacionesPantalla;
    @Column(name = "obligatorio")
    private Boolean obligatorio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAtributo")
    @JsonbTransient
    private Collection<Metadato> metadatoCollection;
    @JoinColumn(name = "id_tipo_atributo", referencedColumnName = "id_tipo_atributo")
    @ManyToOne(optional = false)
    private TipoAtributo idTipoAtributo;
    @JoinColumn(name = "id_tipo_documento", referencedColumnName = "id_tipo_documento")
    @ManyToOne(optional = false)
    private TipoDocumento idTipoDocumento;

    public Atributo() {
    }

    public Atributo(Long idAtributo) {
        this.idAtributo = idAtributo;
    }

    public Long getIdAtributo() {
        return idAtributo;
    }

    public void setIdAtributo(Long idAtributo) {
        this.idAtributo = idAtributo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombrePantalla() {
        return nombrePantalla;
    }

    public void setNombrePantalla(String nombrePantalla) {
        this.nombrePantalla = nombrePantalla;
    }

    public String getIndicacionesPantalla() {
        return indicacionesPantalla;
    }

    public void setIndicacionesPantalla(String indicacionesPantalla) {
        this.indicacionesPantalla = indicacionesPantalla;
    }

    public Boolean getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(Boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public Collection<Metadato> getMetadatoCollection() {
        return metadatoCollection;
    }

    public void setMetadatoCollection(Collection<Metadato> metadatoCollection) {
        this.metadatoCollection = metadatoCollection;
    }

    public TipoAtributo getIdTipoAtributo() {
        return idTipoAtributo;
    }

    public void setIdTipoAtributo(TipoAtributo idTipoAtributo) {
        this.idTipoAtributo = idTipoAtributo;
    }

    public TipoDocumento getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(TipoDocumento idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtributo != null ? idAtributo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atributo)) {
            return false;
        }
        Atributo other = (Atributo) object;
        if ((this.idAtributo == null && other.idAtributo != null) || (this.idAtributo != null && !this.idAtributo.equals(other.idAtributo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.documientos.entity.Atributo[ idAtributo=" + idAtributo + " ]";
    }

}
