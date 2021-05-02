package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.transaction.annotation.Transactional;

public interface AdoptionRepository extends Repository<Adoption, Integer>, CrudRepository<Adoption, Integer>  {
	
	@Query("SELECT adoptions FROM Adoption adoptions WHERE adoptions.pet.id = :petId")
	public Collection<Adoption> findAdoptionByPetId(@Param("petId") int petId);
	
	@Query("SELECT adoption FROM Adoption adoption WHERE adoption.id LIKE ?1")
	public Adoption findAdoptionById(int adpId);
	
	@Query("SELECT adoption.possibleOwner FROM Adoption adoption WHERE adoption.id LIKE ?1")
	public Owner findAdoptionPosibleOwnerByOwnId(int adopId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Adoption adoption WHERE adoption.id =:id")
	public void deleteAdoptionById(@Param("id")int id);

		
}
