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
            @media screen and (min-width: 992px) {
                .modal-lg {
                    width: 1080px; /* New width for large modal */
                }
            }
        </style>
    </head>

    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pp-apcp-sidebar.jspf" %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong><i class="fa fa-file-o"></i> View Agrarian Production Credit Program Requests</strong>
                        <small><%out.print((String) session.getAttribute("provOfficeDesc") + ", " + (String) session.getAttribute("regOfficeDesc"));%></small>
                    </h1>
                </section>
                    
                <section class="content">
                    
                    <%if(issueDAO.retrieveUnresolvedIssues((Integer)session.getAttribute("userType"),(Integer)session.getAttribute("provOfficeCode")).size() > 0){%>
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-ban"></i> You have <a href="view-issues.jsp">ISSUES</a> pending. Please resolve them immediately.</h4>
                    </div>
                    <%}%>

                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>For Release</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table class="table table-bordered table-striped modTable">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Loan Reason</th>
                                                <th>Loan Amount</th>
                                                <th>Land Area</th>
                                                <th>Date Approved</th>
                                                <th>Remarks</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <%
                                                for(APCPRequest r : forReleaseRequests){
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>

                                                <td><a href="MonitorRelease?id=<%out.print(r.getRequestID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <%if(r.getLoanReason().getLoanReason() == 0){%> <!--OTHERS-->
                                                <td><%out.print(r.getLoanReason().getLoanReasonDesc() + ": " + r.getLoanReason().getOtherReason());%></td>
                                                <%}else{%> <!--LOAN REASON-->
                                                <td><%out.print(r.getLoanReason().getLoanReasonDesc());%></td>
                                                <%}%>
                                                <td><%out.print(currency.format(r.getLoanAmount()));%></td>
                                                <td><%out.print(r.getHectares() + " hectares");%></td>
                                                <td><%out.print(r.getDateApproved());%></td>
                                                <td><%out.print(r.getRemarks());%></td>
                                                <td><%out.print(r.getRequestStatusDesc());%></td>
                                            </tr>
                                            <%}%>
                                        </tbody>

                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>                    

                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Released</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table class="table table-bordered table-striped modTable">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Loan Reason</th>
                                                <th>Loan Amount</th>
                                                <th>Land Area</th>
                                                <th>Date Released</th>
                                                <th>Remarks</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <%
                                                for(APCPRequest r : releasedRequests){
                                                r.setReleases(apcpRequestDAO.getAllAPCPReleasesByRequest(r.getRequestID()));
                                                ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>
                                                <td><a href="MonitorRelease?id=<%out.print(r.getRequestID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <%if(r.getLoanReason().getLoanReason() == 0){%> <!--OTHERS-->
                                                <td><%out.print(r.getLoanReason().getLoanReasonDesc() + ": " + r.getLoanReason().getOtherReason());%></td>
                                                <%}else{%> <!--LOAN REASON-->
                                                <td><%out.print(r.getLoanReason().getLoanReasonDesc());%></td>
                                                <%}%>
                                                <td><%out.print(currency.format(r.getLoanAmount()));%></td>
                                                <td><%out.print(r.getHectares() + " hectares");%></td>
                                                <td><%out.print(r.getDateLastReleasedPerRequest());%></td>
                                                <td><%out.print(r.getRemarks());%></td>
                                                <td><%out.print(r.getRequestStatusDesc());%></td>
                                            </tr>
                                            <%}%>

                                        </tbody>

                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>
                    <!-- /.row -->
                </section>
                <!-- /.content -->
            </div>
        </div>
        <!-- /.row -->

        <!-- /.content -->


        <!-- /.content-wrapper -->

        <%@include file="jspf/footer.jspf" %>

        <script type="text/javascript">
            function chg() {
                var arboID = $('input[name=arboID]:checked').val();
                var xhttp = new XMLHttpRequest();

                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState === 4 && xhttp.status === 200) {
                        document.getElementById('arboName').innerHTML = xhttp.responseText;
                    }
                };
                xhttp.open("GET", "ARBONameRefresh?valajax=" + arboID, true);
                xhttp.send();
            }
        </script>
    </body>
</html>
