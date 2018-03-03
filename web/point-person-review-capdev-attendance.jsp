<%-- 
    Document   : technical-assistant-review-capdev-attendance
    Created on : 02 25, 18, 12:47:54 AM
    Author     : Z40
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
                APCPRequest r = apcpRequestDAO.getRequestByID((Integer)request.getAttribute("requestID"));
                ARBO a = arboDAO.getARBOByID(r.getArboID());
                ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(r.getArboID());
                ArrayList<CAPDEVActivity> caList = capdevDAO.getCAPDEVPlanActivities((Integer)request.getAttribute("planID"));
            %>

            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        <strong>APCP</strong> 
                        <small>Region I</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="field-officer-arbo-list.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    </ol>

                </section>
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
                    <div class='row'>
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Attendance</h3>
                                </div>
                                <div class="box-body">
                                    <div class="box">
                                        <div class="box-body">
                                            <div class="nav-tabs-custom">
                                                <!-- Tabs within a box -->

                                                <% int count = 0;  %>
                                                <ul class="nav nav-tabs pull-left">
                                                    <%for(CAPDEVActivity a1 : caList){%>
                                                    <li <%if(count == 0){out.print("class='active'");}%>><a href="#activity<%out.print(a1.getActivityID());%>" data-toggle="tab"><%out.print(a1.getActivityName());%></a></li>
                                                        <%count++;%>
                                                        <%}%>
                                                </ul>

                                                <% int count2 = 0;  %>
                                                <div class="tab-content no-padding">
                                                    <%for(CAPDEVActivity b : caList){%>
                                                    <div class="chart tab-pane <%if(count2 == 0){out.print("active");}%>" id="activity<%out.print(b.getActivityID());%>" style="position: relative; height: 300px;">
                                                        <%count2++;%>
                                                        <div class="box-body">
                                                            <div class="row">
                                                                <div class="col-xs-4">
                                                                    <label>Date:</label>
                                                                    <div class="input-group date">
                                                                        <div class="input-group-addon">
                                                                            <i class="fa fa-calendar"></i>
                                                                        </div>
                                                                        <input type="text" value="<%out.print(f.format(b.getActivityDate()));%>" class="form-control pull-right" id="datepicker" disabled>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <form method="post">
                                                            <div class="box-body">
                                                                <div class="row">
                                                                    <div class="col-xs-4">
                                                                        
                                                                        <label for="">Upload Participants</label>
                                                                        <input type="file" name="file">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="box-body">
                                                                <div class="row">
                                                                    <div class="col-xs-4">
                                                                        <label for="">Observations</label>
                                                                        <%if(b.getObservations() != null){%>
                                                                        <textarea class="form-control" name="observations" id="" rows="2"><%out.print(b.getObservations());%></textarea>
                                                                        <%}else{%>
                                                                        <textarea class="form-control" name="observations" id="" rows="2"></textarea>
                                                                        <%}%>
                                                                    </div>
                                                                    <div class="col-xs-4">
                                                                        <label for="">Recommendations</label>
                                                                        <%if(b.getRecommendation() != null){%>
                                                                        <textarea class="form-control" name="recommendation" id="" rows="2"><%out.print(b.getRecommendation());%></textarea>
                                                                        <%}else{%>
                                                                        <textarea class="form-control" name="recommendation" id="" rows="2"></textarea>
                                                                        <%}%>
                                                                    </div>
                                                                </div>

                                                            </div>
                                                            <div class="box-footer">
                                                                <input type="hidden" value="<%=b.getActivityID()%>" name="activityID">
                                                                <input type="hidden" value="<%=r.getRequestID()%>" name="requestID">
                                                                <input type="hidden" value="<%=(Integer)request.getAttribute("planID")%>" name="planID">
                                                                <button class="btn btn-success pull-right" onclick="form.action='RecordActivityAssessment'" >Submit</button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                    <%}%>
                                                </div>
                                            </div>
                                        </div>
                                                <form method="post"></form>
                                        <div class="box-footer">
                                            <button class="btn btn-success pull-right">Submit</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>    
                </section>
            </div>

        </div>
        <footer class="main-footer">
            <div class="pull-right hidden-xs">
                <b>Version</b> 2.4.0
            </div>
            <strong>Copyright &copy; 2014-2016 <a href="https://adminlte.io">Almsaeed Studio</a>.</strong> All rights
            reserved.
        </footer>
    </div>
    <%@include file="jspf/footer.jspf" %>

</body>
</html>
