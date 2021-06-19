package com.workflow.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
