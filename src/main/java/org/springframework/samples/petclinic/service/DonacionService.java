package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.repository.DonacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DonacionService {
	
	private DonacionRepository donacionRepository;
	
	@Autowired
	public DonacionService(DonacionRepository donacionRepository) {
		this.donacionRepository = donacionRepository;
	}
	
	@Transactional(readOnly = true)
	public Donacion findById(int id) throws DataAccessException{
		return this.donacionRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public List<Donacion> findAllDonacionForCausa(int id) throws DataAccessException{
		return this.donacionRepository.findAllDonacionForCausa(id);
	}
	
	@Transactional
	public void saveDonacion(Donacion donacion) throws DataAccessException{
			this.donacionRepository.save(donacion);
	}
	
	@Transactional
	public List<Donacion> findAllDonacionForOwner(int id) throws DataAccessException{
			return this.donacionRepository.findAllDonacionForOwner(id);
	}
	@Transactional
	public void deleteDonacion(Donacion donacion) throws DataAccessException{
			this.donacionRepository.delete(donacion);
	}
}
