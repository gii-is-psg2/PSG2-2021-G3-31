package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "adoptions")
public class Adoption extends BaseEntity{
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "owner_id")
	private Owner Owner;
	
	@NotEmpty
	@Column(name = "possible_owner")
	private String possibleOwner;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	@NotEmpty
	@Column(name = "description")
	private String description;
	
	@NotEmpty
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
	
	public String getPossibleOwner() {
		return this.possibleOwner;
	}
	
	public void setPossibleOwner(String possibleOwner) {
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