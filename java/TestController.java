package com.example.demoproject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Set;


@Controller
public class TestController {

	String baseDir, fileDir, filename, path;
	
	@RequestMapping("/index")
	public String home() throws Exception{
		System.out.println("hear");
		return "index";
	}

	@RequestMapping(path = "/test1")
	public String test1(){
		System.out.println("move the test1 page");
		return "test1";
	}

	@RequestMapping(path = "/edit")
	public String EditPage(Model model, HttpServletRequest request, @RequestPart MultipartFile file_name) throws Exception{
		System.out.println("move the edit page");

		try {
			baseDir = System.getProperty("user.dir");
			fileDir = "\\src\\main\\webapp\\uploadFiles\\";
			filename = file_name.getOriginalFilename();

			path = baseDir + fileDir + filename;

			//file move
			file_name.transferTo(new File(path));

			model.addAttribute("filename", filename);
			model.addAttribute("FileOpen", "<p style=\"color: blue\">file open</p>");

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("Error", "<p style=\"color: red\">file error</p>");
		}

		return "Edit_page";
	}

	@RequestMapping(path = "/edit/save")
	public String SavePage(Model model, HttpServletRequest request){
		System.out.println("move the save page");
		//model.addAttribute("filename", filename);
		//model.addAttribute("filepath", path);

		path = path.replace(".json", "(edit).json");
		String content = request.getParameter("Document");

		PrintWriter pw = null;

		try{
			pw = new PrintWriter(path);
			pw.printf(content);

			System.out.println("file save");
			model.addAttribute("FileSave", "<p style=\"color: blue\">file Save ---> <a href=\"\\index\">index link</a></p>");
		}catch (IOException e){
			System.out.println("fail...");
			model.addAttribute("SaveFail", "<p style=\"color: red\">Save Fail...</p>");
		}
		pw.close();

		return "Edit_page";
	}

/*
	private static InputStream inputStreamFromClasspath(String path) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
	}
*/
	
	@RequestMapping(path = "/edit/check")
	public String CheckPage(Model model, HttpServletRequest request){
/*
		String schema_path = request.getParameter("schemapath");
		//System.out.println(schema_path);

		ObjectMapper objectMapper = new ObjectMapper();
		JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);

		try {
			System.out.println(path);
			System.out.println(schema_path);

			InputStream jsonStream = inputStreamFromClasspath("1_Robot_Alto_edit.json");
			InputStream schemaStream = inputStreamFromClasspath("schema.json");

			JsonNode json = objectMapper.readTree(jsonStream);
			JsonSchema schema = schemaFactory.getSchema(schemaStream);

			Set<ValidationMessage> validationResult = schema.validate(json);

			// print validation errors
			if (validationResult.isEmpty()) {
				model.addAttribute("Check_Success", "<p style=\"color: blue\">no validation errors :-)<a href=\"\\index\">index link</a></p>");
			} else {
				validationResult.forEach(vm -> model.addAttribute("Check_Massage", "<p style=\"color: red\">"+vm.getMessage()+"<a href=\"\\index\">index link</a></p>"));
			}
		}catch(FileNotFoundException fnfe){
			System.out.println("Not Found File");
		}catch(IOException e){
			System.out.println("Error");
		}
*/

		String schema_path = request.getParameter("schemapath");
		System.out.println(schema_path);

		String schema_contents = request.getParameter("Document");
		System.out.println(schema_contents);


		try (InputStream inputStream = getClass().getResourceAsStream(schema_path)) {
			JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
			Schema schema = SchemaLoader.load(rawSchema);
			schema.validate(new JSONObject(schema_contents)); // throws a ValidationException if this object is invalid
		} catch (ValidationException e) {
			System.out.println(e.getMessage());
			e.getCausingExceptions().stream()
					.map(ValidationException::getMessage)
					.forEach(System.out::println);
		} catch (IOException e){
			System.out.println(e.getMessage());
		}



		return "test1";
	}
}
