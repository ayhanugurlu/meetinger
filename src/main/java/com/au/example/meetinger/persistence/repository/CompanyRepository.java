package com.au.example.meetinger.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.au.example.meetinger.persistence.model.Company;

public interface CompanyRepository extends CrudRepository<Company, Long>{

}
