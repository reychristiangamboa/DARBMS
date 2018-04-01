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
            <%if ((Integer) session.getAttribute("userType") == 3) {%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 4) {%>
            <%@include file="jspf/regional-field-officer-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 5) {%>
            <%@include file="jspf/central-sidebar.jspf"%>
            <%}%>
            <%
                CAPDEVDAO capdevDAO = new CAPDEVDAO();
                LINKSFARMDAO linksFarmDAO = new LINKSFARMDAO();
                
                CAPDEVPlan p = capdevDAO.getCAPDEVPlan((Integer)request.getAttribute("planID"));
                Cluster c = linksFarmDAO.getClusterByID(p.getClusterID());
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

                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">CAPDEV Information</h3>
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

                                                            <div class="form-group col-xs-4">
                                                                <label>Date:</label>

                                                                <div class="input-group date">
                                                                    <div class="input-group-addon">
                                                                        <i class="fa fa-calendar"></i>
                                                                    </div>
                                                                    <input type="text" class="form-control pull-right" id="datepicker" value="<%out.print(f.format(b.getActivityDate()));%>" disabled>
                                                                </div>
                                                                <!-- /.input group -->
                                                            </div>

                                                            <div class="form-group col-xs-4">
                                                                <label>Activity Type: </label>
                                                                <input type="text" class="form-control" value="<%=b.getActivityCategoryDesc()%>" disabled>
                                                            </div>
                                                            
                                                            <div class="form-group col-xs-4">
                                                                <label for="">Technical Assistant: </label>
                                                                <%if(p.getPlanStatus() != 5){%>
                                                                <input type="text" class="form-control" value="Not yet implemented." disabled>
                                                                <%}else{%>
                                                                <input type="text" class="form-control" value="<%=b.getTechnicalAssistant()%>" disabled>
                                                                <%}%>
                                                            </div>

                                                            <div class="row col-xs-12">
                                                                <div class="col-xs-12">
                                                                    <label for=''>Observations</label>
                                                                    <%if(p.getPlanStatus() != 5){%>
                                                                    <textarea class="form-control" rows="3" disabled>Not yet implemented.</textarea>
                                                                    <%}else{%>
                                                                    <textarea class="form-control" rows="3" disabled><%out.print(b.getObservations());%></textarea>
                                                                    <%}%>
                                                                </div>

                                                            </div>
                                                            <div class="row col-xs-12">
                                                                <div class="col-xs-12">
                                                                    <label for=''>Recommendations</label>
                                                                    <%if(p.getPlanStatus() != 5){%>
                                                                    <textarea class="form-control" rows="3" disabled>Not yet implemented.</textarea>
                                                                    <%}else{%>
                                                                    <textarea class="form-control" rows="3" disabled><%out.print(b.getRecommendation());%></textarea>
                                                                    <%}%>
                                                                </div>

                                                            </div>
                                                        </div>
                                                    </div>
                                                    <hr>
                                                    <%if(p.getPlanStatus() != 5){%>
                                                    <div class="row" style="margin-top:20px;">
                                                        <div class="col-xs-12">
                                                            <h4 style="margin-bottom:-10px">Participants</h4>
                                                            <hr>
                                                            <table class="table table-bordered table-striped modTable">
                                                                <thead>
                                                                    <tr>
                                                                        <th>Cluster Members</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <%for(ARB arb : b.getArbList()){%>
                                                                    <tr>
                                                                        <td><a href="ViewARB?id=<%out.print(arb.getArbID());%>"><%out.print(arb.getFullName());%></a></td>
                                                                    </tr>
                                                                    <%}%>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                    <%}else{%>
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
                                                                            <th>ARB</th>
                                                                        </tr>
                                                                    </thead>

                                                                    <tbody>
                                                                        <%for(ARB pARB : present){%>
                                                                        <tr>
                                                                            <td><a href="ViewARB?id=<%out.print(pARB.getArbID());%>"><%out.print(pARB.getFullName());%></a></td>
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
                                                                            <th>ARB</th>
                                                                        </tr>
                                                                    </thead>

                                                                    <tbody>
                                                                        <%for(ARB aARB : absent){%>
                                                                        <tr>
                                                                            <td><a href="ViewARB?id=<%out.print(aARB.getArbID());%>"><%out.print(aARB.getFullName());%></a></td>
                                                                        </tr>
                                                                        <%}%>
                                                                    </tbody>

                                                                </table>
                                                            </div>                                                               
                                                        </div>
                                                    </div>
                                                    <%}%>

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
                                        <%if((Integer)session.getAttribute("userType") == 3){%>
                                        <a class="btn btn-primary" href="CreateLINKSFARMCAPDEVProposal?clusterID=<%out.print(c.getClusterID());%>">Create CAPDEV Proposal</a>
                                        <%}%>
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
