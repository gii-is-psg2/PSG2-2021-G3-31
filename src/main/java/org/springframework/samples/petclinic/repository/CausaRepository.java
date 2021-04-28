package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Causa;


public interface CausaRepository extends Repository<Causa, Integer>, CrudRepository<Causa, Integer>{
	
	@Query("SELECT causa FROM Causa causa WHERE causa.id =:id")
	public Causa findById(@Param("id") int id) throws DataAccessException;
	
	List<Causa> findAll() throws DataAccessException;

}
