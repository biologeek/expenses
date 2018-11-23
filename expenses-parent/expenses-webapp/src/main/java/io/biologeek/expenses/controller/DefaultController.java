package io.biologeek.expenses.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DefaultController {

	@RequestMapping(value={"/"}, method={RequestMethod.GET})
	public ResponseEntity<Void> name() {
		return ResponseEntity.ok().build();
	}
	
	/*@RequestMapping(value="/**", method=RequestMethod.OPTIONS)
	public void optionsRequest(HttpServletResponse response){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.addHeader("Access-Control-Allow-Headers", "origin, Authorization, content-type, accept, x-requested-with");
		response.addHeader("Access-Control-Max-Age", "3600");
		
	}*/
}
