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
import org.springframework.stereotype.Controller;
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
	
	@GetMapping(value = "/causas/{causaId}/donaciones")
	public String ListDonaciones(@PathVariable("causaId") int causaId,Map<String, Object> model) {
		List<Donacion> donaciones = this.donacionService.findAllDonacionForCausa(causaId);
		model.put("donaciones", donaciones);
		return "donaciones/donacionesList";
	}
	
	@GetMapping(value = "/causas/{causaId}/donaciones/{donacionId}")
	public String showCausa(@PathVariable("causaId") int causaId, @PathVariable("donacionId") int donacionId,Map<String, Object> model) {
		Donacion donacion = this.donacionService.findById(donacionId);
		model.put("donacion", donacion);
		return "donaciones/donacionShow";
	}
	
	@GetMapping(value = "/causas/{causaId}/donaciones/new")
	public String initCreationForm(@PathVariable("causaId") int causaId, final Principal principal,Map<String, Object> model) {
		Donacion donacion = new Donacion();
		donacion.setDonante(this.ownerService.findOwnerByUsername(principal.getName()));
		model.put("donacion", donacion);
		return "donaciones/createDonacion";
		
	}

	@PostMapping(value = "/causas/{causaId}/donaciones/new")
	public String processCreationForm(@PathVariable("causaId") int causaId,final Principal principal, @Valid Donacion donacion, BindingResult result, Map<String, Object> model){
		donacion.setFechaDonacion(LocalDate.now());
		donacion.setDonante(this.ownerService.findOwnerByUsername(principal.getName()));
		Causa causa = this.causaService.findById(causaId);
		if(donacion.getCantidadDonada()==null) {
			result.rejectValue("cantidadDonada", "Este campo no puede ser nulo", 
					"Este campo no puede ser nulo");
			return "donaciones/createDonacion";
		}else {
			double suma = donacion.getCantidadDonada()+causa.getRecaudacion();
			if(suma>causa.getObjetivo()) {
				result.rejectValue("cantidadDonada", "La recaudación no pueden ser mayores al objetivo", 
						"La recaudación no pueden ser mayores al objetivo");
				return "donaciones/createDonacion";
			}else {
				causa.setRecaudacion(suma);
				donacion.setCausa(causa);
				if(result.hasErrors()) {
				model.put("donacion", donacion);
				System.out.println(result.getAllErrors());
				return "donaciones/createDonacion";
				}else {
					this.causaService.saveCausa(causa);
					this.donacionService.saveDonacion(donacion);
				return "redirect:/causas"; 
				}
			}
		}
		
	}

}

