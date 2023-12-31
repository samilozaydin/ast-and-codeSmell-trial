package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.nio.file.Paths;

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

		try {
			String isbn = request.getParameter("isbn");
			String nome = request.getParameter("nome");
			String descrizione = request.getParameter("descrizione");
			String immagine = request.getParameter("immagine");
			double prezzo = Double.parseDouble(request.getParameter("prezzo"));
			int quantita = Integer.parseInt(request.getParameter("quantita"));
			String genere = request.getParameter("genere");
			String categoria = request.getParameter("categoria");

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
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}