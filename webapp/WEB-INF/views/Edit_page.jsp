<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.concurrent.TimeUnit" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ThingsMetaData Instance Document Editor</title>

    <!-- Bootstrap core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/resources/css/simple-sidebar2.css" rel="stylesheet">

</head>

<body>

<div class="d-flex" id="wrapper">

    <!-- Sidebar -->
    <div class="bg-light border-right" id="sidebar-wrapper">
        <div class="sidebar-heading">Document Editor</div>
        <div class="list-group list-group-flush">
            <a href="edit" class="list-group-item list-group-item-action bg-light">Edit Page</a>
            <a href="test1" class="list-group-item list-group-item-action bg-light">Test1</a>
        </div>
    </div>
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">

        <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
            <button class="btn btn-primary" id="menu-toggle">Toggle Menu</button>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
                    <li class="nav-item active">
                        <a class="nav-link" href="/index">Home <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="http://ss.ssu.ac.kr" target="_blank">Link</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Dropdown
                        </a>
                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="#">Action</a>
                            <a class="dropdown-item" href="#">Another action</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#">Something else here</a>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>

        <!-- Main Contents-->
        <div class="container-fluid">
            <h1 class="mt-4">ThingsMetaData Instance Document Editor</h1>
            <h2>Edit page</h2>
            <p> <b>ThingsMetaData Instance Document Editor</b> is editor for Context-awareness. </p>
            <p>This Editor can modify Instance Document</p>
            <div>

            </div>
            <hr>

            <div id="Edit_field">
                <!--Document Reader-->
                <!--for save-->
                <form method="post" enctype="multipart/form-data">
                    <div>
                        <h3>Document Edit</h3>
                        <p class="filename" title="Click to open a folder" href="file:///D:/"><%
                            String fileName = (String)request.getAttribute("filename");
                            out.println(fileName);
                        %></p>
                        <input type="text" value="<%
                            String Document_filePath = (String)request.getAttribute("filepath");
                            out.println(Document_filePath);
                        %>" name="filepath" style="display: none">

                    </div>

                    <textarea class="Document" name="Document"><%
                        BufferedReader reader = null;

                        try (BufferedReader Document_reader = new BufferedReader(new FileReader(Document_filePath))) {
                            while (true) {
                                String str = Document_reader.readLine();
                                if (str == null) {
                                    break;
                                }
                                out.println(str);
                            }
                        } catch (FileNotFoundException fnfe) {
                            out.println("파일을 찾을 수 없습니다!");
                        } catch (IOException ioe) {
                            out.println("파일을 읽을 수 없습니다!");
                        }
                    %></textarea>
                    <input type="submit" class="btn btn-primary" value="저장" formaction="/edit/save">

                    <!--Schema Reader-->
                    <div>
                        <h3>Schema</h3>
                        <p>schema.json</p>
                        <input type="text" value="<%
                            //String Schema_filePath = application.getRealPath("jsonFile/schema.json");
                            //out.println(Schema_filePath);

                            String schema_fileDir = "\\target\\classes\\";
                            String Schema_name = "schema.json";
                            String Schema_filePath = System.getProperty("user.dir") + schema_fileDir + Schema_name;

                            out.println(Schema_filePath);

                        %>" name="schemapath" style="display: none">
                    </div>

                    <textarea class="Schema" name="Schema"><%

                        try (BufferedReader Schema_reader = new BufferedReader(new FileReader(Schema_filePath))) {
                            while (true) {
                                String str = Schema_reader.readLine();
                                if (str == null) {
                                    break;
                                }
                                out.println(str);
                            }
                        } catch (FileNotFoundException fnfe) {
                            out.println("파일을 찾을 수 없습니다!");
                        } catch (IOException ioe) {
                            out.println("파일을 읽을 수 없습니다!");
                        }
                    %></textarea>
                        <input type="submit" class="btn btn-primary" value="검사" formaction="/edit/check">
                    </form>
            </div>

            <!--Result Message-->
            <div id="Message_field">
                <h3 id="result">RESULT</h3>
                <div id="resultmessage">
                    <%
                        if(request.getAttribute("Error") != null){
                            out.println(request.getAttribute("Error"));
                        }
                        else if(request.getAttribute("FileOpen") != null){
                            out.println(request.getAttribute("FileOpen"));
                        }
                        else if(request.getAttribute("FileSave") != null){
                            out.println(request.getAttribute("FileSave"));
                        }
                        else if(request.getAttribute("SaveFail") != null){
                            out.println(request.getAttribute("SaveFail"));
                        }
                        else if(request.getAttribute("Check_Success") != null){
                            out.println(request.getAttribute("Check_Success"));
                        }
                        else if(request.getAttribute("Check_Massage") != null){
                            out.println(request.getAttribute("Check_Massage"));
                        }
                    %>
                </div>
            </div>
        </div>
    </div>
    <!-- /#page-content-wrapper -->

</div>
<!-- /#wrapper -->

<!-- Bootstrap core JavaScript -->
<script src="/resources/vendor/jquery/jquery.js"></script>
<script src="/resources/vendor/bootstrap/js/bootstrap.bundle.js"></script>

<!-- Menu Toggle Script -->
<script>
    $("#menu-toggle").click(function(e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
</script>

</body>

</html>
