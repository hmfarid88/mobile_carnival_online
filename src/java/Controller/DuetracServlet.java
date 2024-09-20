/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DB.Ssymphonyy;
import Model.CashModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Acer
 */
public class DuetracServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String name=request.getParameter("tname");
        String ref=request.getParameter("ref");
        String propname=request.getParameter("duename");
        String paydate=request.getParameter("paydate");
        Float amount=Float.parseFloat(request.getParameter("amount")) ;
    
       Connection con = null;
       PreparedStatement ps=null;
       ResultSet rs=null;  
       ResultSet rs1=null;
       ResultSet rs2=null;
        try{
            con = Ssymphonyy.getConnection();
            if(name.equals("Payment")){
            String nbalance="select AMOUNT from netbalance order by SI_NO DESC limit 1";
                    ps = con.prepareStatement(nbalance);
                    rs=ps.executeQuery();
                    rs.next();
                    Long lbalance=rs.getLong(1);
            if(lbalance<amount){
              out.println("<center><br><h2>Sorry, Insufficient Balance !</h2></center>");  
            }else{
                String query="insert into due_transaction(DUE_NAME, REFERENCE, PAYMENT, PAY_DATE, DATE) values(?,?,?,?, CURDATE())";
            ps = con.prepareStatement(query);
            ps.setString(1, propname);
            ps.setString(2, ref);
            ps.setDouble(3, amount);
            ps.setString(4, paydate);
            int a = ps.executeUpdate();
             if (a > 0) {
            String credit="insert into creditbalance(CREDIT_NAME, AMOUNT, DATE) values(?,?, CURDATE())";
             ps = con.prepareStatement(credit);
             ps.setString(1, "Due Payment To"+" "+propname);
             ps.setFloat(2, amount);
             ps.executeUpdate();
                 String propay="select sum(PAYMENT), sum(RECEIVE) from due_transaction where DUE_NAME=?";
            ps = con.prepareStatement(propay);
            ps.setString(1, propname);
            rs1=ps.executeQuery();
            rs1.next();
            Long totalpay=rs1.getLong(1);
            Long totalrecv=rs1.getLong(2);
            Long bl=totalpay-totalrecv;
            String maxsi="select MAX(SI_NO) from due_transaction where DUE_NAME=?";
            ps = con.prepareStatement(maxsi);
            ps.setString(1, propname);
            rs2=ps.executeQuery();
            rs2.next();
            int maxsino=rs2.getInt(1);
            String update="update due_transaction set BALANCE=? where SI_NO=?";
            ps = con.prepareStatement(update);
            ps.setLong(1, bl);
            ps.setInt(2, maxsino);
            ps.executeUpdate();
                response.sendRedirect("cash_book.jsp");
            } else {
                out.println("Sorry! Entry is not Success");
            } }
        }else{
                String query = "insert into due_transaction(DUE_NAME, REFERENCE, RECEIVE, PAY_DATE, DATE) values(?,?,?,?, CURDATE())";
                ps = con.prepareStatement(query);
                ps.setString(1, propname);
                ps.setString(2, ref);
                ps.setDouble(3, amount);
                ps.setString(4, paydate);
                int a = ps.executeUpdate();
                if (a > 0) {
            String debit="insert into debitbalance(DEBIT_NAME, AMOUNT, DATE) values(?,?, CURDATE())";
            ps = con.prepareStatement(debit);
             ps.setString(1, "Due Collection From"+" "+propname);
             ps.setFloat(2, amount);
             ps.executeUpdate();
            String propay="select sum(PAYMENT), sum(RECEIVE) from due_transaction where DUE_NAME=?";
            ps = con.prepareStatement(propay);
            ps.setString(1, propname);
            rs1=ps.executeQuery();
            rs1.next();
            Long totalpay=rs1.getLong(1);
            Long totalrecv=rs1.getLong(2);
            Long bl=totalpay-totalrecv;
            String maxsi="select MAX(SI_NO) from due_transaction where DUE_NAME=?";
            ps = con.prepareStatement(maxsi);
            ps.setString(1, propname);
            rs2=ps.executeQuery();
            rs2.next();
            int maxsino=rs2.getInt(1);
            String update="update due_transaction set BALANCE=? where SI_NO=?";
            ps = con.prepareStatement(update);
            ps.setLong(1, bl);
            ps.setInt(2, maxsino);
            ps.executeUpdate();
                    response.sendRedirect("cash_book.jsp");
                } else {
                    out.println("Sorry! Entry is not Success");
                }
            }
                }
        catch(Exception ex){
            
        }finally {
   try { if (rs2 != null) rs2.close(); rs2=null; } catch (SQLException ex2) { }
   try { if (rs1 != null) rs1.close(); rs1=null; } catch (SQLException ex2) { }
   try { if (rs != null) rs.close(); rs=null; } catch (SQLException ex2) { }
   try { if (ps != null) ps.close(); ps=null; } catch (SQLException ex2) { }
   try { if (con != null) con.close(); con=null; } catch (SQLException ex2) { }
}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        Connection con = null;
        PreparedStatement ps = null;
        ProfitAnalyse pf = new ProfitAnalyse();
        CashModel cam = pf.NetBalance();
        Float netblance = cam.getNetbalance();

        try {
            con = Ssymphonyy.getConnection();
            String query = "update netbalance set AMOUNT=? where  DATE=CURDATE() ";
            ps = con.prepareStatement(query);
            ps.setFloat(1, netblance);
            int b = ps.executeUpdate();
            if (b > 0) {

            } else {
                String query1 = "insert into netbalance (AMOUNT, DATE) values (?,CURDATE())";
                ps = con.prepareStatement(query1);
                ps.setFloat(1, netblance);
                ps.executeUpdate();

            }
                                } catch (Exception ex) {
                                      }finally {
   try { if (ps != null) ps.close(); ps=null; } catch (SQLException ex2) { }
   try { if (con != null) con.close(); con=null; } catch (SQLException ex2) { }
}
        
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}