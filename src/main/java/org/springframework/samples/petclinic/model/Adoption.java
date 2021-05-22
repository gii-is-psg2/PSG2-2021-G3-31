package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "adoptions")
public class Adoption extends BaseEntity{
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "owner_id")
	private Owner Owner;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "possible_owner")
	private Owner possibleOwner;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	@NotEmpty
	@Length(min=0,max=150,message = "Debe de tener como máximo 150 carácteres.")
	@Column(name = "description")
	private String description;
	
	@Column(name = "adoptionStatus")
	private AdoptionState adoptionStatus;
	
	
	public AdoptionState getAdoptionStatus() {
		return adoptionStatus;
	}

	public void setAdoptionStatus(AdoptionState adoptionStatus) {
		this.adoptionStatus = adoptionStatus;
	}

	public Owner getOwner() {
		return Owner;
	}

	public void setOwner(Owner owner) {
		Owner = owner;
	}
	
	public Owner getPossibleOwner() {
		return this.possibleOwner;
	}
	
	public void setPossibleOwner(Owner possibleOwner) {
		this.possibleOwner = possibleOwner;
	}
	
	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}