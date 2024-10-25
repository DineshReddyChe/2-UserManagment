package com.dinesh.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dinesh.dto.LoginFormDto;
import com.dinesh.dto.QuoteApiResponseDto;
import com.dinesh.dto.RegisterFormDto;
import com.dinesh.dto.ResetPwdFormDto;
import com.dinesh.dto.UserDto;
import com.dinesh.service.DashboardService;
import com.dinesh.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping("/register")
	public String loadRegisterPage(Model model) {
		Map<Integer, String> countryMap=userService.getCountries();
		model.addAttribute("countrys", countryMap);
		RegisterFormDto registerFormDto=new RegisterFormDto();
		model.addAttribute("registerForm", registerFormDto);
				
		return "register";
		
	}
	@GetMapping("/states/{countryId}")
	@ResponseBody
	public Map<Integer, String> getStates(@PathVariable Integer countryId){
		Map<Integer, String> state=userService.getStates(countryId);
		return state;
		
	}
	@GetMapping("/cities/{stateId}")
	@ResponseBody
	public Map<Integer, String> getCitys(@PathVariable Integer stateId){
		Map<Integer,String> citysMap=userService.getCities(stateId);
		
		return citysMap;
		
	}
	@PostMapping("/register")
	public String handleRegistration(RegisterFormDto registerFormDto,Model model) {
		boolean status=userService.duplicateEmailCheck(registerFormDto.getEmail());
		if(status) {
			model.addAttribute("emsg", "Duplicate Email Found");
		}else {
			boolean saveUser=userService.saveUser(registerFormDto);
			if(saveUser) {
				model.addAttribute("smsg", "Registration is Sucess, Please Check your eamail..!!");
				
			}else {
				model.addAttribute("emsg", "Registration Failed");
			}
		}
		model.addAttribute("registerForm", new RegisterFormDto());
		model.addAttribute("countrys", userService.getCountries());
		return "register";
		
	}
	
	@GetMapping("/")
	public String Login(Model model) {
		LoginFormDto loginFormDto=new LoginFormDto();
		model.addAttribute("loginForm", loginFormDto);
		return "login";
		
	}
	@PostMapping("/login")
	public String handleUserLogin(LoginFormDto loginFormDto, Model model) {
		UserDto userDto=userService.login(loginFormDto);
		if(userDto==null) {
			model.addAttribute("emsg", "Invalid Credintails");
			model.addAttribute("loginForm", loginFormDto);
		}else {
			String pwdUpdated =userDto.getUpdatedPwd();
			if("yes".equals(pwdUpdated)) {
//			Display Dashboard
			return "redirect:dashboard";
		}else {
			return "redirect:reset-pwd-page?email="+userDto.getEmail();
		}
			}
	
		return "login";
		
	}
	@GetMapping("/dashboard")
	public String dashBoard(Model model) {
		QuoteApiResponseDto quoteApiResponseDto=dashboardService.getQuote();
		model.addAttribute("quote", quoteApiResponseDto);
		return "dashboard";
		
	}
	@GetMapping("/reset-pwd-page")
	public String loadResetPage(@RequestParam String email, Model model) {
		ResetPwdFormDto resetPwdFormDto=new ResetPwdFormDto();
		resetPwdFormDto.setEmail(email);
		model.addAttribute("resetPwd", resetPwdFormDto);
		return "resetPwd";
		
	}
	@PostMapping("/resetPwd")
	public String handlePwdReset(ResetPwdFormDto resetPwdFormDto, Model model) {
		boolean resetPwd=userService.resetPwd(resetPwdFormDto);
		if(resetPwd) {
			return "redirect:dashboard";
		}
		return "resetPwd";
		
	}
}
