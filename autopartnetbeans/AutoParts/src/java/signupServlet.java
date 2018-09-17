/*
/*****************************
* Hayden Kowalchuk - 900450331
* CIST 2372-60273
* Lab X: XXXXXXXXXXXXX	
* Description of file and lab.
* Copyright (C) 2018 Hayden Kowalchuk
***************************** */

import autopartstore.Customer;
import autopartstore.CustomerDAOImpl;
import autopartstore.db.ConnectionManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Hayden Kowalchuk
 */
public class signupServlet extends HttpServlet {

    protected void finishRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/newsignup.jsp").forward(request, response);
    }

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
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private Statement querySmt = null;
    private ResultSet result = null;

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

        boolean success = false;

        if (request.getParameter("action").equals("register")) {

            String name = request.getParameter("name").trim();
            String email = request.getParameter("email").trim();
            String username = request.getParameter("username").trim();
            String password = request.getParameter("password").trim();
            String passwordConfirm = request.getParameter("confirm").trim();
            String address = request.getParameter("address").trim();
            Customer customer = null;
            CustomerDAOImpl customerDAO = new CustomerDAOImpl(ConnectionManager.init(this.getServletContext()));

            // Database operations using JDBC
            try {
                //ensure we have a connection to the DB
                Connection temp = ConnectionManager.init(this.getServletContext());

                // Select user from database to check user login id and password
                querySmt = temp.createStatement();
                result = querySmt.executeQuery("select * from Customers where (Username = '" + username + "' OR EMail = '" + email + "');");
                int count = 0;
                while (result.next()) {
                    count++;
                }
                if (!password.equals(passwordConfirm)) {
                    //ERROR!
                    request.setAttribute("displayAlert", true);
                    request.setAttribute("alertType", "alert-danger");
                    request.setAttribute("alertMessage", "Passwords do not match!");
                    finishRequest(request, response);
                    return;
                }
                if (count > 0) {
                    //ERROR!
                    request.setAttribute("displayAlert", true);
                    request.setAttribute("alertType", "alert-danger");
                    request.setAttribute("alertMessage", "Email already in use!");
                    finishRequest(request, response);
                    return;
                }
                customer = new Customer(0, name, address, email, "", username, password);
                success = customerDAO.insertCustomer(customer);
                request.setAttribute("success", success);
                if (success) {
                    request.setAttribute("displayAlert", true);
                    request.setAttribute("alertType", "alert-success");
                    request.setAttribute("alertMessage", "Account succesfully created!");
                } else {
                    request.setAttribute("displayAlert", true);
                    request.setAttribute("alertType", "alert-danger");
                    request.setAttribute("alertMessage", "Some error occurred somewhere! uh oh!");
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            } finally {
                try {
                    result.close();
                    querySmt.close();
                } catch (NullPointerException e) {
                    {
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        finishRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Provides Singup services and error checking";
    }// </editor-fold>

}
