package samples;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

public class CartServlet extends HttpServlet {

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
    if (member != null && member.isUser()) {
      Cart cart = (Cart) session.getAttribute("cart");
      if (cart == null) {
        cart = new Cart();
        session.setAttribute("cart", cart);
      }
      String action = request.getParameter("action");
      if (action.equals("add")) {
        String id = request.getParameter("id");
        BookBean bean = new BookBean(this, booksDB);
        bean.setId(id);
        Book book = bean.getBook();
        cart.add(book);
        session.setAttribute("cart", cart);
        response.sendRedirect("ShowServlet");
      } else if (action.equals("view")) {
        response.sendRedirect("cart.jsp");
      } else if (action.equals("remove")) {
        String id = request.getParameter("id");
        cart.remove(id);
        session.setAttribute("cart", cart);
        response.sendRedirect("cart.jsp");
      } else if (action.equals("out")) {
        String code = String.format("INV%05d", System.currentTimeMillis() % 100000);
        request.setAttribute("books", cart.getContents());
        session.removeAttribute("cart");
        request.getRequestDispatcher("checkout.jsp?code=" + code).forward(request, response);
      }
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
