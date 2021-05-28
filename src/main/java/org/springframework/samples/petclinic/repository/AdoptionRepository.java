package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.AdoptionState;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.transaction.annotation.Transactional;

public interface AdoptionRepository extends Repository<Adoption, Integer>, CrudRepository<Adoption, Integer>  {
	
	@Query("SELECT adoptions FROM Adoption adoptions WHERE adoptions.pet.id = :petId")
	public Collection<Adoption> findAdoptionByPetId(@Param("petId") int petId);
	
	@Query("SELECT adoption FROM Adoption adoption WHERE adoption.id LIKE ?1")
	public Adoption findAdoptionById(int adpId);
	
	@Query("SELECT adoption.possibleOwner FROM Adoption adoption WHERE adoption.id LIKE ?1")
	public Owner findAdoptionPosibleOwnerByOwnId(int adopId);
	
	@Query("SELECT count(adoption.id) FROM Adoption adoption WHERE adoption.possibleOwner.id LIKE ?1 AND adoption.adoptionStatus LIKE ?2 AND adoption.pet.id LIKE ?3")
	public int numOfPendentingAdptionsByOwnerId(int ownerId,AdoptionState pen, int petId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Adoption adoption WHERE adoption.id =:id")
	public void deleteAdoptionById(@Param("id")int id);

		
}
