package coding.mentor.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import coding.mentor.entity.Book;
import coding.mentor.entity.BookInCart;
import coding.mentor.entity.Order;
import coding.mentor.entity.OrderDetail;
import coding.mentor.service.BookService;
import coding.mentor.service.OrderDetailService;
import coding.mentor.service.OrderService;

/**
 * Servlet implementation class Cart
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CartServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String action = request.getParameter("action");

			// get thong tin book tu request
			int bookId = Integer.parseInt(request.getParameter("bookId"));
			BookService bookService = new BookService();
			// get book list tuwf session
			HttpSession session = request.getSession();
			HashMap<Integer, BookInCart> cart = (HashMap<Integer, BookInCart>) session.getAttribute("cart");
			if (action != null && action.equals("AddCart")) {
				// neu chua co cart, tao moi
				if (cart == null) {
					cart = new HashMap<>();
				}
				// kiem tra book da co trong cart chua
				// ton tai thi quanity + 1
				if (cart.containsKey(bookId)) {
					BookInCart bookInCart = cart.get(bookId);
					bookInCart.setQuantity(bookInCart.getQuantity() + 1);
					request.setAttribute("bookInCart", bookInCart);
					// neu chua ton tai, them moi voi so luong 1
				} else {
					Book book = bookService.getBookDetails(bookId); // lay book tu database
					BookInCart newBookInCart = new BookInCart(book);
					cart.put(bookId, newBookInCart);
					request.setAttribute("bookInCart", newBookInCart);
				}
				session.setAttribute("cart", cart);

				response.sendRedirect("home");
			} else if (action != null && action.equals("ViewCart")) {
				response.sendRedirect("cart.jsp");
			} else if (action != null && action.equals("Remove")) {
				cart.remove(bookId);
				response.sendRedirect("cart.jsp");

			} else if (action != null && action.equals("Checkout")) {
				OrderService orderService = new OrderService();
				OrderDetailService orderDetailService = new OrderDetailService();

				int accountId = (int) session.getAttribute("accountId");
				Order order = new Order(accountId, false);
				int orderId = orderService.newOrder(order);
				List<OrderDetail> orderDetails = new ArrayList<>();
				for (int key : cart.keySet()) {
					OrderDetail orderDetail = new OrderDetail(orderId, key);
					orderDetailService.newOrderDetail(orderDetail);
					orderDetails.add(orderDetail);
				}

				List<Book> bookInOrderDetail = new ArrayList<>();
				int total = 0;
				for (OrderDetail orderDetail : orderDetails) {
					int bookIdInOrderDetail = orderDetail.getBookId();
					Book bookInOrder = bookService.getBookDetails(bookIdInOrderDetail);
					bookInOrderDetail.add(bookInOrder);
					total = total + bookInOrder.getPrice();
				}

				session.removeAttribute("cart");
				request.setAttribute("orderDetail", orderDetails);
				request.setAttribute("bookInOrderDetail", bookInOrderDetail);
				request.setAttribute("total", total);
				request.setAttribute("checkoutMes", "successfully check out!");

				RequestDispatcher rd = request.getRequestDispatcher("order-detail.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
