package org.springframework.samples.petclinic.model;

public enum AdoptionState{
	APPROVED("Approved"),
	REJECTED("Rejected"),
	PENDING("Pending");

	private String status;
	   
	AdoptionState(String st) {
		this.status = st;
	} 
	public String getStatus() {
		return status;
	}
}