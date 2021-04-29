package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.EstadoAdopcion;
import org.springframework.samples.petclinic.model.Pet;

public interface AdoptionRepository extends CrudRepository<Adoption, Integer>  {
	
		Adoption findById(int id) throws DataAccessException;

		@Query("SELECT count(adopcion.id) FROM Adoption adopcion WHERE adopcion.possibleOwner LIKE ?1 AND adopcion.pet LIKE ?2")
		Integer findByPossibleOwnerAndPet(String possibleOwner, Pet pet) throws DataAccessException;
		
		@Query("SELECT estadoAdopcion FROM EstadoAdopcion estadoAdopcion WHERE estadoAdopcion.id LIKE ?1")
		EstadoAdopcion findEstadoById(int id) throws DataAccessException;
}
