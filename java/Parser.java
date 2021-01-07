package com.example.demoproject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class Parser {

	private static InputStream inputStreamFromClasspath(String path) {
		System.out.println(path);
		System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));

	    return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
	}

	public static String Parsing(String filename) {
	    ObjectMapper objectMapper = new ObjectMapper();
	    JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
	    String message = null;
	    Parsing_Message pms = new Parsing_Message();

	    try(
				InputStream jsonStream = inputStreamFromClasspath("json/" + filename);
				InputStream schemaStream = inputStreamFromClasspath("schema.json")
		){

	    	JsonNode json = objectMapper.readTree(jsonStream);
	        JsonSchema schema = schemaFactory.getSchema(schemaStream);


	        Set<ValidationMessage> validationResult = schema.validate(json);
	
	        // print validation errors
	        if (validationResult.isEmpty()) {
	        	//System.out.println("no validation errors :-)");
				pms.set_Message("success");
	        } else {
	            validationResult.forEach(
	            		vm -> {
	            			//System.out.println(vm.getMessage());
	            			pms.set_Message(vm.getMessage());
	            		}
				);
	        }
	    }catch(FileNotFoundException fnfe){

		}catch(IOException e){

		}

	    return pms.get_Message();
	}
}
