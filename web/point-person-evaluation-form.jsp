
<%-- 
    Document   : field-officer-arbo-list
    Created on : Jan 29, 2018, 4:08:13 PM
    Author     : Rey Christian
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="jspf/header.jspf"%>
    </head>

    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/point-person-sidebar.jspf" %>
            <%
            Evaluation e = eDAO.getEvaluationByID((Integer)request.getAttribute("evaluationID"));
            ArrayList<Question> questions = eDAO.getAllQuestionsByType(e.getEvaluationType());
            %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong>APCP</strong> 
                        <small>Region I</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="field-officer-arbo-list.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    </ol>

                </section>

                <!-- Main content -->
                <section class="content">
                    <%if(request.getAttribute("success") != null){%>
                    <div class="alert alert-success alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-check"></i> <%out.print((String)request.getAttribute("success"));%></h4>
                    </div>
                    <%}else if(request.getAttribute("errMessage") != null){%>
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-ban"></i> <%out.print((String)request.getAttribute("errMessage"));%></h4>
                    </div>
                    <%}%>
                    
                    <div class="row">
                        <div class="col-xs-12">

                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong><%out.print(e.getEvaluationTypeDesc());%> Evaluation</strong></h3>
                                </div>
                                <!--                                 /.box-header -->
                                <form method="post">
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <table class="table table-bordered table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th>Question</th>
                                                            <th>1</th>
                                                            <th>2</th>
                                                            <th>3</th>
                                                            <th>4</th>
                                                            <th>5</th>
                                                        </tr>
                                                    </thead>

                                                    <tbody>
                                                        <%for(Question q : questions){%>
                                                        <tr>
                                                            <td><%out.print(q.getQuestion());%></td>
                                                            <td>   
                                                                <input type="radio" name="<%out.print(q.getQuestionID());%>" value="1" required>
                                                            </td>
                                                            <td>   
                                                                <input type="radio" name="<%out.print(q.getQuestionID());%>"  value="2">
                                                            </td>
                                                            <td>   
                                                                <input type="radio" name="<%out.print(q.getQuestionID());%>"  value="3">
                                                            </td>
                                                            <td>   
                                                                <input type="radio" name="<%out.print(q.getQuestionID());%>"  value="4">
                                                            </td>
                                                            <td>   
                                                                <input type="radio" name="<%out.print(q.getQuestionID());%>"  value="5">
                                                            </td>
                                                        </tr>
                                                        <%}%>
                                                    </tbody>

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="box-footer">
                                        <input type="hidden" name="evaluationID" value="<%=e.getEvaluationID()%>">
                                        <button type="submit" onclick="form.action='ProcessEvaluation'" class="btn btn-primary pull-right">Submit</button>
                                    </div>
                                </form>
                            </div>
                            <!--                             /.box-body -->
                        </div>
                        <!--                         /.box -->
                    </div>
                    <!--                     /.col -->
                </section>
            </div>
            <!-- /.row -->

            <!-- /.content -->
            
        </div>

        <!-- /.content-wrapper -->

        <%@include file="jspf/footer.jspf" %>

        <script type="text/javascript">
            function chg() {
                var regionVal = document.getElementById('regionDrop').value;

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState === 4 && xhttp.status === 200) {
                        document.getElementById('provinceDiv').innerHTML = xhttp.responseText;
                    }
                };
                xhttp.open("GET", "RegionRefresh?valajax=" + regionVal, true);
                xhttp.send();
            }

            function chg2() {
                var provinceVal = document.getElementById('provinceDrop').value;

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState === 4 && xhttp.status === 200) {
                        document.getElementById('cityDiv').innerHTML = xhttp.responseText;
                    }
                };
                xhttp.open("GET", "ProvinceRefresh?valajax=" + provinceVal, true);
                xhttp.send();
            }

            $(document).ready(function () {
                $('#regionDrop').on('change', function (e) {
                    $('#provinceDrop').removeAttr('disabled');
                });
                $('#provinceDrop').on('change', function (e) {
                    $('#cityDrop').removeAttr('disabled');
                });
            });
        </script>
    </body>
</html>
