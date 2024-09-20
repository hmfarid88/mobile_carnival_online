<%-- 
    Document   : company_info
    Created on : Dec 26, 2017, 11:54:06 AM
    Author     : user
--%>

<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="DB.Ssymphonyy"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js" lang="">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Mobile Store</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/font-awesome.min.css">
        <link rel="stylesheet" href="css/style.css">
        <link rel="icon" type="image/png" href="img/favicon.ico">
    </head>
    <body>
        <%
            if ((session.getAttribute("admin") == null) || (session.getAttribute("admin") == "")) {
        %>
    <br> <center><h3> You are not logged in</h3><br/>
        <a href="index.jsp"><button class="btn btn-success">Please Login</button></a></center>
        <%} else {
        %>
        <div id="main" class="container">
            <nav style="margin: 0 auto" class="navbar navbar-inverse">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar"> 
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse" id="myNavbar">
                    <ul class="nav navbar-nav">
                      
                        <li style="margin-left: 10px"> <a href="admin_portal.jsp"><span class="fa fa-home"></span> Home</a></li>
                    </ul>
                </div>
            </nav>

            <center>
                <div class="panel panel-primary" style="width: 100%; background-color: #CDCBCB">

                    <div class="panel-body">
                        <h3 class="text-primary" ><strong>Product Info</strong></h3>
                        <div class="col-sm-12">
                            <form method="POST" action="Acce_UpdateServlet">

                                <table border="1px" width="70%" class="table-responsive table-condensed">
                                    <%
                                        String imei=request.getParameter("imei");
                                        Connection con = null;
                                        PreparedStatement ps = null;
                                        ResultSet rs=null;
                                        ResultSet rs1=null;

                                        try {
                                            con = Ssymphonyy.getConnection();
                                            String query = "select * from accessoriesstock where PRODUCT_ID=? ";
                                            ps=con.prepareStatement(query);
                                            ps.setString(1, imei);
                                            rs = ps.executeQuery();
                                            while (rs.next()) {
                                                String query1="select SI_NO from vendor_stock where PRODUCT_ID=?";
                                                ps=con.prepareStatement(query1);
                                            ps.setString(1, imei);
                                            rs1 = ps.executeQuery();
                                            rs1.next();
                                            request.setAttribute("vimei", rs1.getInt(1));
                                    %>
                                    <tr>
                                        <td>Product Name :</td>
                                    <input type="hidden" name="sino1" value="<%= rs.getInt("SI_NO") %>" >
                                      <input type="hidden" name="sino2" value="${vimei}" >
                                        <td><input type="text" class="form-control" name="name" value="<%= rs.getString("PRODUCT_NAME")%>" required="" ></td>
                                    </tr>
                                    <tr>
                                        <td>Model :</td>
                                        <td><input type="text" class="form-control" name="model" value="<%= rs.getString("MODEL")%> " required="" ></td>
                                    </tr>
                                    <tr>
                                        <td>Product Id :</td>
                                        <td><input type="text" class="form-control" name="imei" value="<%= rs.getString("PRODUCT_ID")%> " required="" ></td>
                                    </tr>
                                    <tr>
                                        <td>Purchase Price :</td>
                                        <td><input type="text" class="form-control" name="pprice" value="<%= rs.getString("COST_PRICE")%> " required="" ></td>
                                    </tr>
                                    <tr>
                                        <td>Sell Price :</td>
                                        <td><input type="text" class="form-control" name="sprice" value="<%= rs.getString("SELL_PRICE")%> " required="" ></td>
                                    </tr>
                                    <tr>
                                        <td>Vendor :</td>
                                        <td><input type="text" class="form-control" name="vendor" value="<%= rs.getString("VENDOR")%> " required="" ></td>
                                    </tr>
                                    <tr>
                                        <td>Date :</td>
                                        <td><input type="text" class="form-control" name="date" value="<%= rs.getString("DATE")%> " required="" ></td>
                                    </tr>
                                    <%
                                            }
                                        } catch (Exception ex) {
                                    
                                        }finally {
try { if (rs1 != null) rs1.close(); rs1=null; } catch (SQLException ex2) { }   
try { if (rs != null) rs.close(); rs=null; } catch (SQLException ex2) { }
   try { if (ps != null) ps.close(); ps=null; } catch (SQLException ex2) { }
   try { if (con != null) con.close(); con=null; } catch (SQLException ex2) { }
}
                                    %>
                                    <tr>
                                        <td></td>
                                        <td>
                                            <input type="submit" name="" value="Update" class="btn btn-success btn-sm" >
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>

                    </div>

                </div>
              
            </center>
        </div>
      <%@include file = "footerpage.jsp" %> 
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery-3.1.1.min.js"></script>
        
              
        <% } %>
    </body>
</html>
