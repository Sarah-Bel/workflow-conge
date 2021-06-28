package com.workflow.demo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.flowable.engine.history.HistoricActivityInstance;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity(name="TDemande")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class TDemande implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id ;
	
	
	 private String congeType;
	
	 private  String dateDebut;
	
	 private  String dateFin;
	
	 private  String comment;
	 
	 
	    @ManyToOne
		@JoinColumn(name = "username", nullable = false)
		private User user;
	    
	    private String process;


	   
}