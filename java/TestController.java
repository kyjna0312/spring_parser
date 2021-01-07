package com.example.demoproject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;


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
			fileDir = "\\target\\classes\\json\\";
			filename = file_name.getOriginalFilename();

			path = baseDir + fileDir + filename;

			//file move
			file_name.transferTo(new File(path));

			model.addAttribute("filename", filename);
			model.addAttribute("filepath", path);
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
		
		String savePath = request.getParameter("filepath");
		savePath = savePath.replace(".json", "(edit).json");
		filename = savePath.replace("C:\\Users\\kyj\\Documents\\workspace-spring-tool-suite-4-4.8.1.RELEASE\\demoproject_5\\target\\classes\\json\\", "");

		String content = request.getParameter("Document");

		PrintWriter pw = null;

		try{
			pw = new PrintWriter(savePath);
			pw.printf(content);

			System.out.println("file save");
			model.addAttribute("FileSave", "<p style=\"color: blue\">file Save ---> <a href=\"\\index\">index link</a></p>");
		}catch (IOException e){
			System.out.println("fail...");
			model.addAttribute("SaveFail", "<p style=\"color: red\">Save Fail...</p>");
		}
		pw.close();

		model.addAttribute("filename", filename);
		model.addAttribute("filepath", savePath);

		return "Edit_page";
	}
	
	@RequestMapping(path = "/edit/check")
	public String CheckPage(Model model, HttpServletRequest request){
		
		String savePath = request.getParameter("filepath");
		filename = savePath.replace("C:\\Users\\kyj\\Documents\\workspace-spring-tool-suite-4-4.8.1.RELEASE\\demoproject_5\\target\\classes\\json\\", "");


		Parser ps = new Parser();
		String mesage = ps.Parsing(filename);

		model.addAttribute("filename", filename);
		model.addAttribute("filepath", savePath);

		if(mesage == "success"){
			model.addAttribute("Check_Success", "<p style=\"color: blue\">no validation errors :-)</p>");
		}
		else{
			model.addAttribute("Check_Success", "<p style=\"color: red\">"+mesage+"</p>");
		}

		return "Edit_page";
	}
}
