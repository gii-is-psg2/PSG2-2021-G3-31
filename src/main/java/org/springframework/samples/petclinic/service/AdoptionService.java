package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.AdoptionRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;

@Service
public class AdoptionService {

	private final AdoptionRepository adoptionRepository;
	private final PetService petService;

	@Autowired
	public AdoptionService(final AdoptionRepository adoptionRepository, final PetService petService) {
		super();
		this.adoptionRepository = adoptionRepository;
		this.petService = petService;
	}

	public List<Adoption> getPendingAdoption(final Owner owner) {
		return this.adoptionRepository.getPendingAdoption(owner.getId());
	}

	public Adoption findById(final int adoptionId) {
		return this.adoptionRepository.findById(adoptionId).orElse(null);
	}

	public void save(final Adoption adoption) {
		this.adoptionRepository.save(adoption);
	}

	public Adoption findByOwnerAndPet(final Owner owner, final Pet pet) {
		return this.adoptionRepository.findByOwnerAndPet(owner, pet);
	}

	public void acceptAdoption(final Adoption adopt)
			throws DataAccessException, DuplicatedPetNameException {
		final Pet pet = adopt.getPet();
		final Owner newOwner = adopt.getOwner();
		pet.setinAdoption(false);
		pet.setOwner(newOwner);
		this.petService.savePet(pet);


		final List<Adoption> adoptionByPet = this.adoptionRepository
				.findAdoptionByPet(pet.getId());
		for (final Adoption adoption : adoptionByPet) {
			this.adoptionRepository.delete(adoption);
		}
	}

	public void declineAdoptionApplication(final int adoptionId) {
		this.adoptionRepository.deleteById(adoptionId);
	}

	public List<Adoption> getRequestsByOwner(final int ownerId) {
		return this.adoptionRepository.getRequestByOwner(ownerId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
