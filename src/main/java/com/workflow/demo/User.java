package com.workflow.demo;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="users")
public class User {
	
	@Id
	private int id;
	private String username;
	private String password;
	private String role;

}
