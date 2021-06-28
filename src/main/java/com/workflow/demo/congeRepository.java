package com.workflow.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface congeRepository extends JpaRepository<TDemande, Long> {

	
	 List<TDemande> findByUser(User user);
	 List<TDemande> findByCongeTypeContainingIgnoreCaseOrCommentContainingIgnoreCaseOrUserUsernameContainingIgnoreCase(String comment , String congeType, String username);
	
		
	 List<TDemande>findByUserUsername(String user);

}
