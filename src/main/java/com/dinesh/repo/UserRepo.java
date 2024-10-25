package com.dinesh.repo;

import org.springframework.data.jpa.repository.JpaRepository;


import com.dinesh.model.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Integer>{

	public UserEntity findByEmail(String email);
//	login purpose add this Method
	public UserEntity findByEmailAndPwd(String email,String pwd);
}
