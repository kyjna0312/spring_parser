package com.example.demoproject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class Main {

	private static InputStream inputStreamFromClasspath(String path) {
		System.out.println(path);
		System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));

	    return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
	}

	public static void main(String[] args) throws Exception {
	    ObjectMapper objectMapper = new ObjectMapper();
	    JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
	
	    try(
				InputStream jsonStream = inputStreamFromClasspath("1_Robot_Alto_edit.json");
				InputStream schemaStream = inputStreamFromClasspath("schema.json")
		){
	        System.out.println(jsonStream);

	    	JsonNode json = objectMapper.readTree(jsonStream);
	        JsonSchema schema = schemaFactory.getSchema(schemaStream);

			System.out.println(json);
			System.out.println(schema);

	        Set<ValidationMessage> validationResult = schema.validate(json);
	
	        // print validation errors
	        if (validationResult.isEmpty()) {
	            System.out.println("no validation errors :-)");
	        } else {
	            validationResult.forEach(vm -> System.out.println(vm.getMessage()));
	        }
	    }catch(FileNotFoundException fnfe){

		}catch(IOException e){

		}
	}
}
