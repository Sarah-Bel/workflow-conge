package com.workflow.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    
   @Query(value="select comment from tdemande JOIN users ON (tdemande.username=users.username) ORDER BY comment",nativeQuery = true)
  

	 List<User> findByallt();
 //  ArrayList<User> findByallt();
    
  
}
