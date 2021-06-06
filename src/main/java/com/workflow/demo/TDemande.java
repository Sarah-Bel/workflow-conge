package com.workflow.demo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name="TDemande")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TDemande implements Serializable {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	
	@Column
	 private String congeType;
	@Column
	 private  String dateDebut;
	@Column
	 private  String dateFin;
	@Column
	 private  String comment;
	@Column
	 private  String empName;

}