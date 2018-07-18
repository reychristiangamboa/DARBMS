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
            <%@include file="jspf/pp-capdev-sidebar.jspf" %>

            <%
                CAPDEVPlan plan = capdevDAO.getCAPDEVPlan((Integer)request.getAttribute("planID"));
            %>

            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        <strong><i class="fa fa-check-circle"></i> Check Attendance</strong>
                        <small><%out.print((String) session.getAttribute("provOfficeDesc") + ", " + (String) session.getAttribute("regOfficeDesc"));%></small>
                    </h1>
                </section>
                <section class="content">
                    <%if (request.getAttribute("success") != null) {%>
                    <div class="alert alert-success alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-check"></i> <%out.print((String) request.getAttribute("success"));%></h4>
                    </div>
                    <%} else if (request.getAttribute("errMessage") != null) {%>
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-ban"></i> <%out.print((String) request.getAttribute("errMessage"));%></h4>
                    </div>
                    <%}%>
                    <div class='row'>
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Attendance</h3>
                                </div>
                                <div class="box-body">


                                    <div class="nav-tabs-custom">
                                        <% int count = 0;  %>
                                        <ul class="nav nav-tabs pull-left">
                                            <%
                                                plan.setActivities(capdevDAO.getCAPDEVPlanActivities(plan.getPlanID()));
                                                for (CAPDEVActivity a1 : plan.getActivities()) {
                                            %>
                                            <li <%if (count == 0) {
                                                    out.print("class='active'");
                                                }%>><a href="#activity<%out.print(a1.getActivityID());%>" data-toggle="tab"><%out.print(a1.getActivityName());%></a></li>
                                                <%count++;%>
                                                <%}%>
                                        </ul>

                                        <% int count2 = 0;  %>
                                        <div class="tab-content no-padding">
                                            <%for (CAPDEVActivity b : plan.getActivities()) {%>
                                            <div class="chart tab-pane <%if (count2 == 0) {
                                                    out.print("active");
                                                }%>" id="activity<%out.print(b.getActivityID());%>" style="position: relative; height: 300px;">
                                                <%count2++;%>

                                                <form method="post">
                                                    <div class="box-body">
                                                        <div class="row">
                                                            <div class="col-xs-4">
                                                                <label>Date:</label>
                                                                <div class="input-group date">
                                                                    <div class="input-group-addon">
                                                                        <i class="fa fa-calendar"></i>
                                                                    </div>
                                                                    <input type="text" value="<%out.print(f.format(plan.getPlanDate()));%>" class="form-control pull-right" id="datepicker" disabled>
                                                                </div>
                                                            </div>

                                                        </div>
                                                        <div class="row">
                                                            <div class="col-xs-4">
                                                                <div class="form-group">
                                                                    <input type="radio" name="attendanceMethod" checked onclick="document.getElementById('manualAttendance<%out.print(b.getActivityID());%>').style.display = 'block';document.getElementById('importAttendance<%out.print(b.getActivityID());%>').style.display = 'none'">
                                                                    <label for="">Check Attendees</label>
                                                                    &nbsp;&nbsp;
                                                                    <input type="radio" name="attendanceMethod" onclick="document.getElementById('manualAttendance<%out.print(b.getActivityID());%>').style.display = 'none';document.getElementById('importAttendance<%out.print(b.getActivityID());%>').style.display = 'block'">
                                                                    <label for="">Import Attendees</label>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-xs-4" id="importAttendance<%out.print(b.getActivityID());%>"  style="display:none">
                                                                <label for="">Upload Participants</label>
                                                                <input type="file" name="file" disabled>
                                                            </div>
                                                            <div class="col-xs-12" id="manualAttendance<%out.print(b.getActivityID());%>">
                                                                <table class="table table-striped table-bordered modTable">
                                                                    <thead>
                                                                        <tr>
                                                                            <td>Action</td>
                                                                            <td>Full Name</td>
                                                                            <td>COMAT</td>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>

                                                                        <%
                                                                            b.setArbList(capdevDAO.getCAPDEVPlanActivityParticipants(b.getActivityID()));
                                                                            for (ARB arb : b.getArbList()) {
                                                                        %>
                                                                        <tr>
                                                                            <%if(arb.getIsPresent() == 1){ // PRESENT %>
                                                                            <td><input type="checkbox" name="attendee" value="<%out.print(arb.getArbID());%>" checked disabled /></td>
                                                                                <%}else if(arb.getIsPresent() == 2){ // REPLACED %>
                                                                            <td><input type="checkbox" disabled /></td>
                                                                                <%}else{ // AVAILABLE/ABSENT %>
                                                                            <td><input type="checkbox" name="attendee" value="<%out.print(arb.getArbID());%>" /></td>
                                                                                <%}%>
                                                                            <td><%out.print(arb.getFullName());%></td>

                                                                            <%if(arb.getIsCOMAT() > 0){%>
                                                                            <td>&checkmark;</td>
                                                                            <%}else{%>
                                                                            <td>&nbsp;</td>
                                                                            <%}%>
                                                                        </tr>
                                                                        <%}%>

                                                                    </tbody>
                                                                    <tfoot>
                                                                        <tr>
                                                                            <td colspan="3">
                                                                                <%if(b.getActive() == 1){ // Activity Assessment Recorded, both BUTTONS disabled %>
                                                                                <button type="button" class="btn btn-success" disabled>Check Attendance</button>
                                                                                <button type="button" class="btn btn-primary" disabled>Replace</button>
                                                                                <%}else{ // BUTTONS are clickable %>
                                                                                <button type="button" class="btn btn-success" data-toggle="modal" data-target="#attendanceModal<%out.print(b.getActivityID());%>">Check Attendance</button>
                                                                                <%if(b.getNonParticipantARBs().isEmpty()){ // IF ALL ARE INVITED, disabled %>
                                                                                <button type="button" class="btn btn-primary" disabled>Replace</button>
                                                                                <%}else{%>
                                                                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#replaceModal<%out.print(b.getActivityID());%>">Replace</button>
                                                                                <%}%>
                                                                                <%}%>
                                                                            </td>
                                                                        </tr>
                                                                    </tfoot>
                                                                </table>

                                                                <div id="replaceModal<%out.print(b.getActivityID());%>" class="modal fade" role="dialog">
                                                                    <div class="modal-dialog">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                                                <h4 class="modal-title">Replace Attendee</h4>
                                                                            </div>
                                                                            <div class="modal-body">
                                                                                <table class="table table-bordered table-striped modTable">
                                                                                    <thead>
                                                                                        <tr>
                                                                                            <th>Action</th>
                                                                                            <th>ARB</th>
                                                                                            <th>COMAT</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                    <tbody>
                                                                                        <% for(ARB nonParticipant : b.getNonParticipantARBs()){ %>
                                                                                        <tr>
                                                                                            <td><input type="checkbox" name="replacee" value="<%out.print(nonParticipant.getArbID());%>" /></td>
                                                                                            <td><%out.print(nonParticipant.getFLName());%></td>
                                                                                            <%if(nonParticipant.getIsCOMAT() > 0){%>
                                                                                            <td>&checkmark;</td>
                                                                                            <%}else{%>
                                                                                            <td>&nbsp;</td>
                                                                                            <%}%>
                                                                                        </tr>
                                                                                        <% } %>
                                                                                    </tbody>
                                                                                </table>
                                                                            </div>
                                                                            <div class="modal-footer">
                                                                                <button class="btn btn-default" data-dismiss="modal">Cancel</button>
                                                                                <button type="submit" class="btn btn-primary" onclick="form.action = 'ReplaceParticipant?planID=<%out.print(plan.getPlanID());%>&activityID=<%out.print(b.getActivityID());%>'">Replace</button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div id="attendanceModal<%out.print(b.getActivityID());%>" class="modal fade" role="dialog">
                                                                    <div class="modal-dialog">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                                                <h4 class="modal-title">Confirm Attendance</h4>
                                                                            </div>
                                                                            <div class="modal-body">
                                                                                <div class="row">
                                                                                    <div class="col-xs-4">
                                                                                        <div class="form-group">
                                                                                            <label for="">Technical Assistant</label>
                                                                                            <%if (b.getTechnicalAssistant() != null) {%>
                                                                                            <input type="text" name="TA" class="form-control" value="<%=b.getTechnicalAssistant()%>" disabled>
                                                                                            <%} else {%>
                                                                                            <input type="text" name="TA" class="form-control">
                                                                                            <%}%>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <hr>
                                                                                <div class="row">
                                                                                    <center>
                                                                                        <p>Unchecked participants will be considered absent. Proceed?</p>
                                                                                    </center>
                                                                                </div>
                                                                            </div>
                                                                            <div class="modal-footer">
                                                                                <button class="btn btn-default" data-dismiss="modal">Cancel</button>
                                                                                <button class="btn btn-primary" onclick="form.action = 'CheckAttendance?planID=<%out.print(plan.getPlanID());%>&activityID=<%out.print(b.getActivityID());%>'">Confirm</button>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </form>
                                            </div>

                                            <%}%>
                                        </div>
                                    </div>


                                    <div class="box-footer">
                                        <div class="pull-right">
                                            <input type="hidden" value="<%=(Integer) request.getAttribute("planID")%>" name="planID">
                                            <% if (plan.assessmentsAreComplete()) { %>
                                            <button type="button" class="btn btn-success" data-toggle="modal" data-target="#assessmentModal">Submit Plan Assessment</button>
                                            <% }else{ %>
                                            <button class="btn btn-success" id="sendCapdevAssessment" disabled>Submit Plan Assessment</button>
                                            <% } %>
                                        </div>  
                                    </div>

                                    <div id="assessmentModal" class="modal fade" role="dialog">
                                        <div class="modal-dialog modal-lg">
                                            <form method="post">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                        <h4 class="modal-title">CAPDEV Plan Assessment</h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="row">
                                                            <div class="col-xs-3">
                                                                <label>Implementation Date: </label>
                                                                <div class="input-group date">
                                                                    <div class="input-group-addon">
                                                                        <i class="fa fa-calendar"></i>
                                                                    </div>
                                                                    <input type="date" name="implementationDate" class="form-control" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-xs-12">
                                                                <div class="form-group">
                                                                    <label for="">Observation/s</label>
                                                                    <textarea name="observation" id="" cols="10" rows="5" class="form-control"></textarea>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-xs-12">
                                                                <div class="form-group">
                                                                    <label for="">Recommendation/s</label>
                                                                    <textarea name="recommendation" id="" cols="10" rows="5" class="form-control"></textarea>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button class="btn btn-default" data-dismiss="modal">Cancel</button>
                                                        <button type="submit" class="btn btn-success" onclick="form.action = 'SendCAPDEVAssessment?planID=<%out.print(plan.getPlanID());%>'">Submit</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>


                                </div>
                            </div>

                        </div>
                    </div>    
                </section>
            </div>

        </div>

        <%@include file="jspf/footer.jspf" %>
    </body>
</html>
