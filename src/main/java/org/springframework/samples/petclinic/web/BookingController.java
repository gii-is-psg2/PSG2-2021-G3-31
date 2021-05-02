package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedFechaEntradaPetBookingException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedFechaSalidaPetBookingException;
import org.springframework.samples.petclinic.service.exceptions.InvalidDatePetBookingException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookingController {
	
	private final PetService petService;
	
	@Autowired
	public BookingController(PetService petService) {
		this.petService = petService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

//	@ModelAttribute("booking")
//	public Booking loadPetWithBooking(int petId) {
//		Pet pet = this.petService.findPetById(petId);
//		Booking booking = new Booking();
//		pet.addBooking(booking);
//		return booking;
//	}
	
	@GetMapping(value = "/owners/{ownerId}/pets/{petId}/bookings/new")
	public String initNewBookingForm(@PathVariable("petId") int petId, Map<String, Object> model) {
		Pet pet = this.petService.findPetById(petId);
		Booking booking = new Booking();
		pet.addBooking(booking);
		model.put("booking", booking);
		return "pets/createOrUpdateBookingForm";
	}

	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/bookings/new")
	public String processNewBookingForm(@Valid Booking booking, BindingResult result, @PathVariable("petId") int petId) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateBookingForm";
		}
		try {
			this.petService.saveBooking(booking);
			return "redirect:/owners/{ownerId}";
		} catch(InvalidDatePetBookingException ex) {
			result.rejectValue("fechaEntrada", "invalid date", "La fecha de entrada debe ser posterior a la fecha de salida");
			return "pets/createOrUpdateBookingForm";
		} catch(DuplicatedFechaEntradaPetBookingException ex) {
			result.rejectValue("fechaEntrada", "invalid date", "Existe una reserva que coincide con la fecha de entrada para esta mascota");
			return "pets/createOrUpdateBookingForm";
		} catch(DuplicatedFechaSalidaPetBookingException ex) {
			result.rejectValue("fechaSalida", "invalid date", "Existe una reserva que coincide con la fecha de salida para esta mascota");
			return "pets/createOrUpdateBookingForm"; 
		}
	}

	@GetMapping(value = "/owners/*/pets/{petId}/bookings")
	public String showBookings(@PathVariable int petId, Map<String, Object> model) {
		model.put("bookings", this.petService.findPetById(petId).getBookings());
		return "bookingList";
	}
}
