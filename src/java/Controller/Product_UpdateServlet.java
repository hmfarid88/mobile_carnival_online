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
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hmfar
 */
public class Product_UpdateServlet extends HttpServlet {

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
       int sino1=Integer.parseInt(request.getParameter("sino1"));
       int sino2=Integer.parseInt(request.getParameter("sino2"));
       String brand = request.getParameter("brand");
       String model = request.getParameter("model");
       String color = request.getParameter("color");
       String imei = request.getParameter("imei");
       String vendor = request.getParameter("vendor");
       String date = request.getParameter("date");
       Float pprice =Float.parseFloat(request.getParameter("pprice")) ;
       Float sprice =Float.parseFloat(request.getParameter("sprice")) ;
       

            Connection con = null;
            PreparedStatement ps = null;
            
            try {
                con = Ssymphonyy.getConnection();
                String costrate="update stock set BRAND=?, MODEL=?, COLOR=?, IMEI=?, PURCHASE_PRICE=?, SELL_PRICE=?, VENDOR=?, DATE=? where SI_NO=?";
                ps = con.prepareStatement(costrate);
                ps.setString(1, brand);
                ps.setString(2, model);
                ps.setString(3, color);
                ps.setString(4, imei);
                ps.setFloat(5, pprice);
                ps.setFloat(6, sprice);
                ps.setString(7, vendor);
                ps.setString(8, date);
                ps.setInt(9, sino1);
                int x = ps.executeUpdate();
                if(x>0){
                    String query = "update vendor_stock set  PRODUCT=?, PRODUCT_ID=?, PURCHASE_PRICE=?, VENDOR=?, DATE=? where  SI_NO=? ";
                ps = con.prepareStatement(query);
                ps.setString(1, model);
                ps.setString(2, imei);
                ps.setFloat(3, pprice);
                ps.setString(4, vendor);
                ps.setString(5, date);
                ps.setInt(6, sino2);
                ps.executeUpdate();
                response.sendRedirect("admin_portal.jsp");
               
                } else {
                    out.println("Product is not Updated");
                }
                } catch (SQLException ex) {

            }finally{
  
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
