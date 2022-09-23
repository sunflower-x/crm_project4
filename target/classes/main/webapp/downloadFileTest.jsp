<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>

<html>
<head>
    <base href="<%=basePath%>>">
    <!--JQUERY-->
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script>
        $(function () {
            $("#downloadFileBtn").click(function (){
                window.location.href="workbench/activity/downloadFile.do"
            })
        })
    </script>
</head>
<body>

<input type="button" value="download" id="downloadFileBtn"/>

</body>

</html>
