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
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            

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

                    <%
                        ArrayList<APCPRequest> requestedRequests2 = (ArrayList)request.getAttribute("requested");
                    %>
                    
                    
                    <div class="row">
                        <div class="col-xs-6">
                            <div class="box">
                                <div class="box-header"><h3 class="box-title">Filter</h3></div>
                                <form method="post" role="form">
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-2">
                                                <div class="form-group">
                                                    <label for="actName">Select All</label>
                                                    <input type="checkbox" id="filterBy" name="selectAll" value="Yes">
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="actName">Cities / Municipalities</label>
                                                    <select name="cities[]" id="cities" class="form-control select2" multiple="multiple">
                                                        <%for(CityMun city : cityMunList){%>
                                                        <option value="<%=city.getCityMunCode()%>"><%out.print(city.getCityMunDesc());%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>


                                    </div>
                                    <div class="box-footer">
                                        <button type="submit" onclick="form.action = 'FilterCAPDEVRequestsCCP'" class="btn btn-success pull-right">Filter</button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                    

                    
                    <div class="row text-center">
                        <div class="col-xs-12">
                            <a name="all" href="#">Select All <i class="fa fa-chevron-down"></i></a>
                        </div>
                    </div>
                    
                    <!--REQUESTED-->
                    <div class="row" id="1">
                        <div class="col-xs-12" >
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Requested</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="example1" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Loan Reason</th>
                                                <th>Loan Amount</th>
                                                <th>Land Area</th>
                                                <th>Date Requested</th>
                                                <th>Remarks</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <%
                                                for(APCPRequest r : requestedRequests2){
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>

                                                <td><a href="CreateCAPDEVProposal?id=<%out.print(r.getRequestID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(r.getLoanReason());%></td>
                                                <td><%out.print(currency.format(r.getLoanAmount()));%></td>
                                                <td><%out.print(r.getHectares() + " hectares");%></td>
                                                <td><%out.print(f.format(r.getDateRequested()));%></td>
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
                </section>
                <!-- /.content -->

            </div>
            <!-- /.content-wrapper -->

        </div>
        <%@include file="jspf/footer.jspf" %>
        
        <script>
            $(document).ready(function(){
                var update_pizza = function () {
                    if ($("#filterBy").is(":checked")) {

                        $('#cities').prop('disabled', 'disabled');
                        $('#provinces').prop('disabled', 'disabled');

                    } else {
                        $('#cities').prop('disabled', false);
                        $('#provinces').prop('disabled', false);
                        $('#cities').val() = '';
                    }
                };

                $(update_pizza);
                $("#filterBy").change(update_pizza);
            });
        </script>
        
    </body>
</html>
