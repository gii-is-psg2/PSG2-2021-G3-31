package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/adoptions")
@Controller
public class AdoptionController {

	private final AdoptionService adoptionService;
	private final OwnerService ownerService;
	private final PetService petService;

	@Autowired
	public AdoptionController(final AdoptionService adoptionService, final OwnerService ownerService,
			final PetService petService) {
		super();
		this.adoptionService = adoptionService;
		this.ownerService = ownerService;
		this.petService = petService;
	}

	@GetMapping(value = "/adoptions")
	public ModelAndView getPendingAdoptions(final Principal principal) {
		final Owner owner = this.ownerService.getOwnerByUserName(principal.getName());
		final List<Adoption> adopt = this.adoptionService.getPendingAdoption(owner);
		final ModelAndView mav = new ModelAndView("owners/ownerAdoption");
		mav.addObject("adoptions", adopt);
		mav.addObject("adoptionsNumber", adopt.size());
		return mav;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("owner", "pet");
	}

	@GetMapping(value = "{adoptions_id}/accept")
	public String acceptAdoption(@PathVariable("adoptions_id") final int adoptionsId,
			final Principal principal) throws DataAccessException, DuplicatedPetNameException {
		final Adoption adopt = this.adoptionService.findById(adoptionsId);
		final Owner owner = this.ownerService.getOwnerByUserName(principal.getName());
		if (adopt.getPet().getOwner().getId().equals(owner.getId()))
			this.adoptionService.acceptAdoption(adopt);
		return "redirect:/adoptions/adoptions";
	}

	@GetMapping(value = "{adoptions_id}/decline")
	public String declineAdoption(@PathVariable("adoptions_id") final int adoptionsId,
			final Principal principal) {
		final Adoption adopt = this.adoptionService.findById(adoptionsId);
		final Owner owner = this.ownerService.getOwnerByUserName(principal.getName());
		if (adopt.getPet().getOwner().getId().equals(owner.getId()))
			this.adoptionService.declineAdoptionApplication(adoptionsId);
		return "redirect:/adoptions/adoptions";
	}

	@GetMapping(value = "/pets")
	public String inAdoptionList(ModelMap model) {
		model.addAttribute("pets", petService.findPetsInAdoption());
		return "adoptions/listPetsInAdoption";
	}

	@GetMapping(value = "/pets/{petId}/apply")
	public String createNewAdoption(final Map<String, Object> model,
			@PathVariable("petId") final int petId) {
		final Adoption adoption = new Adoption();
		final Pet adoptedPet = this.petService.findPetById(petId);
		if (adoptedPet == null || !adoptedPet.getinAdoption()) {
			return "redirect:/";
		}

		model.put("adoption", adoption);
		return "adoptions/createAdoption";
	}

	@PostMapping(value = "/pets/{petId}/apply")
	public String postNewAdoption(@Valid final Adoption adoption,
			final BindingResult result, @PathVariable("petId") final int petId, final Principal principal) {
		if (result.hasErrors()) {
			return "adoptions/createAdoption";
		}

		final Owner owner = this.ownerService.getOwnerByUserName(principal.getName());
		adoption.setOwner(owner);
		final Pet adoptedPet = this.petService.findPetById(petId);
		if (adoptedPet == null) {
			result.rejectValue("description", "petDoesntExist");
			return "adoptions/createAdoption";
		} else if (!adoptedPet.getinAdoption().booleanValue()) {
			result.rejectValue("description", "notAdoptablePet");
			return "adoptions/createAdoption";
		} else if (this.adoptionService.findByOwnerAndPet(adoption.getOwner(),
				adoptedPet) != null) {
			result.rejectValue("description", "alreadyApplied");
			return "adoptions/createAdoption";
		} else if (adoptedPet.getOwner().equals(owner)) {
			result.rejectValue("description", "alreadyOwned");
			return "adoptions/createAdoption";
		}

		adoption.setPet(adoptedPet);
		this.adoptionService.save(adoption);
		return "redirect:/";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
