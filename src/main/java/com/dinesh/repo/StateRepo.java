package com.dinesh.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dinesh.model.StateEntity;



public interface StateRepo extends JpaRepository<StateEntity, Integer>{

	public List<StateEntity> findByCountryId(Integer countryId);
}
