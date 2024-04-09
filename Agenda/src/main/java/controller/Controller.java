package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Contato;
import model.ContatoDao;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */
@WebServlet(name = "Controller", urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete",
		"/report" })

public class Controller extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The contato. */
	Contato contato = new Contato();
	
	/** The contato dao. */
	ContatoDao contatoDao = new ContatoDao();

	/**
	 * Instantiates a new controller.
	 */
	public Controller() {
		super();
	}

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		switch (request.getServletPath()) {
		case "/main":
			selectAllContatos(request, response);
			break;
		case "/insert":
			insertContato(request, response);
			break;
		case "/select":
			selectContato(request, response);
			break;
		case "/update":
			updateContato(request, response);
			break;
		case "/delete":
			deleteContato(request, response);
			break;
		case "/report":
			reportContato(request, response);
			break;
		default:
			// = "/Controller"
			response.sendRedirect("Index.html");
		}
	}

	/**
	 * Select all contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void selectAllContatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<Contato> arrayList = contatoDao.selectAllContato();
		request.setAttribute("contatos", arrayList);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("Agenda.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * Insert contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void insertContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		contatoDao.insertContato(contato);
		response.sendRedirect("main");
	}

	/**
	 * Select contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// recebe o id da tela agenda.jsp e detalha na tela editar.jsp
	protected void selectContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		contato.setIdcon(Integer.parseInt(request.getParameter("idcon")));
		contatoDao.selectContatoById(contato);
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("Editar.jsp");
		requestDispatcher.forward(request, response);

	}

	/**
	 * Delete contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// recebe o id da tela agenda.jsp e deleta e devolve a tela agenda.jsp
	protected void deleteContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		contato.setIdcon(Integer.parseInt(request.getParameter("idcon")));
		contatoDao.deleteContato(contato);
		response.sendRedirect("main");
	}

	// recebe o id da tela editar.jsp
	// altera
	/**
	 * Update contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// redireciona ao metodo main que lista e devolve a tela agenda.jsp
	protected void updateContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		contato.setIdcon(Integer.parseInt(request.getParameter("idcon")));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		contatoDao.updateContato(contato);
		response.sendRedirect("main");
	}


	/**
	 * Report contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void reportContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document documento = new Document();
		try {
			response.setContentType("apllication/pdf");
			response.addHeader("Content-Disposition", "inline; filename=" + "contatos.pdf");
			PdfWriter.getInstance(documento, response.getOutputStream());
			documento.open();
			documento.add(new Paragraph("Lista de contatos:"));
			documento.add(new Paragraph(" "));
			PdfPTable tabela = new PdfPTable(3);
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("E-mail"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			ArrayList<Contato> lista = contatoDao.selectAllContato();
			for (Contato contato : lista) {
				tabela.addCell(contato.getNome());
				tabela.addCell(contato.getFone());
				tabela.addCell(contato.getEmail());
			}
			documento.add(tabela);
			documento.close();
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}
	}
}
