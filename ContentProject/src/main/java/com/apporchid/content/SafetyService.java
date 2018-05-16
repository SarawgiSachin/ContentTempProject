package com.apporchid.content;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.apporchid.cloudseer.service.python.PythonScriptExecutor;
import com.apporchid.common.utils.JsonUtils;

@Service
public class SafetyService  {
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(SafetyService.class);
	private int noOfRetries = 5;

	@Inject
	PythonScriptExecutor pythonScriptExecutor;

	public String getSaftey360(String text, String call) throws Exception {
		try {
			JSONObject json = getSafetyJson();
			json.put("text", text);
			json.put("call",call);
			//this.setToken(json);
			String responseString = pythonScriptExecutor
					.executeScript("python/safety/safety360.py", json, noOfRetries);
			return responseString;
		}catch(Exception e){
			logger.debug("exception occured while executing safety pythonscript.");
			throw e;
		}
	}
	
	public JSONObject getSafetyJson() throws IOException{
		InputStream ioStream = new ClassPathResource("/data/safety.json").getInputStream();
		if (ioStream != null) {
			String mappingJson = IOUtils.toString(ioStream, StandardCharsets.UTF_8.name());
			JSONObject response = JsonUtils.fromJson(mappingJson, JSONObject.class);
			return response;
		}
		return null;
	}

}
