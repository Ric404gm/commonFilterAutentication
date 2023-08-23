package com.eiyu.common.filter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerVersion {

	@GetMapping( "/versionfilter" )
	@ResponseBody
	public String  testVersion() throws Exception {
		return "1.0.0";
	}


}
