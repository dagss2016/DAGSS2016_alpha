/*
 Práctica Java EE 7, DAGSS 2015/16 (ESEI, U. de Vigo)
 */
package es.uvigo.esei.dagss.dominio.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.Min;

@Entity
public class Receta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Prescripcion prescripcion;

    @Min(1)
    Integer cantidad;

    @Temporal(javax.persistence.TemporalType.DATE)
    Date inicioValidez;

    @Temporal(javax.persistence.TemporalType.DATE)
    Date finValidez;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    EstadoReceta estadoReceta;

    @ManyToOne(optional = true)
    Farmacia farmaciaDispensadora;


    public Receta() {
    }

    public Receta(Prescripcion prescripcion, Integer cantidad, Date inicioValidez, Date finValidez, EstadoReceta estadoReceta) {
        this(prescripcion, cantidad, inicioValidez, finValidez, estadoReceta, null);
    }

    public Receta(Prescripcion prescripcion, Integer cantidad, Date inicioValidez, Date finValidez, EstadoReceta estadoReceta, Farmacia farmaciaDispensadora) {
        this.prescripcion = prescripcion;
        this.cantidad = cantidad;
        this.inicioValidez = inicioValidez;
        this.finValidez = finValidez;
        this.estadoReceta = estadoReceta;
        this.farmaciaDispensadora = this.farmaciaDispensadora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prescripcion getPrescripcion() {
        return prescripcion;
    }

    public void setPrescripcion(Prescripcion prescripcion) {
        this.prescripcion = prescripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getInicioValidez() {
        return inicioValidez;
    }

    public void setInicioValidez(Date inicioValidez) {
        this.inicioValidez = inicioValidez;
    }

    public Date getFinValidez() {
        return finValidez;
    }

    public void setFinValidez(Date finValidez) {
        this.finValidez = finValidez;
    }

    public EstadoReceta getEstado() {
        return estadoReceta;
    }

    public void setEstado(EstadoReceta estado) {
        this.estadoReceta = estado;
    }

    public EstadoReceta getEstadoReceta() {
        return estadoReceta;
    }

    public void setEstadoReceta(EstadoReceta estadoReceta) {
        this.estadoReceta = estadoReceta;
    }

    public Farmacia getFarmaciaDispensadora() {
        return farmaciaDispensadora;
    }

    public void setFarmaciaDispensadora(Farmacia farmaciaDispensadora) {
        this.farmaciaDispensadora = farmaciaDispensadora;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Receta other = (Receta) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }



    

    
}
