package com.example.authsamlservice.domains;

import com.google.gson.Gson;

import java.util.Set;

public class User {

    private String email;
    private String fullname;
    private String matricola;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    
    public String getMatricola() {
    	return matricola;
    }
    
    public void setMatricola(String matricola) {
    	this.matricola = matricola;
    }


	public User(String email, String fullname, String matricola) {
		super();
		this.email = email;
		this.fullname = fullname;
		this.matricola = matricola;
	}
	
    public User() {
    	
    }

    @Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
