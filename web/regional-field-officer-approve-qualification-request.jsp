<%-- 
    Document   : provincial-field-officer-add-arbo-qualified
    Created on : Feb 20, 2018, 12:44:25 PM
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
            <%@include file="jspf/regional-field-officer-sidebar.jspf" %>

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
                    <%if (request.getAttribute("errMessage") != null) {%>
                    <p class="text text-center text-danger"><%=request.getAttribute("errMessage")%></p>
                    <%}%>
                    <%if (request.getAttribute("success") != null) {%>
                    <p class="text text-center text-success"><%=request.getAttribute("success")%></p>
                    <%}%>
                    <div class='row'>
                        <div class='col-xs-6'>
                            <div class='box'>
                                <div class='box-header with-border'>
                                    <h3 class="box-title">Upload ARBO List</h3>
                                </div>
                                <form role='form' method="post" action="ImportAPCPARBO">
                                    <div class='box-body'>

                                        <div class='box-body'>
                                            <div class="row">
                                                <div class="col-xs-3">
                                                    <div class="form-group">
                                                        <label for="fileExcel">Import Qualified ARBO</label>
                                                        <input type='file' id='fileExcel' name="file">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="box-footer">
                                            <button type="submit" name="import" class="btn btn-primary pull-right">Submit</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">

                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Create ARBO</strong></h3>
                                </div>
                                <!--                                 /.box-header -->
                                <form role="form" method="post" action="AddAPCPARBO">
                                    <div class="box-body">             

                                        <div class="row">
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <div class="input-group" id="arboName">
                                                        <input type="text" class="form-control" disabled required>
                                                        <span class="input-group-btn">
                                                            <button type="button" class="btn btn-success" data-toggle="modal" data-target="#modal-default">Select ARBO</button>
                                                        </span>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>


                                        <div class="row">
                                            <div class="col-xs-3">
                                                <div class="form-group">
                                                    <label for="">Total Amount Approved</label>
                                                    <input type="text" name="totalAmountApproved" class="form-control" id='' placeholder="">                                                </div>
                                            </div>
                                            <div class="col-xs-3">
                                                <div class="form-group">
                                                    <label for="">Cumulative Release</label>
                                                    <input type="text" name="cumulativeRelease" class="form-control" id='' placeholder="">
                                                </div>
                                            </div>
                                            <div class="col-xs-3">
                                                <div class="form-group">
                                                    <label for="">Current Year Release</label>
                                                    <input type="date" name="currentYearRelease" class="form-control" id='' placeholder="">
                                                </div>
                                            </div>
                                            <div class="col-xs-3">
                                                <div class="form-group">
                                                    <label for="">Date Released</label>
                                                    <input type="date" name="dateReleased" class="form-control" id='' placeholder="">                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-3">
                                                <div class="form-group">
                                                    <label for="">OS Balance</label>
                                                    <input type="text" name="OSBalance" class="form-control" id='' placeholder="">
                                                </div>
                                            </div>
                                            <div class="col-xs-3">
                                                <div class="form-group">
                                                    <label for="">Past Due Amount</label>
                                                    <input type="text" name="pastDueAmount" class="form-control" id='' placeholder="">
                                                </div>
                                            </div>
                                            <div class="col-xs-3">
                                                <div class="form-group">
                                                    <label for="">Due Date</label>
                                                    <input type="date" name="dueDate" class="form-control" id='' placeholder="">                                                </div>
                                            </div>
                                            <div class="col-xs-3">
                                                <div class="form-group" id="">
                                                    <label for="">Reason for Past Due</label>
                                                    <select class="form-control" id="reasonPastDue" name="reasonPastDue" style="width: 100%;"  required>
                                                        <option value="1">Withdrawal</option>
                                                        <option value="2">Drought</option>
                                                        <option value="3">Typhoon</option>
                                                        <option value="4">Earthquake</option>
                                                        <option value="5">Volcanic Eruption</option>
                                                        <option value="6">War</option>
                                                        <option value="7">Pest Infestation</option>
                                                        <option value="8">Lack of Income</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>



                                    </div>
                                    <!--                                     /.box-body -->

                                    <div class="box-footer">
                                        <button type="submit" name="manual" class="btn btn-primary pull-right">Submit</button>
                                    </div>
                                    <div class="modal fade" id="modal-default">
                                        <div class="modal-dialog modal-lg">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span></button>
                                                    <h4 class="modal-title"></h4>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div class="col-xs-12">
                                                            <div class="box">
                                                                <div class="box-header with-border">
                                                                    <h3 class="box-title">Agrarian Reform Beneficiary Organizations (ARBO)</h3>
                                                                    <div class="btn-group pull-right">
                                                                        <a href="provincial-field-officer-add-arbo.jsp" class="btn btn-primary" ><i class="fa fa-user-plus "> </i> Add ARBO </a>                                                                                   
                                                                    </div>                         
                                                                </div>
                                                                <!-- /.box-header -->
                                                                <div class="box-body">             
                                                                    <table id="example1" class="table table-bordered table-striped">
                                                                        <thead>
                                                                            <tr>
                                                                                <th></th>
                                                                                <th>ARBO Name</th>
                                                                                <th>Leader</th>
                                                                                <th>No. of Members</th>
                                                                            </tr>
                                                                        </thead>

                                                                        <tbody>
                                                                        <form id="modalForm">
                                                                            <%for(ARBO arbo : nonQualifiedARBOs){%>
                                                                            <tr>
                                                                                <td><input name="arboID" value="<%out.print(arbo.getArboID());%>" type="radio"/></td>
                                                                                <td><%out.print(" "+arbo.getArboName());%></td>
                                                                                <td>Internet Explorer 4.0</td>
                                                                                <td>Win 95+</td>
                                                                            </tr>
                                                                            <%}%>
                                                                        </form>
                                                                        </tbody>

                                                                    </table>
                                                                </div>
                                                                <!-- /.box-body -->
                                                            </div>
                                                            <!-- /.box -->
                                                        </div>
                                                        <!-- /.col -->
                                                    </div>

                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                                                    <button type="button" onclick="chg()" class="btn btn-primary" data-dismiss="modal">Select</button>
                                                </div>
                                            </div>
                                            <!-- /.modal-content -->
                                        </div>
                                        <!-- /.modal-dialog -->
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

            
        </div>

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
