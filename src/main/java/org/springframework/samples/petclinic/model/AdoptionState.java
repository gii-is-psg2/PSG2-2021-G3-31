package org.springframework.samples.petclinic.model;

public enum AdoptionState{
	ACEPTADA("Aceptada"),
	DENEGADA("Denegada"),
	PENDIENTE("Pendiente");

	private String status;
	   
	AdoptionState(String st) {
		this.status = st;
	} 
	public String getStatus() {
		return status;
	}
}