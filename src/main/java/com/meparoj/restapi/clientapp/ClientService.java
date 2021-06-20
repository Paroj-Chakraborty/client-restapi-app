package com.meparoj.restapi.clientapp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ClientService {
	
	private static List<Client> clients = new ArrayList<Client>();
	
	//Sample initial data  
	static {
		clients.add(new Client(8801235111088L,"Matt","Demon","10235845","Bahamas"));
		clients.add(new Client(8801235111189L,"Phil","Jones","75896302","Paris"));
		clients.add(new Client(9301235111187L,"Bill","Murray","52458963","Chicago"));
		clients.add(new Client(9301235111187L,"John","Moore","42512587","Kolkata"));
	}
	
	public List<Client> findAll() {
			
		return clients;
	}
	
	public Client findById(long id) {
		
		for(Client client: clients) {
			if(client.getId() == id)
				return client;
		}
		
		return null;
	}
	
	public List<Client> findByFirstName(String firstName) {
		
		List<Client> tempClients = new ArrayList<Client>();
		for(Client client: clients) {
			if(client.getFirstName().equals(firstName))
				tempClients.add(client);
		}
		
		return tempClients;
	}
	
	public Client findByPhoneNumber(String phNumber) {
		
		for(Client client: clients) {
			if(client.getMobileNumber().equals(phNumber))
				return client;
		}
		
		return null;
	}

	public void save(Client client) {
			clients.add(client);
		}
	
	
	public Client deleteById(long id) {
		Client client = findById(id);
		
		if(client == null) return null;
		
		if(clients.remove(client)) {
			return client;
		}
		return null;
		
	}
	
	// function to check an id or phone number already exists for others
	public boolean existOrNot(Client client) {
		
		long tempId = client.getId();
		String mobNumber= client.getMobileNumber();
		
		for(Client tempClient: clients) {
			if(tempClient.getMobileNumber().equals(mobNumber)) {
				return true;
			}
		}
		
		for(Client tempClient: clients) {
			if(tempClient.getId()==tempId) {
				return true;
			}
		}
		
		return false;
	}
	
	//function to validate the SA id number
	public boolean validateId(Client client) {
		
		String tempId = String.valueOf(client.getId());
		
		String yy = tempId.substring(0,2);
		String mm = tempId.substring(2,4);
		String dd = tempId.substring(4,6);
		
		int n = tempId.length();
		int sum = 0;
		
		//check whether the id is of 13 digits or not
		if(n!=13)
			return true;
		
		//check if the date format is valid or not
		if(!isValid(yy,mm,dd)) {
			return true;
		}
		
		//the 11th digit can only be 0 or 1
		int citizenDigit = Integer.parseInt(String.valueOf(tempId.charAt(10)));
		if(citizenDigit!=0 && citizenDigit!=1 )
			return true;
		
		//Check whether an id number is valid according to Luhn algorithm
		for(int i=0;i<n;i++) {
			
			int temp = Integer.parseInt(String.valueOf(tempId.charAt(i)));
			if(i%2==0) {
				sum = sum + temp;
			}
			else {
				temp = temp*2;
				if(temp>10) {
					sum = sum + (temp%10) + 1;
				}
				else {
					sum = sum + temp;
				}
			}
		}
		
		if(sum%10==0)
			return false;
		
		return true;
	}
	
	//function to check valid date
	private boolean isValid(String yy, String mm, String dd) {
		
		int day = Integer.parseInt(dd);
		int month = Integer.parseInt(mm);
		int year = Integer.parseInt(yy);
		
		if(month<1 || month>12)
			return false;
		if(day<1 || day>31)
			return false;
		
		if(month==2) {
			if(year%4==0)
				return (day<=29);
			else
				return (day<=28);
		}
		
		if(month==4 || month==6 || month==9 || month==11)
			return (day<=30);
		
		return true;
		
	}

	

}
