package org.springframework.samples.petclinic.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Adoption;


public interface AdoptionRepository extends CrudRepository<Adoption, Integer> {

	@Query("SELECT adoption FROM Adoption adoption WHERE adoption.pet.id =:id")
	public List<Adoption> findByPetId(@Param("id") Integer id);
	
	@Query("SELECT adoption FROM Adoption adoption WHERE adoption.owner.id =:id")
	public List<Adoption> findByOwnerId(@Param("id") int id);
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
