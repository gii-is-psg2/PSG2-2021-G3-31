package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.repository.CausaRepository;
import org.springframework.samples.petclinic.service.exceptions.ObjetivoAlcanzadoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CausaService {
	
	private CausaRepository causaRepository;
	
	@Transactional(readOnly = true)
	public Causa findById(int id) throws DataAccessException{
		return this.causaRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public List<Causa> findAll() throws DataAccessException{
		return this.causaRepository.findAll();
	}
	
	@Transactional
	public void saveCausa(Causa causa) throws DataAccessException, ObjetivoAlcanzadoException{
		if(causa.getObjetivo()<causa.getRecaudacion()) {
			throw new ObjetivoAlcanzadoException();
		}else {
			this.causaRepository.save(causa);
		}
	}
	
	
}
