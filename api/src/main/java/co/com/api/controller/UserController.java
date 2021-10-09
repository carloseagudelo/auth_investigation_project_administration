package co.com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.com.entity.request.UserRequestDTO;
import co.com.iservices.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired private IUserService userServices;

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<String> signin(@RequestBody(required=true) UserRequestDTO userInformation) throws Exception {
		return userServices.singup(userInformation);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<String> list() throws Exception {
		return userServices.getAll();
	}
	
	@RequestMapping(value = "/validateToken", method = RequestMethod.GET)
	public ResponseEntity<String> list(@RequestHeader("Authorization") String token) throws Exception {
		return userServices.validateToken(token);
	}
	
}
