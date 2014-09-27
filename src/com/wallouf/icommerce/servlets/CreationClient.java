package com.wallouf.icommerce.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wallouf.icommerce.beans.Client;

/**
 * Servlet implementation class CreationClient
 */
@WebServlet("/CreationClient")
public class CreationClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreationClient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/**
		 * Verification presence informations
		 */
		if(request.getParameter("nomClient") == null || request.getParameter("nomClient").isEmpty() || request.getParameter("adresseClient") == null
				|| request.getParameter("adresseClient").isEmpty() || request.getParameter("telephoneClient") == null || request.getParameter("telephoneClient").isEmpty()){
			this.getServletContext().getRequestDispatcher("/WEB-INF/creerClient.jsp").forward(request, response);
		}else{
			Client nouveauClient = new Client((String) request.getParameter("nomClient"), (String) request.getParameter("prenomClient"), (String) request.getParameter("adresseClient"), (String) request.getParameter("emailClient"), (String) request.getParameter("telephoneClient"));
			request.setAttribute("client", nouveauClient);
			this.getServletContext().getRequestDispatcher("/WEB-INF/afficherClient.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
