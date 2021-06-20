package com.workflow.demo;

import java.util.List;

import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface congeRepository extends JpaRepository<TDemande, Long> {

	 @Query("select td from TDemande td where td.comment LIKE %?1% or td.congeType LIKE %?2%  ")
	 
	   List<TDemande> findByrechercheid(String comment , String congeType, String empName);

	 List<TDemande> findByUser(User user);

	String findByUser(String user_id);
	 
	// public  List<TDemande> findByUser(String user);


}
