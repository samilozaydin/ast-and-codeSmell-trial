package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddProductServlet")
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection connection = null;
		RequestDispatcher dispatcher = null;

		String isbn = (String) request.getParameter("isbn");
		String nome = (String) request.getParameter("nome");
		String descrizione = (String) request.getParameter("descrizione");
		String immagine = (String) request.getParameter("immagine");
		double prezzo = Double.parseDouble(request.getParameter("prezzo"));
		int quantita = Integer.parseInt(request.getParameter("quantita"));
		String genere = (String) request.getParameter("genere");
		String categoria = (String) request.getParameter("categoria");
		System.out.println(genere);

		try {
			String query = "INSERT INTO prodotti(isbn, nome, descrizione, immagine_prod, prezzo, quantita, genere_nome, categoria_nome) values(?,?,?,?, ?, ?, ?, ?)";
			connection = DbManager.getConnection();
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, isbn);
			ps.setString(2, nome);
			ps.setString(3, descrizione);
			ps.setString(4, immagine);
			ps.setDouble(5, prezzo);
			ps.setInt(6, quantita);
			ps.setString(7, genere);
			ps.setString(8, categoria);
			ps.executeUpdate();

			dispatcher = request.getRequestDispatcher("profilo.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection == null)
					return;
				else
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}