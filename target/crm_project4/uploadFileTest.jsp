<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>

<html>
<head>
    <base href="<%=basePath%>>">
    <!--JQUERY-->
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
</head>
<body>

<form action="workbench/activity/uploadFile.do" method="post" enctype="multipart/form-data">
    <input type="file" name="uploadFile">
    <input type="text" name="name">
    <input type="submit" value="submit">
</form>

</body>
</html>
