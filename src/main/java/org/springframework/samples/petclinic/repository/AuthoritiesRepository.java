package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Authorities;



public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{
	
	@Query("SELECT  authorities.authority FROM Authorities authorities WHERE authorities.user.username LIKE ?1")
	String getRol(String usurname) throws DataAccessException;
}
