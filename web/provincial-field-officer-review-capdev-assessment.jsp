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
        <style>
            .example-modal .modal {
                position: relative;
                top: auto;
                bottom: auto;
                right: auto;
                left: auto;
                display: block;
                z-index: 1;
            }

            .example-modal .modal {
                background: transparent !important;
            }
        </style>
    </head>

    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf" %>
            <%
                CAPDEVPlan p = capdevDAO.getCAPDEVPlan((Integer)request.getAttribute("planID"));
                APCPRequest r = apcpRequestDAO.getRequestByID(p.getRequestID());
                ARBO a = arboDAO.getARBOByID(r.getArboID());
                ArrayList<CAPDEVActivity> caList = capdevDAO.getCAPDEVPlanActivities((Integer)request.getAttribute("planID"));
                
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
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">ARBO Information</h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>  
                                </div>
                                <!-- /.box-header -->


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
                                            <div class="chart tab-pane <%if(count2 == 0){out.print("active");}%>" id="activity<%out.print(b.getActivityID());%>" style="position: relative;">
                                                
                                                <div class="box-body">

                                                    <div class="row">
                                                        <div class="col-xs-12">

                                                            <div class="form-group col-xs-6">
                                                                <label>Date:</label>

                                                                <div class="input-group date">
                                                                    <div class="input-group-addon">
                                                                        <i class="fa fa-calendar"></i>
                                                                    </div>
                                                                    <input type="text" class="form-control pull-right" id="datepicker" value="<%out.print(f.format(b.getActivityDate()));%>" disabled>
                                                                </div>
                                                                <!-- /.input group -->
                                                            </div>

                                                            <div class="row col-xs-12">
                                                                <div class="col-xs-12">
                                                                    <label for=''>Observations</label>
                                                                    <textarea class="form-control" rows="3" disabled><%out.print(b.getObservations());%></textarea>
                                                                </div>

                                                            </div>
                                                            <div class="row col-xs-12">
                                                                <div class="col-xs-12">
                                                                    <label for=''>Recommendations</label>
                                                                    <textarea class="form-control" rows="3" disabled><%out.print(b.getRecommendation());%></textarea>
                                                                </div>

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <hr>
                                                    <div class="row" style="margin-top:20px;">
                                                        <div class="col-xs-12">
                                                            <div class="col-xs-6">
                                                                <h4 style="margin-bottom:-10px">Present ARBs</h4>
                                                                <%ArrayList<ARB> present = capdevDAO.getCAPDEVPlanActivityParticipantsAttendance(b.getActivityID(),1);%>
                                                                <hr>
                                                                <%count2++;%> 
                                                                <table id="attendance<%out.print(count2);%>" class="table table-bordered table-striped">
                                                                    <thead>
                                                                        <tr>
                                                                            <th>ARB Full Name</th>
                                                                        </tr>
                                                                    </thead>

                                                                    <tbody>
                                                                        <%for(ARB pARB : present){%>
                                                                        <tr>
                                                                            <td><%out.print(pARB.getFullName());%></td>
                                                                        </tr>
                                                                        <%}%>
                                                                    </tbody>

                                                                </table>
                                                            </div>
                                                            <%count2++;%>         
                                                            <div class="col-xs-6">
                                                                <h4 style="margin-bottom:-10px">Absent ARBs</h4>
                                                                <%ArrayList<ARB> absent = capdevDAO.getCAPDEVPlanActivityParticipantsAttendance(b.getActivityID(),0);%>
                                                                <hr>
                                                                <table id="attendance<%out.print(count2);%>" class="table table-bordered table-striped">
                                                                    <thead>
                                                                        <tr>
                                                                            <th>ARB Full Name</th>
                                                                        </tr>
                                                                    </thead>

                                                                    <tbody>
                                                                        <%for(ARB aARB : absent){%>
                                                                        <tr>
                                                                            <td><%out.print(aARB.getFullName());%></td>
                                                                        </tr>
                                                                        <%}%>
                                                                    </tbody>

                                                                </table>
                                                            </div>                                                               
                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="chart tab-pane" id="info" style="position: relative;">


                                                </div>
                                                <div class="chart tab-pane" id="history" style="position: relative;">


                                                </div>
                                            </div>
                                             <%count2++;%>                         
                                            <%}%>
                                        </div>
                                        <hr>        
                                    </div>
                                    <!-- /.box-body -->



                                </div>
                                <div class="box-footer">
                                    <div class= "pull-right">
                                        <a class="btn btn-primary" href="CreateCAPDEVProposal?id=<%out.print(p.getRequestID());%>">Create CAPDEV Proposal</a>
                                    </div>
                                </div>
                            </div>
                            <!-- /.col -->
                        </div>
                </section> 
                <!-- /.content -->

            </div>
            <!-- /.content-wrapper -->
        </div>
        <%@include file="jspf/footer.jspf" %>
    </body>
</html>
