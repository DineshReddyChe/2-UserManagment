package com.dinesh.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import com.dinesh.model.CountryEntity;

public interface CountryRepo extends JpaRepository<CountryEntity, Integer>{

}
