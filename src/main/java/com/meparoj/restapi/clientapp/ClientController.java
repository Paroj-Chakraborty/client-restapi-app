package com.meparoj.restapi.clientapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ClientController {
	
	@Autowired
	ClientService clientService;
	
	// To find all clients "http://localhost:8080/restapi/clients"
	@GetMapping("/restapi/clients")
	public List<Client> getAllClients(){
		
		return clientService.findAll();
	}
	
	// To find clients by Id
	@GetMapping("/restapi/clients/byId/{id}")
	public Client getClientById(@PathVariable long id) {
		
		return clientService.findById(id);
	}
	
	// To find clients by FirstName
	@GetMapping("/restapi/clients/byFirstName/{firstName}")
	public List<Client> getClientByFirstName(@PathVariable String firstName) {
		
		return clientService.findByFirstName(firstName);
	}		
	
	// To find clients by phone number
		@GetMapping("/restapi/clients/byNumber/{mobileNumber}")
		public Client getClientById(@PathVariable String mobileNumber) {
			
			return clientService.findByPhoneNumber(mobileNumber);
		}
	
	@PostMapping("/restapi/clients")
	public ResponseEntity<ClientMessage> createClient(@RequestBody Client client){
		
		if(clientService.validateId(client)) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ClientMessage("The Id is not valid!"));
		}
		
		if(clientService.existOrNot(client)) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ClientMessage("The phone no or ID already exists!"));
		}
		
		clientService.save(client);
	
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ClientMessage("The Client is enrolled!"));
	}
	
	@PutMapping("/restapi/clients/{id}")
	public ResponseEntity<ClientMessage> updateClient(@PathVariable long id, @RequestBody Client client){
		
		if(clientService.validateId(client)) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ClientMessage("The Id is not valid!"));
		}
		
		clientService.deleteById(id);
		
		if(clientService.existOrNot(client)) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ClientMessage("The phone no or ID already exists!"));
		}
		
		clientService.save(client);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ClientMessage("The Client is updated!"));
	}
	

}
