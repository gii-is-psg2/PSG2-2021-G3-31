package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
public class SpecialitiesFormatter implements Formatter<Specialty>{
	
	
	private final VetService vetService;

	@Autowired
	public SpecialitiesFormatter(VetService vetService) {
		this.vetService = vetService;
	}

	@Override
	public String print(Specialty speciality, Locale locale) {
		return speciality.getName();
	}

	@Override
	public Specialty parse(String text, Locale locale) throws ParseException {
		Collection<Specialty> findEspecialidades = this.vetService.findSpecialties();
		for (Specialty espe : findEspecialidades) {
			if (espe.getName().equals(text)) {
				return espe;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}
