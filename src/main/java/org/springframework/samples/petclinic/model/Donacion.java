package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "donaciones")
public class Donacion extends BaseEntity {
    @Column(name = "fecha_donacion")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate fechaDonacion;
    
    @NotNull
    @Positive(message="La cantidad debe ser mayor 0")
    private Double cantidadDonada;
    
    @ManyToOne
    @JoinColumn(name = "id_causa")
    private Causa causa;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_owner")
    private Owner donante;

	public LocalDate getFechaDonacion() {
		return fechaDonacion;
	}

	public void setFechaDonacion(LocalDate fechaDonacion) {
		this.fechaDonacion = fechaDonacion;
	}

	public Double getCantidadDonada() {
		return cantidadDonada;
	}

	public void setCantidadDonada(Double cantidadDonada) {
		this.cantidadDonada = cantidadDonada;
	}

	public Causa getCausa() {
		return causa;
	}

	public void setCausa(Causa causa) {
		this.causa = causa;
	}

	public Owner getDonante() {
		return donante;
	}

	public void setDonante(Owner donante) {
		this.donante = donante;
	}
}
