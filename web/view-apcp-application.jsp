<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/jspf/header.jspf"%>

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


    <!DOCTYPE html>




    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pfo-apcp-sidebar.jspf" %>

            <%
//            APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();
//            ARBODAO arboDAO = new ARBODAO();
            
        int reqID = (Integer) request.getAttribute("requestID");
        APCPRequest req = apcpRequestDAO.getRequestByID(reqID);
        ARBO arbo = arboDAO.getARBOByID(req.getArboID());
        UserDAO userDAO = new UserDAO();
            %>                                        
            <% User u1 = new User(); %>
            <% User u2 = new User(); %>
            <% User u3 = new User(); %>
            <% User u4 = new User(); %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong><i class="fa fa-money"></i> APCP Request for New Accessing Conduits</strong> 
                        <small><%out.print((String) session.getAttribute("provOfficeDesc") + ", " + (String) session.getAttribute("regOfficeDesc"));%></small>
                    </h1>

                </section>

                <!-- Main content -->
                <section class="content">

                    <%if(request.getAttribute("errMessage") != null){%>
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-ban"></i> <%out.print((String)request.getAttribute("errMessage"));%></h4>
                    </div>
                    <%}%>
                    <div class="box box-solid">
                        <div class="box-header with-border">
                            <h3 class="box-title">Collapsible Accordion</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div class="box-group" id="accordion">
                                <!-- we are adding the .panel class so bootstrap.js collapse plugin detects it -->
                                <div class="panel box box-info">
                                    <div class="box-header with-border">
                                        <h4 class="box-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                                APCP Request Information
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapseTwo" class="panel-collapse collapse">
                                        <div class="box-body">
                                            <%@include file="/jspf/apcp-request-info.jspf" %>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel box box-primary">
                                    <div class="box-header with-border">
                                        <h4 class="box-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                                ARBO Information
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapseThree" class="panel-collapse collapse">
                                        <div class="box-body">
                                            <%@include file="/jspf/arbo-information.jspf" %>
                                        </div>
                                    </div>
                                </div>
                                        
                                <%
                                if (req.getRequestStatus() == 1){%>
                                <%@include file="jspf/clear-apcp-application.jspf" %>
                                <%}else if(req.getRequestStatus() == 2){%>
                                <%@include file="jspf/endorse-apcp-application.jspf" %>
                                <%}else if(req.getRequestStatus() == 3){%>
                                <%@include file="jspf/approve-apcp-application.jspf" %>
                                <%}
                                %>

                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.box -->

                    </div>
                    </div>

                </section>
                <!-- /.content -->

            </div>

        </div>
        <!-- ./wrapper -->

        <%@include file="jspf/footer.jspf"%>  
        <script type="text/javascript">


        </script>

    </body>


</html>
