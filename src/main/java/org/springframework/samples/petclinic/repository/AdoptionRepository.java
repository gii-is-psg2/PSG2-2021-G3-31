package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;

public interface AdoptionRepository extends CrudRepository<Adoption, Integer> {

	@Query("select r from Adoption r where r.pet.owner.id =:ownerId")
	public List<Adoption> getPendingAdoption(@Param("ownerId") Integer ownerId);

	@Query("select a from Adoption a where a.pet.id =:petId")
	public List<Adoption> findAdoptionByPet(@Param("petId") Integer petId);

	@Query("select a from Adoption a where a.owner.id =:ownerId")
	public List<Adoption> getRequestByOwner(@Param("ownerId") Integer ownerId);

	public Adoption findByOwnerAndPet(Owner owner, Pet pet);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
