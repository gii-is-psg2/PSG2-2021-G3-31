package org.springframework.samples.petclinic.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
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
	
	@Transactional(readOnly = true)
	public List<Adoption> findByPetId(Integer id) throws DataAccessException {
		return adoptionRepository.findByPetId(id);
	}
	
	@Transactional(readOnly = true)
	public List<Adoption> findByOwnerId(Integer id) throws DataAccessException {
		return adoptionRepository.findByOwnerId(id);
	}
	
	@Transactional
    public Adoption saveAdoption(Adoption adoption) throws DataAccessException {
           return adoptionRepository.save(adoption);
    }

	@Transactional
    public void deleteAdoption(Adoption adoption) throws DataAccessException {
        adoptionRepository.delete(adoption);
    }
	
	@Transactional
    public Adoption findById(Integer id) throws DataAccessException {
           return adoptionRepository.findById(id).get();
    }
	
	@Transactional
    public Iterable<Adoption> findAll() throws DataAccessException {
           return adoptionRepository.findAll();
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
