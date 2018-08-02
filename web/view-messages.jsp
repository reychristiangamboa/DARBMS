<!DOCTYPE html>
<html>
    <head>
        <%@include file="jspf/header.jspf" %>
        <style>
            li .disabled{
                pointer-events: none;
                opacity: 0.6;
            }
        </style>

    </head>
    <body class="hold-transition skin-green sidebar-mini">
        <div class="wrapper">

            <%if ((Integer) session.getAttribute("userType") == 1) {%>
            <%@include file="jspf/admin-navbar.jspf" %>
            <%@include file="jspf/admin-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 2) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pp-apcp-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 3) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 4) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/regional-field-officer-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 5) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/central-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 6) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pfo-apcp-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 7) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pfo-capdev-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 8) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pp-capdev-sidebar.jspf" %>
            <%}%>
            <%
                MessageDAO mDAO = new MessageDAO();
                UserDAO uDAO = new UserDAO();
                
                ArrayList<Message> mList = new ArrayList();
                ArrayList<User> uList = new ArrayList();
                
                uList = uDAO.getAllUsers();
                mList = mDAO.retrieveMyMessages((Integer) session.getAttribute("userID"));
            %>

            <div class="content-wrapper">

                <section class="content-header">
                    <h1>
                        <strong><i class="fa fa-envelope-o"></i> Messages</strong> 
                    </h1>
                </section>

                <section class="content">

                    <%if(request.getAttribute("success") != null){%>
                    <div class="alert alert-success alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-check"></i> <%out.print((String)request.getAttribute("success"));%></h4>
                    </div>
                    <%}else if(request.getAttribute("errMessage") != null){%>
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-ban"></i> <%out.print((String)request.getAttribute("errMessage"));%></h4>
                    </div>
                    <%}%>

                    <div class="row">
                        <div class="col-xs-3 pull-right">
                            <button class="btn btn-primary pull-right" data-target="#messageModal" data-toggle="modal">Create Message</button>    
                        </div>

                    </div>
                    <br>
                    <div class="row">
                        <div class="col-xs-12">  
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Messages</h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>  
                                </div>
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <table class="table table-bordered table-striped modTable">
                                                <thead>
                                                    <tr>
                                                        <th>Sent By</th>
                                                        <th>Message</th>
                                                        <th>Sent Date</th>
                                                        <th>Sent Time</th>
                                                    </tr>
                                                </thead>

                                                <tbody>
                                                    <%
                                                        for (Message m : mList) {
                                                            User u = uDAO.searchUser(m.getSentBy());
                                                    %>
                                                    <tr>
                                                        <td><%out.print(u.getFullName());%></td>
                                                        <td><%out.print(m.getBody());%></td>
                                                        <td><%out.print(f.format(m.getDateSent()));%></td>
                                                        <td><%out.print(m.getTimeSent());%></td>
                                                    </tr>
                                                    <% }%>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="modal fade" id="messageModal">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">×</span>
                                    </button>
                                    <h4 class="modal-title">Create Message</h4>
                                </div>
                                <form method="post">
                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="form-group">
                                                    To: 
                                                    <select name="sendTo" id="" class="form-control">
                                                        <%for(User u : uList){%>
                                                        <option value="<%out.print(u.getUserID());%>"><%out.print(u.getFullName() + " - " + u.getUserTypeDesc());%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="form-group">
                                                    <label for="">Message</label>
                                                    <textarea name="message" id="" cols="3" rows="2" class="form-control" required></textarea>
                                                </div>
                                            </div>
                                        </div>    

                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" onclick="form.action = 'SendMessage'" class="btn btn-success pull-right">Send</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>

        <!--Import jQuery before materialize.js-->
        <%@include file="jspf/footer.jspf" %>

    </body>
</html>
