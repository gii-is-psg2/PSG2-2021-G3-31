package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.AdoptionState;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.repository.AdoptionRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.service.exceptions.SolicitudAdopcionDuplicada;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class AdoptionService {

private AdoptionRepository adoptionRepository;
	
	@Autowired
	public AdoptionService(AdoptionRepository adoptionRepository) {
		this.adoptionRepository = adoptionRepository;
	}
	@Transactional
	public void addAdoption(Adoption adoption,int ownerId,boolean isCreate) throws DataAccessException, SolicitudAdopcionDuplicada {
		int numAdopciones=this.adoptionRepository.numOfPendentingAdptionsByOwnerId(ownerId,AdoptionState.PENDIENTE,adoption.getPet().getId());
		if (numAdopciones>0 && isCreate==true)            	
        	throw new SolicitudAdopcionDuplicada();
        else
        	adoptionRepository.save(adoption);
	}
	
	@Transactional(readOnly = true)
	public Collection<Adoption> requestList(int petId) throws DataAccessException {
		return adoptionRepository.findAdoptionByPetId(petId);
	}
	
	@Transactional(readOnly = true)
	public Adoption findAdoptionById(int adpId) throws DataAccessException {
		return adoptionRepository.findAdoptionById(adpId);
	}
	
	@Transactional(readOnly = true)
	public Owner findAdoptionPosibleOwnerByOwnId(int adopId) throws DataAccessException {
		return adoptionRepository.findAdoptionPosibleOwnerByOwnId(adopId);
	}
	
	@Transactional(readOnly = true)
	public List<Adoption> findAdoptionsByOwnerId(int ownerId) throws DataAccessException {
		return adoptionRepository.findAdoptionsByOwnerId(ownerId);
	}
	
	@Transactional
	public void deleteAdoptionById(int id) throws DataAccessException {
		this.adoptionRepository.deleteAdoptionById(id);
	}
	
}
