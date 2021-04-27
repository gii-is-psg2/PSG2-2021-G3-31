package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.repository.CausaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CausaService {
	
	private CausaRepository causaRepository;
	
	@Autowired
	public CausaService(CausaRepository causaRepository) {
		this.causaRepository = causaRepository;
	}
	
	@Transactional(readOnly = true)
	public Causa findById(int id) throws DataAccessException{
		return this.causaRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public List<Causa> findAll() throws DataAccessException{
		return this.causaRepository.findAll();
	}
	
	@Transactional
	public void saveCausa(Causa causa) throws DataAccessException{
			this.causaRepository.save(causa);
	}
	
	
}
