package com.dinesh.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinesh.dto.LoginFormDto;
import com.dinesh.dto.RegisterFormDto;
import com.dinesh.dto.ResetPwdFormDto;
import com.dinesh.dto.UserDto;
import com.dinesh.model.CityEntity;
import com.dinesh.model.CountryEntity;
import com.dinesh.model.StateEntity;
import com.dinesh.model.UserEntity;
import com.dinesh.repo.CityRepo;
import com.dinesh.repo.CountryRepo;
import com.dinesh.repo.StateRepo;
import com.dinesh.repo.UserRepo;
import com.dinesh.service.EmailService;
import com.dinesh.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private CountryRepo countryRepo;
	@Autowired
	private StateRepo stateRepo;
	@Autowired
	private CityRepo cityRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private EmailService emailService;
	 
	@Override
	public Map<Integer, String> getCountries() {
		// TODO Auto-generated method stub
		Map<Integer, String> countyMap=new HashMap<>();
		List<CountryEntity> countryList= countryRepo.findAll();
		countryList.forEach(c->{countyMap.put(c.getCountryId(), c.getCountrName());
		});
		return countyMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		// TODO Auto-generated method stub
		Map<Integer, String> stateMap=new HashMap<>();
		List<StateEntity> stateList=stateRepo.findByCountryId(countryId);
		stateList.forEach(c->{stateMap.put(c.getStateId(), c.getStateName());
		});
		return stateMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		// TODO Auto-generated method stub
		Map<Integer, String> cityMap=new HashMap<>();
		List<CityEntity> cityListr=cityRepo.findByStateId(stateId);
		cityListr.forEach(c->{cityMap.put(c.getCityId(), c.getCityName());});
		return cityMap;
	}

	@Override
	public boolean duplicateEmailCheck(String email) {
		// TODO Auto-generated method stub
		UserEntity byemail=userRepo.findByEmail(email);
		if(byemail != null) {
			return true;
		}else {
		return false;
		}
	}

	@Override
	public boolean saveUser(RegisterFormDto regFormDTO) {
		// TODO Auto-generated method stub
		UserEntity userEntity= new UserEntity();
		BeanUtils.copyProperties(regFormDTO, userEntity);
		CountryEntity country=countryRepo.findById(regFormDTO.getCountryId()).orElse(null);
		userEntity.setCountry(country);
		StateEntity state=stateRepo.findById(regFormDTO.getStateId()).orElse(null);
		userEntity.setState(state);
		CityEntity city=cityRepo.findById(regFormDTO.getCityId()).orElse(null);
		userEntity.setCity(city);
		String randomPwd=generatedRandomPwd();
		userEntity.setPwd(randomPwd);
		userEntity.setUpdatedPwd("No");
		UserEntity saveUser=userRepo.save(userEntity);
		if(null != saveUser.getUserId()) {
			String subject="Your Account Is Created";
			String body="Your Passwort to Login:"+randomPwd;
			String to=regFormDTO.getEmail();
			emailService.sendEmail(subject, body, to);
			return true;
		}
		return false;
	}

	private String generatedRandomPwd() {
	String upperCaseLetters="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String lowerCaseLetters="abcdefghijklmnopqrstuvwxyz";
	String alphabets=upperCaseLetters+lowerCaseLetters;
	Random random=new Random();
	StringBuffer generatedPwd=new StringBuffer();
	for(int i=0; i<5; i++) {
//		give any no for 0 to 51
		int randomIndex=random.nextInt(alphabets.length());
		generatedPwd.append(alphabets.charAt(randomIndex));
	}
		return generatedPwd.toString();
	}

	@Override
	public UserDto login(LoginFormDto loginFormDTO) {
		// TODO Auto-generated method stub
		UserEntity user=userRepo.findByEmailAndPwd(loginFormDTO.getEmail(), loginFormDTO.getPwd());
		if(user!=null) {
			UserDto userDto=new UserDto();
			BeanUtils.copyProperties(user, userDto);
			return userDto;
		}
		return null;
	}

	@Override
	public boolean resetPwd(ResetPwdFormDto resetPwdDTO) {
		// TODO Auto-generated method stub
		String email=resetPwdDTO.getEmail();
		UserEntity user=userRepo.findByEmail(email);
//		setting New Pwd
		user.setPwd(resetPwdDTO.getNewPwd());
		user.setUpdatedPwd("YES");
//		save method is used to upsert
		userRepo.save(user);   
		return true;
	}

	}

	
	


