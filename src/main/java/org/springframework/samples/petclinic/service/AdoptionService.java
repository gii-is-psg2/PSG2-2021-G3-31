package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.EstadoAdopcion;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.AdoptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionService {

private AdoptionRepository adoptionRepository;
	
	@Autowired
	public AdoptionService(AdoptionRepository adoptionRepository) {
		this.adoptionRepository = adoptionRepository;
	}
	
	@Transactional
	public void saveAdoption(Adoption adoption) throws DataAccessException {
		adoptionRepository.save(adoption);
	}
	
	@Transactional(readOnly = true)
	public Adoption findAdoptionById(int id) throws DataAccessException {
		return adoptionRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Adoption> findAll(){
		return adoptionRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Integer findAdoptionByPossibleOwnerAndPet(String possibleOwner,Pet pet) throws DataAccessException {
		return adoptionRepository.findByPossibleOwnerAndPet(possibleOwner,pet);
	}

	@Transactional(readOnly = true)
	public List<Adoption> findAllAdoptionsWithPendingState(List<Adoption> adoptions){
		List<Adoption> res = new ArrayList<Adoption>();
		for (Adoption adoption: adoptions) {
			if(adoption.getEstadoAdopcion().getName().equals("Pendiente")) {
				res.add(adoption);
			}
		}
		return res;
	}
	
	@Transactional
	public void acceptAdoptionApplication(Adoption adoption) throws DataAccessException {
		EstadoAdopcion estado = this.adoptionRepository.findEstadoById(2);
		adoption.setEstadoAdopcion(estado);
		this.adoptionRepository.save(adoption);
	}
	
	@Transactional
	public void denyAdoptionApplication(Adoption adoption) throws DataAccessException{
		EstadoAdopcion estado = this.adoptionRepository.findEstadoById(4);
		adoption.setEstadoAdopcion(estado);
		this.adoptionRepository.save(adoption);
	}
	
	public EstadoAdopcion findEstadoById(int id) {
		return this.adoptionRepository.findEstadoById(id);
	}
}
