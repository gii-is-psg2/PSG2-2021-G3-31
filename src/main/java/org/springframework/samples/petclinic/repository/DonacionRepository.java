package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Donacion;

public interface DonacionRepository extends Repository<Donacion, Integer>, CrudRepository<Donacion, Integer>{
	
	@Query("SELECT donacion FROM donacion causa WHERE donacion.id =:id")
	public Donacion findById(@Param("id") int id) throws DataAccessException;
	
	@Query("SELECT donacion FROM donacion causa WHERE donacion.causa.id =:id")
	List<Donacion> findAllDonacionForCausa(@Param("id") int id) throws DataAccessException;
	
}
