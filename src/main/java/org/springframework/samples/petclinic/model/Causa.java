package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "causas")
public class Causa extends BaseEntity {
    @Column(length = 120)
    @NotEmpty(message = "El nombre no puede estar vacio")
    private String nombre;
    
    @Column(length = 120)
    @NotEmpty(message = "El nombre de la organizacion no puede estar vacio")
    private String organizacion;
    
    @Column(length = 5000)
    @NotEmpty(message = "La descripcion no puede estar vacio")
    private String descr;
    
    private Double recaudacion;
    
    @NotNull
    @Positive(message="La cantidad debe ser mayor 0")
    private Double objetivo;

    public Double getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(Double objetivo) {
		this.objetivo = objetivo;
	}

	public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(String organizacion) {
        this.organizacion = organizacion;
    }

    public String getDescripcion() {
        return descr;
    }

    public void setDescripcion(String descripcion) {
        this.descr = descripcion;
    }

    public Double getRecaudacion() {
        return recaudacion;
    }

    public void setRecaudacion(Double recaudacion) {
        this.recaudacion = recaudacion;
    }    
}
