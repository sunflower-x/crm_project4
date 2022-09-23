<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>

<html>
<head>
    <base href="<%=basePath%>>">
    <!--JQUERY-->
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <!--BOOTSTRAP框架-->
    <link rel="stylesheet" type="text/css" href="jquery/bootstrap_3.3.0/css/bootstrap.min.css">
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <%--bs_pagination--%>
    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>
    <title>Title</title>
    <script>
        $(function () {
            $("#myPagination").bs_pagination({
                currentPage: 1,
                rowsPerPage: 10,
                maxRowsPerPage: 100,
                totalPages: 100,
                totalRows: 0,

                visiblePageLinks: 5,

                showGoToPage: true,
                showRowsPerPage: true,
                showRowsInfo: true,
                showRowsDefaultInfo: true,

                directURL: false, // or a function with current page as argument
                disableTextSelectionInNavPane: true, // disable text selection and double click

                bootstrap_version: "3",

                // bootstrap 3
                containerClass: "well",

                mainWrapperClass: "row",

                navListContainerClass: "col-xs-12 col-sm-12 col-md-6",
                navListWrapperClass: "",
                navListClass: "pagination pagination_custom",
                navListActiveItemClass: "active",

                navGoToPageContainerClass: "col-xs-6 col-sm-4 col-md-2 row-space",
                navGoToPageIconClass: "glyphicon glyphicon-arrow-right",
                navGoToPageClass: "form-control small-input",

                navRowsPerPageContainerClass: "col-xs-6 col-sm-4 col-md-2 row-space",
                navRowsPerPageIconClass: "glyphicon glyphicon-th-list",
                navRowsPerPageClass: "form-control small-input",

                navInfoContainerClass: "col-xs-12 col-sm-4 col-md-2 row-space",
                navInfoClass: "",

                // element IDs
                nav_list_id_prefix: "nav_list_",
                nav_top_id_prefix: "top_",
                nav_prev_id_prefix: "prev_",
                nav_item_id_prefix: "nav_item_",
                nav_next_id_prefix: "next_",
                nav_last_id_prefix: "last_",

                nav_goto_page_id_prefix: "goto_page_",
                nav_rows_per_page_id_prefix: "rows_per_page_",
                nav_rows_info_id_prefix: "rows_info_",

                onChangePage: function(event,data) { // returns page_num and rows_per_page after a link has clicked
                    console.log('Current page is: ' + data.currentPage + '. ' + data.rowsPerPage + ' are displayed per page.');
                },
                onLoad: function(event,data) { // returns page_num and rows_per_page on plugin load
                    console.log('Current page is: ' + data.currentPage + '. ' + data.rowsPerPage + ' are displayed per page.');
                }
            });
        })

    </script>
</head>
<body>

<div id="myPagination"></div>

</body>
</html>
