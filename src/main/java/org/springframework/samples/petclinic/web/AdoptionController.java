package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.AdoptionState;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.service.exceptions.SolicitudAdopcionDuplicada;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	@ModelAttribute("pendiente")
	public AdoptionState populaterol() {
		return AdoptionState.PENDIENTE;
	}

	@GetMapping()
	public String adoptionList(ModelMap modelMap, Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		Owner possibleOwner = this.ownerService.findOwnerByUsername(user.getUsername());
		Iterable<Pet> adoptionPets = this.petService.adoptionPetList();
		modelMap.addAttribute("possibleOwner", possibleOwner);
		modelMap.addAttribute("adoptionPets", adoptionPets);
		return "adopciones/adopcionesList";
	}
	@GetMapping(value = "/{petId}/adoptionForm")
	public String adoptionForm(@PathVariable("petId") int petId, Map<String, Object> modelMap, Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		Owner possibleOwner = this.ownerService.findOwnerByUsername(user.getUsername());
		Owner owner = this.petService.findPetById(petId).getOwner();
		modelMap.put("possibleOwnerUser", possibleOwner.getUser().getUsername());
		modelMap.put("ownerUser", owner.getUser().getUsername());
		modelMap.put("adoption",new Adoption());
		return "/adopciones/adopcionesForm";
		
	}
	@PostMapping(value = "/{petId}/adoptionForm")
	public String sendApplicationForm(@PathVariable("petId") int petId, Map<String, Object> modelMap, Authentication authentication, @Valid Adoption adoption, BindingResult result)
			throws DataAccessException, SolicitudAdopcionDuplicada {
		Pet pet = this.petService.findPetById(petId);
		UserDetails user = (UserDetails) authentication.getPrincipal();
		Owner possibleOwner = this.ownerService.findOwnerByUsername(user.getUsername());
		Owner owner = this.petService.findPetById(petId).getOwner();
		if (result.hasErrors()) {
			System.out.print(result.getAllErrors());
			modelMap.put("possibleOwnerUser", possibleOwner.getUser().getUsername());
			modelMap.put("ownerUser", owner.getUser().getUsername());
			return "/adopciones/adopcionesForm";
		}else {
			adoption.setOwner(owner);
			adoption.setPossibleOwner(possibleOwner);
			adoption.setPet(pet);
			adoption.setAdoptionStatus(AdoptionState.PENDIENTE);
			modelMap.put("adoption",adoption);
			try{
				this.adoptionService.addAdoption(adoption,possibleOwner.getId(),true);
            }catch(SolicitudAdopcionDuplicada ex){
                result.rejectValue("description", "Ya ha realizado una solicitud que no se ha evaluado, por favor espere a que sea aceptada",
                		"Ya ha realizado una solicitud que no se ha evaluado, por favor espere a que sea aceptada");
                return "/adopciones/adopcionesForm";
            }
			return "redirect:/adoptions";	
			}
		}
	
	@GetMapping(value = "/{petId}/requestList")
	public String requestList(@PathVariable("petId") int petId, Map<String, Object> modelMap, Authentication authentication) {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		Pet pet =this.petService.findPetById(petId);
		if(user.getUsername().equals(pet.getOwner().getUser().getUsername())) {
		Iterable<Adoption> requestList = this.adoptionService.requestList(petId);
		modelMap.put("requestList", requestList);
		return "/adopciones/solicitudesList";
		}else {
		return "exception";
		}
	}
	
	@GetMapping(value = "/{petId}/requestList/{adopcionId}/accept")
	public String aceptRequest(@PathVariable("petId") int petId, @PathVariable("adopcionId") int adopcionId, Map<String, Object> modelMap, Authentication authentication) throws DataAccessException, DuplicatedPetNameException {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		Pet pet = this.petService.findPetById(petId);
		Owner ow1 =this.ownerService.findOwnerByUsername(user.getUsername());
		if(user.getUsername().equals(pet.getOwner().getUser().getUsername())) {
		Adoption adop = this.adoptionService.findAdoptionById(adopcionId);
		adop.setAdoptionStatus(AdoptionState.ACEPTADA);
		List<Adoption> adops=(List<Adoption>) this.adoptionService.requestList(petId);
		for(int i=0;i<adops.size();i++) {
			this.adoptionService.deleteAdoptionById(adops.get(i).getId());
		}
		Owner ow2 = adop.getPossibleOwner();
		ow1.removePet(pet);
		ow2.addPet(pet);
		pet.setAdoption(false);
		pet.setOwner(ow2);
		this.ownerService.saveOwnerOnly(ow1);
        this.ownerService.saveOwnerOnly(ow2);
		this.petService.savePet(pet);
		return "redirect:/adoptions";
		}else {
		return "exception";
		}
	}
	
	@GetMapping(value = "/{petId}/requestList/{adopcionId}/reject")
	public String rejectRequest(@PathVariable("petId") int petId, @PathVariable("adopcionId") int adopcionId, Map<String, Object> modelMap, Authentication authentication) throws DataAccessException, DuplicatedPetNameException {
		UserDetails user = (UserDetails) authentication.getPrincipal();
		Pet pet = this.petService.findPetById(petId);
		if(user.getUsername().equals(pet.getOwner().getUser().getUsername())) {
		Adoption adop = this.adoptionService.findAdoptionById(adopcionId);
		adop.setAdoptionStatus(AdoptionState.DENEGADA);
		try{
			this.adoptionService.addAdoption(adop,adop.getPossibleOwner().getId(),false);
        }catch(SolicitudAdopcionDuplicada ex){
        }
		return "redirect:/adoptions";
		}else {
		return "exception";
		}
	}
}
