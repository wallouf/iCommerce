package com.wallouf.icommerce.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListerCommande
 */
@WebServlet( "/ListerCommande" )
public class ListerCommande extends HttpServlet {
    private static final long  serialVersionUID = 1L;
    public static final String VUE              = "/WEB-INF/listerCommande.jsp";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListerCommande() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {
        // TODO Auto-generated method stub
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

}
