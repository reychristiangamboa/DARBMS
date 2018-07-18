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

            <%@include file="jspf/field-officer-navbar.jspf"%>

            <%if ((Integer) session.getAttribute("userType") == 1) {%>
            <%@include file="jspf/admin-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 2) {%>
            <%@include file="jspf/pp-apcp-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 3) {%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 4) {%>
            <%@include file="jspf/regional-field-officer-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 5) {%>
            <%@include file="jspf/central-sidebar.jspf"%>
            <%}%>
            <%
                ARBDAO arbDAO99 = new ARBDAO();
                ARBODAO arboDAO99 = new ARBODAO();
                APCPRequestDAO apcpRequestDAO99 = new APCPRequestDAO();
                CAPDEVDAO capdevDAO99 = new CAPDEVDAO();
                UserDAO uDAO99 = new UserDAO();
                
                ARBODAO arboDAO = new ARBODAO();
                APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();
                ARBDAO arbDAO = new ARBDAO();
                UserDAO userDAO = new UserDAO();
                
                ArrayList<CAPDEVActivity> activities = capdevDAO99.getCAPDEVActivities();
                APCPRequest r = apcpRequestDAO99.getRequestByID((Integer)request.getAttribute("requestID"));
                
                ArrayList<ARB> arbList = arbDAO99.getAllARBsARBO(r.getArboID());
                ArrayList<APCPRelease> releaseList = apcpRequestDAO99.getAllAPCPReleasesByRequest((Integer) request.getAttribute("requestID"));
                ArrayList<PastDueAccount> reasons = capdevDAO99.getAllPastDueReasons();
                
                
                    
        int reqID = (Integer) request.getAttribute("requestID");
        APCPRequest req = apcpRequestDAO99.getRequestByID(reqID);
        ARBO arbo = arboDAO99.getARBOByID(req.getArboID());
        
            %>                                        
            <% User u1 = new User(); %>
            <% User u2 = new User(); %>
            <% User u3 = new User(); %>
            <% User u4 = new User(); %>
            %>


            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <i class="fa fa-money"></i> Monitor Releases
                    </h1>
                </section>


                <!-- Main content -->
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
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">APCP Information</h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>  
                                </div>  
                                <div class="box-body"> 
                                    <div class="box-group" id="accordion">
                                        <!--ARBO-->
                                        <div class="panel box box-info">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
                                                        ARBO Information
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseFour" class="panel-collapse collapse">
                                                <div class="box-body">
                                                    <%@include file="jspf/arbo-information.jspf" %>
                                                </div>
                                            </div> 
                                        </div>
                                        <!--APCP-->
                                        <div class="panel box box-info">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseFive">
                                                        APCP Request Information
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseFive" class="panel-collapse collapse">
                                                <div class="box-body">
                                                    <%@include file="jspf/apcp-request-info.jspf" %>
                                                </div>
                                            </div>
                                        </div>

                                        <!--RELEASES-->
                                        <div class="panel box box-info">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                                        Release Information
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseTwo" class="panel-collapse collapse in">
                                                <div class="box-body">
                                                    <%@include file="jspf/release-info.jspf" %>
                                                </div>
                                            </div>
                                        </div>

                                        <!--DISBURSEMENTS-->
                                        <div class="panel box box-info">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseWEW">
                                                        Disbursement Information
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseWEW" class="panel-collapse collapse">
                                                <div class="box-body">
                                                    <%@include file="jspf/disbursement-info.jspf" %>
                                                </div>
                                            </div>
                                        </div>

                                        <!--ARBO REPAYMENT-->
                                        <div class="panel box box-primary">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                                        ARBO Repayment Information
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseThree" class="panel-collapse collapse">
                                                <div class="box-body">
                                                    <%@include file="jspf/arbo-repayment-info.jspf" %>
                                                </div>
                                            </div>
                                        </div> 

                                        <!--ARB REPAYMENT-->  
                                        <div class="panel box box-primary">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseSeven">
                                                        ARB Repayment Information
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseSeven" class="panel-collapse collapse">
                                                <div class="box-body">
                                                    <%@include file="jspf/arb-repayment-info.jspf" %>
                                                </div>
                                            </div>
                                        </div>

                                        <!--PAST DUE REPAYMENT-->
                                        <div class="panel box box-primary">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                                        Past Due Information
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseOne" class="panel-collapse collapse">
                                                <div class="box-body">
                                                    <%@include file="jspf/pastdue-info.jspf" %>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                    
                </section>
                <!-- /.col -->
            </div>

            <!-- /.content -->

        </div>

        <%@include file="jspf/footer.jspf" %>
        <script type="text/javascript">
            $(document).ready(function () {
                alert("WEW!");
                var arboInterestRate = <%out.print(r.getLoanReason().getLoanTerm().getArboInterestRate());%>
                alert(arboInterestRate);
                $('#releaseAmount').on('input', function () {
                    var val = this.value * arboInterestRate;
                    $('#releaseOSBalance').val(val);
                });
                
                var interestRate = <%out.print(r.getLoanReason().getLoanTerm().getArbInterestRate());%>

                $('#disbursementAmount').on('input', function () {
                    var val = this.value * interestRate;
                    $('#disbursementOSBalance').val(val);
                });
                
            });

            function chg() {
                var arbID = document.getElementById('arbRepaymentSelect').value;
                
                alert(arbID);

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState === 4 && xhttp.status === 200) {
                        document.getElementById('arbRepaymentDetails').innerHTML = xhttp.responseText;
                    }
                };
                xhttp.open("GET", "RefreshARBRepayment?arbID=" + arbID + "&requestID=" + r.getRequestID()), true);
                xhttp.send();
            }
        </script>

    </body>
</html>
