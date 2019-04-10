package io.homecloud.homeserver.HelloWorldcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@RequestMapping("/hello_world")
	public String sendBackHelloWorld() {
		return "Hello World from homeServer";
	}
	
}
