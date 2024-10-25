package com.dinesh.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.dinesh.model.CityEntity;

public interface CityRepo extends JpaRepository<CityEntity, Integer>{

	public List<CityEntity> findByStateId(Integer stateId);
}
