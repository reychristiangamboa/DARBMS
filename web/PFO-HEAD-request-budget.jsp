<%-- 
    Document   : PFO-HEAD-request-budget
    Created on : Jul 13, 2018, 8:31:39 PM
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
            <%@include file="jspf/field-officer-navbar.jspf"%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>

                        <strong><i class="fa fa-money"></i> Request Budget</strong> 
                        <small><%out.print((String) session.getAttribute("provOfficeDesc") + ", " + (String) session.getAttribute("regOfficeDesc"));%></small>

                    </h1>

                </section>
                <section class="content">

                    <%if(request.getAttribute("success") != null){%>
                    <div class="alert alert-success alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-check"></i> <%out.print((String)request.getAttribute("success"));%></h4>
                    </div>
                    <%}%>

                    <form method='post'>
                        <div class="box box-default">
                            <div class="box-header with-border">
                                <h3 class="box-title">Provincial Budgets</h3>
                            </div>
                            <div class="box-body">

                                <div class="row">
                                    <div class="col-xs-3">
                                        <div class="form-group">
                                            <label>Budget Type</label>
                                            <select class="form-control" name="budgetType">
                                                <option value='1' selected>APCP</option>
                                                <option value='2'>CAPDEV</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3">
                                        <div class="form-group">
                                            <label>Budget</label>
                                            <div class="input-group">
                                                <div class="input-group-addon">
                                                    <i>&#8369;</i>
                                                </div>
                                                <input name="budgetRequested" class="form-control numberOnly" required />
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-xs-6">
                                        <div class="form-group">
                                            <label>Reason for Request</label>
                                            <textarea class="form-control" rows="2" name='reason'></textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="box-footer">
                                <button type="submit" onclick="form.action = 'RequestBudget'" class="btn btn-success pull-right">Submit</button>
                            </div>

                        </div>

                    </form>
                </section>
            </div>
        </div>
        <%@include file="jspf/footer.jspf"%>
    </body>
</html>
