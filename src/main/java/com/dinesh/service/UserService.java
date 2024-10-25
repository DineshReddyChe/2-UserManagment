package com.dinesh.service;

import java.util.Map;

import com.dinesh.dto.LoginFormDto;
import com.dinesh.dto.RegisterFormDto;
import com.dinesh.dto.ResetPwdFormDto;
import com.dinesh.dto.UserDto;

public interface UserService {

	public Map<Integer,String> getCountries();

	public Map<Integer,String> getStates(Integer countryId);

	public Map<Integer,String> getCities(Integer stateId);

	public boolean duplicateEmailCheck(String email);

	public boolean saveUser(RegisterFormDto regFormDTO);

	public UserDto login(LoginFormDto loginFormDTO);

	public boolean resetPwd(ResetPwdFormDto resetPwdDTO);

	
}
