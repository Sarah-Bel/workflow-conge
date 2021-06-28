package com.workflow.demo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.mail.Email;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="users")

public class User {
	
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name="user_id", referencedColumnName = "id" )
//	List<TDemande> TDemandes=new ArrayList<>();
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	@JsonIgnore
	List<TDemande> tdemande=new ArrayList<>();
		
	@Id
	private String username;
	private String password;
	private String role;
	private String emp_email;

	
	public String rtid() {
		return getUsername();
	}
	

	

}