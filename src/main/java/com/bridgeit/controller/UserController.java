package com.bridgeit.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.model.User;
import com.bridgeit.model.UserResponse;
import com.bridgeit.security.model.AuthenticatedUser;
import com.bridgeit.service.Service;
import com.bridgeit.validation.Validation;

/**
 * @author bridgeit
 *
 */

@RestController
public class UserController {

	@Autowired
	Service service;
	@Autowired
	Validation validate;

	@RequestMapping(value = { "/hello" }, method = RequestMethod.GET)
	public String welcome() {
		return "Welcome";
	}

	@RequestMapping(value = { "/user/register" }, method = RequestMethod.POST)
	public ResponseEntity<UserResponse> registration(@RequestBody User user) {
		UserResponse message = new UserResponse();

		if (validate.userValidate(user)) {
			message.setMessage(service.registrationValidate(user));
			return new ResponseEntity<UserResponse>(message, HttpStatus.OK);
		} else {
			message.setMessage("Please Enter Correct Values...");
			return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public ResponseEntity<UserResponse> login(@RequestBody User user) {
		UserResponse response = new UserResponse();
		String token = service.loginValidate(user);
		if (token != null) {

			User userInfo = service.getUser(user.getUserName());
			response.setMessage("Welcome " + userInfo.getUserName());
			response.setEmail(userInfo.getEmail());
			response.setProfilePicture(userInfo.getProfilePicture());
			response.setUserName(userInfo.getUserName());
			response.setToken(token);
			return new ResponseEntity<UserResponse>(response, HttpStatus.OK);

		} else {
			response.setMessage("Invalid Username or Password");
			return new ResponseEntity<UserResponse>(response, HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/user/activate/{jwt:.+}", method = RequestMethod.GET)
	public ResponseEntity<String> activation(@PathVariable String jwt) {
		String status = service.verifyToken(jwt);
		return new ResponseEntity<String>(status, HttpStatus.OK);

	}

	@RequestMapping(value = { "/user/forgot/{email:.+}" }, method = RequestMethod.POST)
	public ResponseEntity<String> forgot(@PathVariable String email) {
		System.out.println(email);

		String message = service.forgetPassword(email);
		return new ResponseEntity<String>(message, HttpStatus.OK);

	}

	@RequestMapping(value = "/user/verifyToken/{jwt:.+}", method = RequestMethod.GET)
	public void verifyTokenReset(@PathVariable String jwt, HttpSession httpSession, HttpServletResponse response)
			throws Exception {
		int userId = service.verifyTokenReset(jwt);
		if (userId != 0) {
			httpSession.setAttribute("userId", userId);
			response.sendRedirect("http://localhost:8080/ToDoNotes/#!/resetPassword");
		} else {
			response.sendRedirect("http://localhost:8080/ToDoNotes/#!/login");
		}
	}

	@RequestMapping(value = "/user/reset/{password}", method = RequestMethod.POST)
	public ResponseEntity<UserResponse> resetPassword(@PathVariable String password, HttpSession httpSession) {
		UserResponse message = new UserResponse();
		try {
			int userId = (int) httpSession.getAttribute("userId");
			httpSession.removeAttribute("userId");

			if (userId != 0) {
				String status = service.resetPassword(userId, password);
				message.setMessage(status);
				return new ResponseEntity<UserResponse>(message, HttpStatus.OK);
			} else {
				message.setMessage("invalid Access");
				return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			message.setMessage("invalid Access");
			return new ResponseEntity<UserResponse>(message, HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = { "/logout" }, method = RequestMethod.POST)
	public ResponseEntity<String> logout(HttpServletRequest request) {
		String message = "No Active Session";
		HttpSession session = request.getSession();
		if (session.isNew()) {
			return new ResponseEntity<String>(message, HttpStatus.CONFLICT);
		}
		session.invalidate();

		message = "Logout Successfully...";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@RequestMapping(value = { "/getUser" }, method = RequestMethod.POST)
	public ResponseEntity<User> getUserInfo() {

		AuthenticatedUser authenticatedUser=(AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	int userId=(int)(long)authenticatedUser.getId();
   
		User user = service.getUserInfo(userId);

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}

	}
}
