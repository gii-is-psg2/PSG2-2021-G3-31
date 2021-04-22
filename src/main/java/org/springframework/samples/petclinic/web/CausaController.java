package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CausaController {
	
	private final CausaService causaService;
	
	private final AuthoritiesService authoritiesService;
	
	@Autowired
	public CausaController(CausaService causaService, AuthoritiesService authoritiesService) {
		this.causaService = causaService;
		this.authoritiesService =authoritiesService;
	}
	
	@GetMapping(value = { "/causas" })
	public String ListCausas(Map<String, Object> model) {
		List<Causa> causas = this.causaService.findAll();
		model.put("causas", causas);
		return "causas/causasList";
	}
	
	@GetMapping(value = { "/causas/{causaId}" })
	public String showCausa(final Principal principal,@PathVariable("causaId") int causaId,Map<String, Object> model) {
		Causa causa = this.causaService.findById(causaId);
		model.put("causa", causa);
		return "causas/causaShow";
	}
	
}
