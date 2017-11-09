package com.au.example.meetinger.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.au.example.meetinger.persistence.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	@Query("SELECT u FROM USER u WHERE u.userName = :uName")
    List<User> findUserbyUsername(@Param("uName") String userName);
	
	@Query("SELECT u FROM USER u WHERE u.company.id = :companyId")
    List<User> findCompanyUser(@Param("companyId") Long companyId);

}
