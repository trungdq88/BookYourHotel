package samples;

import java.io.IOException;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

public class SearchServlet extends HttpServlet {

  @Resource(name = "BooksDB")
  private DataSource booksDB;

  /**
   * Processes requests for both HTTP
   * <code>GET</code> and
   * <code>POST</code> methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    HttpSession session = request.getSession();
    Member member = (Member) session.getAttribute("member");
    if (member != null) {
      String action = request.getParameter("action");
      ArrayList<Book> results = new ArrayList<Book>();
      SearchBean bean = new SearchBean(this, booksDB);
      if (action.equals("By Title")) {
        String name = request.getParameter("name");
        bean.setName(name);
        results = bean.findByName();
      } else if (action.equals("By Price")) {
        float min = Float.parseFloat(request.getParameter("min"));
        float max = Float.parseFloat(request.getParameter("max"));
        bean.setMin(min);
        bean.setMax(max);
        results = bean.findByPrice();
      }
      request.setAttribute("results", results);
      request.getRequestDispatcher("results.jsp").forward(request, response);
    } else {
      response.sendRedirect("login.jsp");
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP
   * <code>GET</code> method.
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
   * Handles the HTTP
   * <code>POST</code> method.
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
