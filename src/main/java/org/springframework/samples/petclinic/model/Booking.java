package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaEntrada;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fechaSalida;
	
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	@Column(name = "room")
	@NotEmpty
	@Digits(fraction = 0, integer = 100)
	private String room;

	public LocalDate getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(LocalDate fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public LocalDate getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(LocalDate fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

}
