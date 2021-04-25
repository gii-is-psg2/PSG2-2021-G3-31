package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Donacion;

public interface DonacionRepository extends Repository<Donacion, Integer>, CrudRepository<Donacion, Integer>{
	
	@Query("SELECT donacion FROM Donacion donacion WHERE donacion.id LIKE ?1")
	Donacion findById(int id) throws DataAccessException;
	
	@Query("SELECT donacion FROM Donacion donacion WHERE donacion.causa.id LIKE ?1")
	List<Donacion> findAllDonacionForCausa(int id) throws DataAccessException;
	
}
