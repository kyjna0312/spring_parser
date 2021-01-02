<%@ page import="java.io.File" %>
<%@ page import="java.io.FileWriter" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.IOException" %>
<%
   String filename, content, filePath;
   request.setCharacterEncoding("UTF-8");

   //파일이름
   filename = (String) request.getAttribute("filename");
   //파일경로
   filePath = (String) request.getAttribute("filepath");
   filePath = filePath.replace(".json", "(edit).json");
   //내용
    content = request.getParameter("Document");

   out.println(filename + "<br><br>");
   out.println(filePath + "<br><br>");
   out.println(content);

    PrintWriter pw = null;

   try{
       pw = new PrintWriter(filePath);
       pw.printf(content);

       out.println("file save");
   }catch (IOException e){
       out.println("fail...");
   }

   pw.close();

%>