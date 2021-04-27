package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CausaController {
	
	private final CausaService causaService;
	
	private final AuthoritiesService authoritiesService;
	
	@Autowired
	public CausaController(CausaService causaService, AuthoritiesService authoritiesService) {
		this.causaService = causaService;
		this.authoritiesService =authoritiesService;
	}
	
	@ModelAttribute("rol")
	public String populaterol(final Principal principal) {
		return this.authoritiesService.getRol(principal.getName());
	}
	
	@GetMapping(value ="/causas" )
	public String ListCausas(Principal principal, Map<String, Object> model) {
		List<Causa> causas = this.causaService.findAll();
		model.put("causas", causas);
		//model.put("rol", this.authoritiesService.getRol(principal.getName()));
		return "causas/causasList";
	}
	
	@GetMapping(value = "/causas/{causaId}" )
	public String showCausa(@PathVariable("causaId") int causaId, Map<String, Object> model) {
		Causa causa = this.causaService.findById(causaId);
		model.put("causa", causa);
		return "causas/causaShow";
	}
	
	@GetMapping(value = "/causas/new")
	public String initCreationForm(Map<String, Object> model, final Principal principal) {
		Causa causa = new Causa();
		if(this.authoritiesService.getRol(principal.getName()).equals("admin")) {
		model.put("causa", causa);
		return "causas/createCausa";
		}else {
			return "exception";
		}
	}

	@PostMapping(value = "/causas/new")
	public String processCreationForm(@Valid Causa causa, BindingResult result, ModelMap model){
		causa.setRecaudacion(0.0);
		if(result.hasErrors()) {
			model.put("causa", causa);
			System.out.println(result.getAllErrors());
			return "causas/createCausa";
		}else {
			this.causaService.saveCausa(causa);
			return "redirect:/causas"; 
		}
	}
	
}
