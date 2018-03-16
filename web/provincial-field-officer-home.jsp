<%-- 
    Document   : provincial-field-officer-home
    Created on : Mar 16, 2018, 4:45:24 PM
    Author     : Rey Christian
--%>

<!DOCTYPE html>
<html>
    <head>
        <%@include file="jspf/header.jspf"%>
        <style>
            .rate{
                color:black;
                cursor:pointer;
                width: 80px;
                margin: 0 auto;
            }
            .rate:hover{
                color:red;
            }
            .checked {
                color: orange;
            }

        </style>
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf"%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        User Profile
                    </h1>
                </section>

                <!-- Main content -->
                <section class="content">

                    <div class="row">

                        <div class="col-md-12">
                            <!-- /.col -->
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">ARB Visuals</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                                    </div>
                                </div>
                                <div class="box-body">
                                    <div class="nav-tabs-custom">
                                        <ul class="nav nav-tabs">
                                            <li class="active"><a href="#gender" data-toggle="tab">ARB Per Gender</a></li>
                                            <li><a href="#educ" data-toggle="tab">ARB Education Level</a></li>
                                            <li><a href="#citymun" data-toggle="tab">ARB Per City Municipality</a></li>
                                            <li><a href="#crop" data-toggle="tab">Crops History</a></li>
                                        </ul>

                                        <div class="tab-content">
                                            <div class="active tab-pane" id="gender">
                                                <div class="row">
                                                    <div class="col-xs-2"></div>
                                                    <div class="col-xs-8">
                                                        <div class="box-body">
                                                            <canvas id="pieCanvas"></canvas>
                                                        </div>  
                                                    </div>
                                                    <div class="col-xs-2"></div>
                                                </div>

                                            </div>
                                            <!-- /.tab-pane -->
                                            <div class="tab-pane" id="educ">
                                                <div class="box-body" id="bar">
                                                    <div class="row">
                                                        <div class="col-xs-2"></div>
                                                        <div class="col-xs-8">
                                                            <div class="chart">
                                                                <canvas id="barCanvas" style="height:230px"></canvas>
                                                            </div>
                                                        </div>
                                                        <div class="col-xs-2"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- /.tab-pane -->
                                            <div class="tab-pane" id="citymun">
                                                <div class="active tab-pane" id="gender">
                                                    <div class="box-body">
                                                        <canvas id="pieCanvas" style="height:250px"></canvas>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- /.tab-pane -->
                                            <div class="tab-pane" id="crop">
                                                <div class="box-body">
                                                    <div class="chart">
                                                        <canvas id="lineCanvas" style="height:250px"></canvas>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- /.tab-pane -->
                                        </div>
                                        <!-- /.tab-content -->
                                    </div>
                                    <!-- /.nav-tabs-custom -->

                                </div>
                            </div>

                            <!-- /.col -->
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">APCP Visuals</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                                    </div>
                                </div>
                                <div class="box-body">
                                    <div class="nav-tabs-custom">
                                        <ul class="nav nav-tabs">
                                            <li class="active"><a href="#release" data-toggle="tab">Release Line</a></li>
                                            <li><a href="#pend" data-toggle="tab">Pending Requests</a></li>
                                            <li><a href="#disbursement" data-toggle="tab">Disbursements</a></li>
                                            <li><a href="#repayment" data-toggle="tab">Repayments</a></li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="active tab-pane" id="release">
                                                <table id="example1" class="table table-bordered table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th>Loan <br>Tracking No.</th>
                                                            <th>Last Release Date</th>
                                                            <th>Progress</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td>1</td>
                                                            <td>April 1, 2011
                                                            </td>
                                                            <td>
                                                                <div class="progress">
                                                                    <div class="progress-bar progress-bar-green" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                                                                        40% Complete (success)
                                                                    </div> 
                                                                </div> 
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>2</td>
                                                            <td>June 2, 2010
                                                            </td>
                                                            <td>
                                                                <div class="progress">
                                                                    <div class="progress-bar progress-bar-green" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                                                                        <span class="sr-only">40% Complete (success)</span>
                                                                    </div>                                           
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                    <tfoot>
                                                        <tr>
                                                            <th>Loan <br>Tracking No.</th>
                                                            <th>Last Release Date</th>
                                                            <th>Progress</th>
                                                        </tr>
                                                    </tfoot>
                                                </table>   

                                            </div>
                                            <!-- /.tab-pane -->
                                            <div class="tab-pane" id="pend">
                                                <table id="example3" class="table table-bordered table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th>Request ID</th>
                                                            <th>Loan Request</th>
                                                            <th>Loan Amount</th>
                                                            <th>Status</th>
                                                            <th>Date</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td>1</td>
                                                            <td>April 1, 2011
                                                            </td>
                                                            <td>
                                                                10100000
                                                            </td>
                                                            <td>
                                                                <span class="label label-danger">RICE</span>
                                                            </td>
                                                            <td>
                                                                April 1, 2011
                                                            </td>
                                                        </tr>

                                                    </tbody>
                                                    <tfoot>
                                                        <tr>
                                                            <th>Request ID</th>
                                                            <th>Loan Request</th>
                                                            <th>Loan Amount</th>
                                                            <th>Status</th>
                                                            <th>Date</th>
                                                        </tr>
                                                    </tfoot>
                                                </table>  
                                            </div>
                                            <!-- /.tab-pane -->
                                            <div class="tab-pane" id="disbursement">
                                                <div class="box-body">
                                                    <canvas id="pieCanvas" style="height:250px"></canvas>
                                                </div>
                                            </div>
                                            <!-- /.tab-pane -->
                                            <div class="tab-pane" id="repayment" style="overflow-y: scroll; overflow-x: hidden;  max-height: 300px; ">
                                                <ul class="timeline timeline-inverse">
                                                    <!-- timeline time label -->
                                                    <li class="time-label">
                                                        <span class="bg-red">
                                                            INTERVENTION: RICE TRAINING 101
                                                        </span>
                                                    </li>
                                                    <!-- /.timeline-label -->
                                                    <!-- timeline item -->
                                                    <li>
                                                        <i class="fa fa-envelope bg-blue"></i>

                                                        <div class="timeline-item">
                                                            <span class="time"><i class="fa fa-clock-o"></i> 12:05</span>

                                                            <h3 class="timeline-header"><a href="#">Support Team</a> sent you an email</h3>

                                                            <div class="timeline-body">
                                                                Etsy doostang zoodles disqus groupon greplin oooj voxy zoodles,
                                                                weebly ning heekya handango imeem plugg dopplr jibjab, movity
                                                                jajah plickers sifteo edmodo ifttt zimbra. Babblely odeo kaboodle
                                                                quora plaxo ideeli hulu weebly balihoo...
                                                            </div>
                                                            <div class="timeline-footer">
                                                                <a class="btn btn-primary btn-xs">Read more</a>
                                                                <a class="btn btn-danger btn-xs">Delete</a>
                                                            </div>
                                                        </div>
                                                    </li>
                                                    <!-- END timeline item -->
                                                    <!-- timeline item -->
                                                    <li>
                                                        <i class="fa fa-user bg-aqua"></i>

                                                        <div class="timeline-item">
                                                            <span class="time"><i class="fa fa-clock-o"></i> 5 mins ago</span>

                                                            <h3 class="timeline-header no-border"><a href="#">Sarah Young</a> accepted your friend request
                                                            </h3>
                                                        </div>
                                                    </li>
                                                    <!-- END timeline item -->
                                                    <!-- timeline item -->
                                                    <li>
                                                        <i class="fa fa-comments bg-yellow"></i>

                                                        <div class="timeline-item">
                                                            <span class="time"><i class="fa fa-clock-o"></i> 27 mins ago</span>

                                                            <h3 class="timeline-header"><a href="#">Jay White</a> commented on your post</h3>

                                                            <div class="timeline-body">
                                                                Take me to your leader!
                                                                Switzerland is small and neutral!
                                                                We are more like Germany, ambitious and misunderstood!
                                                            </div>
                                                            <div class="timeline-footer">
                                                                <a class="btn btn-warning btn-flat btn-xs">View comment</a>
                                                            </div>
                                                        </div>
                                                    </li>
                                                    <!-- END timeline item -->
                                                    <!-- timeline time label -->
                                                    <li class="time-label">
                                                        <span class="bg-green">
                                                            3 Jan. 2014
                                                        </span>
                                                    </li>
                                                    <!-- /.timeline-label -->
                                                    <!-- timeline item -->
                                                    <li>
                                                        <i class="fa fa-camera bg-purple"></i>

                                                        <div class="timeline-item">
                                                            <span class="time"><i class="fa fa-clock-o"></i> 2 days ago</span>

                                                            <h3 class="timeline-header"><a href="#">Mina Lee</a> uploaded new photos</h3>

                                                            <div class="timeline-body">
                                                                <img src="http://placehold.it/150x100" alt="..." class="margin">
                                                                <img src="http://placehold.it/150x100" alt="..." class="margin">
                                                                <img src="http://placehold.it/150x100" alt="..." class="margin">
                                                                <img src="http://placehold.it/150x100" alt="..." class="margin">
                                                            </div>
                                                        </div>
                                                    </li>
                                                    <!-- END timeline item -->
                                                    <li>
                                                        <i class="fa fa-clock-o bg-gray"></i>
                                                    </li>
                                                </ul>
                                            </div>
                                            <!-- /.tab-pane -->
                                        </div>
                                        <!-- /.tab-content -->
                                    </div>
                                    <!-- /.nav-tabs-custom -->
                                    <div class="box-footer">
                                        <div class="row">
                                            <div class="col-sm-3 col-xs-6">
                                                <div class="description-block border-right">
                                                    <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 17%</span>
                                                    <h5 class="description-header">$35,210.43</h5>
                                                    <span class="description-text">TOTAL REVENUE</span>
                                                </div>
                                                <!-- /.description-block -->
                                            </div>
                                            <!-- /.col -->
                                            <div class="col-sm-3 col-xs-6">
                                                <div class="description-block border-right">
                                                    <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> 0%</span>
                                                    <h5 class="description-header">$10,390.90</h5>
                                                    <span class="description-text">TOTAL COST</span>
                                                </div>
                                                <!-- /.description-block -->
                                            </div>
                                            <!-- /.col -->
                                            <div class="col-sm-3 col-xs-6">
                                                <div class="description-block border-right">
                                                    <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 20%</span>
                                                    <h5 class="description-header">$24,813.53</h5>
                                                    <span class="description-text">TOTAL PROFIT</span>
                                                </div>
                                                <!-- /.description-block -->
                                            </div>
                                            <!-- /.col -->
                                            <div class="col-sm-3 col-xs-6">
                                                <div class="description-block">
                                                    <span class="description-percentage text-red"><i class="fa fa-caret-down"></i> 18%</span>
                                                    <h5 class="description-header">1200</h5>
                                                    <span class="description-text">GOAL COMPLETIONS</span>
                                                </div>
                                                <!-- /.description-block -->
                                            </div>
                                        </div>
                                        <!-- /.row -->
                                    </div>
                                </div>
                            </div>





                            <!-- /.col -->

                        </div>
                        <!-- /.col -->
                        <div class=" col-xs-6">
                            <div class="box">
                                <div class="box-header with-border" >
                                    <h3 class="box-title">APCP Requests</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                                    </div>
                                </div>
                                <div clas="row  color-palette-set">
                                    <div class="col-lg-4 col-xs-6">
                                        <!-- small box -->
                                        <div class="small-box bg-green-gradient color-palette">
                                            <div class="inner">
                                                <h3>44</h3>

                                                <p>Requested</p>
                                            </div>
                                            <div class="icon">
                                                <i class="fa fa-keyboard-o"></i>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="col-lg-4 col-xs-6">
                                        <!-- small box -->
                                        <div class="small-box bg-green-active color-palette">
                                            <div class="inner">
                                                <h3>44</h3>

                                                <p>Cleared</p>
                                            </div>
                                            <div class="icon">
                                                <i class="fa fa-check-square-o"></i>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="col-lg-4 col-xs-6">
                                        <!-- small box -->
                                        <div class="small-box bg-green">
                                            <div class="inner">
                                                <h3>44</h3>

                                                <p>Endorsed</p>
                                            </div>
                                            <div class="icon">
                                                <i class="fa fa-upload"></i>
                                            </div>

                                        </div>
                                    </div>

                                </div>
                                <div clas="row">
                                    <div class="col-lg-4 col-xs-6">
                                        <!-- small box -->
                                        <div class="small-box bg-yellow">
                                            <div class="inner">
                                                <h3>44</h3>

                                                <p>Approved</p>
                                            </div>
                                            <div class="icon">
                                                <i class="fa fa-thumbs-o-up"></i>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="col-lg-4 col-xs-6">
                                        <!-- small box -->
                                        <div class="small-box bg-red">
                                            <div class="inner">
                                                <h3>44</h3>

                                                <p>For Release</p>
                                            </div>
                                            <div class="icon">
                                                <i class="fa fa-folder-o"></i>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="col-lg-4 col-xs-6">
                                        <!-- small box -->
                                        <div class="small-box bg-green">
                                            <div class="inner">
                                                <h3>44</h3>

                                                <p>Released</p>
                                            </div>
                                            <div class="icon">
                                                <i class="fa fa-folder-open-o"></i>
                                            </div>

                                        </div>
                                    </div>

                                </div>


                                <div class="box-body" >


                                    <table id="example6" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Request ID</th>
                                                <th>Loan Request</th>
                                                <th>Loan Amount</th>
                                                <th>Status</th>
                                                <th>Date</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>1</td>
                                                <td>April 1, 2011
                                                </td>
                                                <td>
                                                    10100000
                                                </td>
                                                <td>
                                                    <span class="label label-danger">RICE</span>
                                                </td>
                                                <td>
                                                    April 1, 2011
                                                </td>
                                            </tr>

                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>Request ID</th>
                                                <th>Loan Request</th>
                                                <th>Loan Amount</th>
                                                <th>Status</th>
                                                <th>Date</th>
                                            </tr>
                                        </tfoot>
                                    </table>  

                                    <!-- /.tab-pane -->

                                </div>

                            </div>
                        </div>
                        <div class=" col-xs-6">
                            <div class="box">
                                <div class="box-header with-border" >
                                    <h3 class="box-title">CAPDEV Proposals</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                                    </div>
                                </div>
                                <div clas="row">
                                    <div class="col-lg-3 col-xs-6">
                                        <!-- small box -->
                                        <div class="small-box bg-yellow">
                                            <div class="inner">
                                                <h3>44</h3>

                                                <p>Requested</p>
                                            </div>
                                            <div class="icon" >
                                                <i class="fa fa-keyboard-o"></i>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-xs-6">
                                        <!-- small box -->
                                        <div class="small-box bg-red">
                                            <div class="inner">
                                                <h3>44</h3>

                                                <p>Pending</p>
                                            </div>
                                            <div class="icon">
                                                <i class="fa fa-hourglass-2"></i>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-xs-6">
                                        <!-- small box -->
                                        <div class="small-box bg-green">
                                            <div class="inner">
                                                <h3>44</h3>

                                                <p>Approved</p>
                                            </div>
                                            <div class="icon">
                                                <i class="fa  fa-thumbs-up"></i>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="col-lg-3 col-xs-6">
                                        <!-- small box -->
                                        <div class="small-box bg-aqua">
                                            <div class="inner">
                                                <h3>44</h3>

                                                <p>Implemented</p>
                                            </div>
                                            <div class="icon">
                                                <i class="fa fa-check-square-o"></i>
                                            </div>

                                        </div>
                                    </div>
                                </div>



                                <div class="box-body" >


                                    <table id="example5" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Request ID</th>
                                                <th>Loan Request</th>
                                                <th>Loan Amount</th>
                                                <th>Status</th>
                                                <th>Date</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>1</td>
                                                <td>April 1, 2011
                                                </td>
                                                <td>
                                                    10100000
                                                </td>
                                                <td>
                                                    <span class="label label-danger">RICE</span>
                                                </td>
                                                <td>
                                                    April 1, 2011
                                                </td>
                                            </tr>

                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>Request ID</th>
                                                <th>Loan Request</th>
                                                <th>Loan Amount</th>
                                                <th>Status</th>
                                                <th>Date</th>
                                            </tr>
                                        </tfoot>
                                    </table>  

                                    <!-- /.tab-pane -->

                                </div>

                            </div>
                        </div>
                    </div>


            </div>
            <!-- /.row -->

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            <b>Version</b> 2.4.0
        </div>
        <strong>Copyright &copy; 2014-2016 <a href="https://adminlte.io">Almsaeed Studio</a>.</strong> All rights
        reserved.
    </footer>
</div>
<!-- ./wrapper -->
<%@include file="jspf/footer.jspf" %>
<script type="text/javascript">
    $(function () {
        var ctx = $('#barCanvas').get(0).getContext('2d');
    <%
            Chart bar = new Chart();
            String json = bar.getBarChartEducation(arbListProvince);
    %>
        new Chart(ctx, <%out.print(json);%>);

        var ctx2 = $('#lineCanvas').get(0).getContext('2d');
    <%
            Chart line = new Chart();
            String json2 = line.getCropHistory(arbListProvince);
    %>
        new Chart(ctx2, <%out.print(json2);%>);

        var ctx3 = $('#pieCanvas').get(0).getContext('2d');
    <%
            Chart pie = new Chart();
            String json3 = pie.getPieChartGender(arbListProvince);
    %>
        new Chart(ctx3, <%out.print(json3);%>);
    });
</script>
</body>
</html>
