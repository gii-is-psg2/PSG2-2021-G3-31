package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Causa;
import org.springframework.samples.petclinic.model.Donacion;
import org.springframework.samples.petclinic.service.CausaService;
import org.springframework.samples.petclinic.service.DonacionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.exceptions.ObjetivoAlcanzadoException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DonacionController {
	
	private final DonacionService donacionService;
	
	private final OwnerService ownerService;
	
	private final CausaService causaService;
	

	@Autowired
	public DonacionController(DonacionService donacionService, OwnerService ownerService,CausaService causaService) {
		this.donacionService = donacionService;
		this.ownerService=ownerService;
		this.causaService = causaService;
	}
	
	@GetMapping(value = {  "/causas/{causaId}" })
	public String ListDonaciones(@PathVariable("causaId") int causaId,Map<String, Object> model) {
		List<Donacion> donaciones = this.donacionService.findAllDonacionForCausa(causaId);
		model.put("donaciones", donaciones);
		return "donaciones/donacionesList";
	}
	
	@GetMapping(value = { "/causas/{causaId}/donacion/{donacionId}" })
	public String showCausa(@PathVariable("causaId") int causaId, @PathVariable("donacionId") int donacionId,Map<String, Object> model) {
		Donacion donacion = this.donacionService.findById(donacionId);
		model.put("donacion", donacion);
		return "donaciones/donacionShow";
	}
	
	@GetMapping(value = "/causas/{causaId}/donacion/new")
	public String initCreationForm(@PathVariable("causaId") int causaId, @PathVariable("donacionId") int donacionId, final Principal principal,Map<String, Object> model) {
		Donacion donacion = new Donacion();
		donacion.setDonante(this.ownerService.findOwnerByUsername(principal.getName()));
		donacion.setCausa(this.causaService.findById(causaId));
		model.put("donacion", donacion);
		return "donaciones/CreateDonacion";
		
	}

	@PostMapping(value = "/causas/{causaId}/donacion/new")
	public String processCreationForm(@Valid Donacion donacion, BindingResult result, ModelMap model) throws ObjetivoAlcanzadoException{
		Causa causa=donacion.getCausa();
		causa.setRecaudacion(donacion.getCantidadDonada()+donacion.getCausa().getRecaudacion());
		donacion.setFechaDonacion(LocalDate.now());
		if(result.hasErrors()) {
			model.put("donacion", donacion);
			return "causas/CreateCausa";
		}else {
			try {
				this.causaService.saveCausa(causa);
			}catch(ObjetivoAlcanzadoException ex) {
				result.rejectValue("objetivo", "La recaudación no pueden ser mayores al objetivo", 
						"La recaudación no pueden ser mayores al objetivo");
			}
			this.donacionService.saveCausa(donacion);
			return "donaciones/CreateDonacion"; 
		}
	}

}
