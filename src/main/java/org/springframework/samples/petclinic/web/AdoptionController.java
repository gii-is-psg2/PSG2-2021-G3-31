package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	private static final String VIEWS_OWNER_PETS_IN_PROCESS_TO_BE_ADOPTED = "adoptions/petsInProcessToBeAdoptedList";
	private static final String VIEWS_ADOPTION_REQUESTS = "adoptions/createAdoptionRequestForm";

	

	@Autowired
	private final AdoptionService adoptionService;
	
	@Autowired
	private final PetService petService;
	
	@Autowired
	private final OwnerService ownerService;

	@Autowired
	public AdoptionController(AdoptionService adoptionService, PetService petService, OwnerService ownerService
			) {
		this.adoptionService = adoptionService;
		this.petService = petService;
		this.ownerService = ownerService;
	}
	
	@GetMapping()
	public String initAdoptionList(Map<String, Object> model) {
		Owner owner = new Owner();
		model.put("owner", owner);
		List<Pet> allAdoptablePets = this.petService.findAdoptablePets();
		model.put("pets", allAdoptablePets);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    List<? extends GrantedAuthority> rolList = auth.getAuthorities().stream().collect(Collectors.toList());
	    String rol = rolList.get(0).getAuthority();
	    if(rol.equals("owner")){
		    model.put("owners", this.ownerService.findOwnerByUsername(name).getId());
		    List<Integer> idList= new ArrayList<>();
		    for(Adoption a: this.adoptionService.findByOwnerId(this.ownerService.findOwnerByUsername(name).getId())) {
		    	idList.add(a.getPet().getId());
		    }
		    model.put("adoptions",idList);
	    }
	    
		return VIEWS_OWNER_PETS_IN_PROCESS_TO_BE_ADOPTED;
	}
	@GetMapping(value="/newRequest/{petId}")
	public String initCreationRequest(ModelMap modelMap, @PathVariable("petId") int petId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName();
	    modelMap.addAttribute("owners", this.ownerService.findOwnerByUsername(name).getId());
	    modelMap.addAttribute("pets", petId);
	    modelMap.addAttribute("adoptions", new Adoption());
		return VIEWS_ADOPTION_REQUESTS;
	}
	@PostMapping(value = "/newRequest/{petId}")
	public String processCreationForm(Owner owner, @Valid Adoption adoption, BindingResult result, ModelMap model) {		
		if (result.hasErrors()) {
			model.put("adoptions", adoption);
			return VIEWS_ADOPTION_REQUESTS;
		}
		else {
			
			this.adoptionService.saveAdoption(adoption);
            return "redirect:/adoptions";
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
