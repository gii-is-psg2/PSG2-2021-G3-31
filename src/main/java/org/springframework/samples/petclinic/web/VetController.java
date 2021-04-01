/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
public class VetController {

	private final VetService vetService;
	
	private final AuthoritiesService authoritiesService;

	@Autowired
	public VetController(VetService clinicService, AuthoritiesService authoritiesService) {
		this.vetService = clinicService;
		this.authoritiesService =authoritiesService;
	}
	


	@GetMapping(value = { "/vets" })
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}
	
	@GetMapping(value = { "/vet" })
	public String showVetListForVets(Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		model.put("vets", vets);
		return "vets/listForVet";
	}

	@GetMapping(value = { "/vets.xml"})
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a collection of Vet
		// objects
		// so it is simpler for JSon/Object mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetService.findVets());
		return vets;
	}
	
	
	@GetMapping(value = "/vet/new")
	public String initCreationForm(Map<String, Object> model) {
		Vet vet = new Vet();
		model.put("vet", vet);
		model.put("specialties", this.vetService.findSpecialties());
		return "vets/CreateOrEditVet";
	}

	@PostMapping(value = "/vet/new")
	public String processCreationForm(@Valid Vet vet, BindingResult result, ModelMap model,@RequestParam(value="specialties") Specialty[] specialties) {
		if (this.vetService.usuarioRegistrado(vet.getFirstName(),vet.getLastName())==1) {
			result.rejectValue("firstName", "Usuario ya registrado", "Usuario ya registrado");
			return "vets/CreateOrEditVet";
		}else if(result.hasErrors()) {
			model.put("vet", vet);
			return "vets/CreateOrEditVet";
		}else if(this.vetService.nombreUsuarioRegistrado(vet.getUser().getUsername())==1) {
			result.rejectValue("user.username", "Nombre de usuario ya registrado", "Nombre de usuario ya registrado");
			return "vets/CreateOrEditVet";
		}
		else if(vet.getUser().getUsername()==null || vet.getUser().getUsername().isEmpty()) {
			result.rejectValue("user.username", "Debe indicar un nombre de usuario", "Debe indicar un nombre de usuario");
			return "vets/CreateOrEditVet";
		}else if(vet.getUser().getPassword()==null || vet.getUser().getPassword().isEmpty()) {
			result.rejectValue("user.password", "Debe indicar una contraseña", "Debe indicar una contraseña");
			return "vets/CreateOrEditVet";
		}
		else {
			for(int i=0;i<specialties.length;i++) {
				vet.addSpecialty(specialties[i]);
			}
			this.vetService.saveVet(vet);
			return "redirect:/vet";
		}
	}
	
	@GetMapping(value = "/vet/{vetId}/edit")
	public String initEditForm(final Principal principal,@PathVariable("vetId") int vetId,Map<String, Object> model) {
		Vet vet = this.vetService.findVetById(vetId);
		if(vet.equals(this.vetService.findVetByUsername(principal.getName()))||this.authoritiesService.getRol(principal.getName()).equals("admin")) {
			model.put("vet", vet);
			model.put("specialties", this.vetService.findSpecialties());
			return "vets/CreateOrEditVet";
		}else {
			return "exception";	
		}
	}
	
	@PostMapping(value = "/vet/{vetId}/edit")
	public String processEditForm(final Principal principal,@PathVariable("vetId") int vetId,@Valid Vet vet, BindingResult result, Map<String, Object> model,
		 @RequestParam(value="specialties") Specialty[] specialties) {
		vet.setId(vetId);
		Vet vetGuardado= this.vetService.findVetById(vetId);
		if (result.hasErrors()) {
			model.put("vet", vet);
			model.put("specialties", this.vetService.findSpecialties());
			return "vets/CreateOrEditVet";
		}
		else if(vet.getUser().getPassword()==null || vet.getUser().getPassword().isEmpty()) {
			result.rejectValue("user.password", "Debe indicar una contraseña", "Debe indicar una contraseña");
			return "vets/CreateOrEditVet";
		}else if(this.vetService.nombreUsuarioRegistrado(vet.getUser().getUsername())>=1&&!vet.getUser().getUsername().equals(vetGuardado.getUser().getUsername())) {
			result.rejectValue("user.username", "Nombre de usuario ya registrado", "Nombre de usuario ya registrado");
			return "vets/CreateOrEditVet";
		}
		else {
			for(int i=0;i<specialties.length;i++) {
				vet.addSpecialty(specialties[i]);
			}
			this.vetService.saveVet(vet);
			return "redirect:/vet";
		}
	}
	/*
	@GetMapping("/vets/{vetId}/delete")
    public String deleteVet(@PathVariable("vetId") int vetId, ModelMap model) {
        Optional<Vet> vet =this.vetService.findVetByIdOpt(vetId);
        if (vet.isPresent()) {
            this.vetService.deleteVet(vet.get());
            model.addAttribute("message","Veterinario eliminado correctamente.");
        }else {
			model.addAttribute("message", "Veterinario no encontrado.");
		}
        return showVetList(model);
    }*/
	
	@GetMapping("/vet/{vetId}/delete")
    public String deleteVetForVet(@PathVariable("vetId") int vetId, ModelMap model) {
        Optional<Vet> vet =this.vetService.findVetByIdOpt(vetId);
        if (vet.isPresent()) {
            this.vetService.deleteVet(vet.get());
            model.addAttribute("message","Veterinario eliminado correctamente.");
        }else {
			model.addAttribute("message", "Veterinario no encontrado.");
		}
        return "redirect:/vet";
    }
}
