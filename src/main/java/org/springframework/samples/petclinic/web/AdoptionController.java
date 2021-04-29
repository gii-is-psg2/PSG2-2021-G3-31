package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.EstadoAdopcion;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adoptions")
public class AdoptionController {
	
	private final AdoptionService adoptionService;

	private final PetService petService;

	private final OwnerService ownerService;

	@Autowired
	public AdoptionController(AdoptionService adoptionService, PetService petService, OwnerService ownerService) {
		this.adoptionService = adoptionService;
		this.petService = petService;
		this.ownerService = ownerService;
	}

	@GetMapping()
	public String adoptionList(ModelMap modelMap, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Owner possibleOwner = this.ownerService.findOwnerByUsername(userDetails.getUsername());
		modelMap.addAttribute("possibleOwner", possibleOwner);

		String view = "adoptions/adoptionList";
		Iterable<Pet> pets = this.petService.findPetsInAdoption();
		
		modelMap.addAttribute("pets", pets);
		return view;
	}
	
	@GetMapping(value="/pendingAdoptionsList")
	public String pendingAdoptionList(ModelMap modelMap) {
		EstadoAdopcion estado = this.adoptionService.findEstadoById(1);
		modelMap.addAttribute("pendingAdoption", estado);
		List<Adoption> adoptions = (List<Adoption>)this.adoptionService.findAll();
		modelMap.addAttribute("adoptions", this.adoptionService.findAllAdoptionsWithPendingState(adoptions));
		return "adoptions/stateAdoptionList";
	}
	
	@GetMapping(value="/allAdoptionsList")
	public String allAdoptionList(ModelMap modelMap/***/, Authentication authentication/***/) {
		EstadoAdopcion estado = this.adoptionService.findEstadoById(1);
		modelMap.addAttribute("pendingAdoption", estado);
		modelMap.addAttribute("adoptions", this.adoptionService.findAll());
		
		//////
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Owner possibleOwner = this.ownerService.findOwnerByUsername(userDetails.getUsername());
		modelMap.addAttribute("possibleOwner", possibleOwner);
		//////
		return "adoptions/stateAdoptionList";
	}

	@GetMapping(value = "/{petId}/applicationForm")
	public String initApplyForm(Map<String, Object> model, Authentication authentication,
			@PathVariable("petId") int petId) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Owner possibleOwner = this.ownerService.findOwnerByUsername(userDetails.getUsername());

		if (possibleOwner == null) {
			return "redirect:/login";
			
		} else {

			String possibleOwnerName = possibleOwner.getUser().getUsername();

			Owner owner = this.petService.findPetById(petId).getOwner();

			Adoption adoption = new Adoption();
			adoption.setOwner(owner);
			adoption.setPossibleOwner(possibleOwnerName);
			Pet pet = this.petService.findPetById(petId);
			adoption.setPet(pet);
			model.put("adoption", adoption);
			model.put("pet", pet);
			return "/adoptions/applicationForm";
		}
	}

	@PostMapping(value = "/{petId}/applicationForm")
	public String sendApplicationForm(@PathVariable("petId") int petId,@Valid Adoption adoption, BindingResult result, 
			Map<String, Object> model, Principal principal) throws DataAccessException, DuplicatedPetNameException {
		Pet pet = this.petService.findPetById(petId);
		Owner owner = this.petService.findPetById(petId).getOwner();
		Owner possibleOwner = this.ownerService.findOwnerByUsername(principal.getName());
		adoption.setOwner(owner);
		adoption.setPossibleOwner(possibleOwner.getUser().getUsername());
		model.put("pet", pet);
		
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());

			return "/adoptions/applicationForm";
		} else {
			Integer alreadyExists = adoptionService.findAdoptionByPossibleOwnerAndPet(possibleOwner.getUser().getUsername()
					, pet);
			if(alreadyExists>=1){
				result.rejectValue("description", "Ya se ha solicitado una adopcón para esta mascota", 
						"Ya se ha solicitado una adopcón para esta mascota");
				return "/adoptions/applicationForm";
			}else {
				EstadoAdopcion estado = this.adoptionService.findEstadoById(1);
				adoption.setEstadoAdopcion(estado);
				pet.addAdoption(adoption);

				this.adoptionService.saveAdoption(adoption);
				this.petService.savePet(pet);

				return "redirect:/adoptions/allAdoptionsList";	
				}
		}
	}
	
	
	@GetMapping(value="/accept/{adoptionId}")
	public String acceptAdoptionApplication(@PathVariable("adoptionId") int adoptionId, Principal principal,
		Map<String, Object> model) throws Exception {
		Adoption adoption = this.adoptionService.findAdoptionById(adoptionId);
		
		this.adoptionService.acceptAdoptionApplication(adoption);
		
		Owner possibleOwner = this.ownerService.findOwnerByUsername(adoption.getPossibleOwner());
		Owner owner = this.ownerService.findOwnerByUsername(adoption.getOwner().getUser().getUsername());
		Pet pet = adoption.getPet();
		
		owner.removePet(pet);
		possibleOwner.addPet(pet);
		pet.setInAdoption(false);
		this.adoptionService.acceptAdoptionApplication(adoption);		
		
		this.ownerService.saveOwner(owner);
		this.ownerService.saveOwner(possibleOwner);
		this.petService.savePet(pet);
		return "redirect:/adoptions/pendingAdoptionsList";
	}
	
	@GetMapping(value="/deny/{adoptionId}")
	public String denyAdoptionApplication(@PathVariable("adoptionId") int adoptionId, Authentication authentication,
		Map<String, Object> model) throws Exception {
		Boolean authenticated = authentication.isAuthenticated();
		Adoption adoption = this.adoptionService.findAdoptionById(adoptionId);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Owner existingOwner = this.ownerService.findOwnerByUsername(userDetails.getUsername());
		System.out.println("---------------------------------------------");
		System.out.println(existingOwner);
		System.out.println("---------------------------------------------");

		
		if(existingOwner==null || !authenticated){
			return "welcome";
		}else {
			this.adoptionService.denyAdoptionApplication(adoption);
			EstadoAdopcion estado = this.adoptionService.findEstadoById(1);
			model.put("pendingAdoption", estado);
			return "redirect:/adoptions/pendingAdoptionsList";
		}	
	}
	
	
	/////// AUX ////////
	@GetMapping(value="/createAdoption")
	public String createAdoption() {
		return "adoptions/createAdoption";
	}
	
	////////////////////
}
