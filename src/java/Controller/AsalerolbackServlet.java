/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DB.Ssymphonyy;
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
 * @author Polash
 */
public class AsalerolbackServlet extends HttpServlet {

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

        String rollback = request.getParameter("pid");
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs=null;

        try {
            con = Ssymphonyy.getConnection();
            String cid="select count(*) as data from paymentinfo where CUSTOMER_ID IN (select CUSTOMER_ID from accessor_sale where PRODUCT_ID='"+ rollback +"')";
            ps = con.prepareStatement(cid);
            rs=ps.executeQuery();
            rs.next();
            int x=rs.getInt("data");
            if(x>0){
             out.println("<h3>Sorry! You are not allowed to delete this sale</h3>");
            }else{
            String query1 = "insert into accessoriesstock (PRODUCT_NAME, MODEL, PRODUCT_ID, COST_PRICE, SELL_PRICE,"
                    + " VENDOR, DATE) select PRODUCT_NAME, MODEL,PRODUCT_ID, COST_PRICE, STOCK_RATE,"
                    + " VENDOR, STOCK_DATE from accessor_sale where PRODUCT_ID=? ";
            ps = con.prepareStatement(query1);
            ps.setString(1, rollback);
            int b = ps.executeUpdate();
            if (b > 0) {
                String query2 = "delete from accessor_sale  where PRODUCT_ID=? ";
                ps = con.prepareStatement(query2);
                ps.setString(1, rollback);
                ps.executeUpdate();
                response.sendRedirect("symmobilesell.jsp");
            }}
        } catch (SQLException ex) {
        }finally {
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