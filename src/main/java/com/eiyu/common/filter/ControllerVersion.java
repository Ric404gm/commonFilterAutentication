package com.eiyu.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerVersion {


	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerVersion.class);

	@Autowired
	private  ContainerTokenUserComponent component;

	@GetMapping( "/versionfilter" )
	@ResponseBody
	public String  testVersion() throws Exception {
		
		LOGGER.info(component.getCurrentUser());
		return "1.0.0";
	}


}
