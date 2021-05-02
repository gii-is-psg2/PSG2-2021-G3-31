package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Causa;


public interface CausaRepository extends Repository<Causa, Integer>, CrudRepository<Causa, Integer>{
	
	@Query("SELECT causa FROM Causa causa WHERE causa.id LIKE ?1")
	Causa findCausaById(int id) throws DataAccessException;
	
	Collection<Causa> findAll() throws DataAccessException;

}
