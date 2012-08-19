package ar.edu.itba.it.paw.hotelapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.hotel.Comment;
import ar.edu.itba.it.paw.hotel.Hotel;
import ar.edu.itba.it.paw.hotel.HotelManager;

public class ViewHotel extends HttpServlet {

	private static final long serialVersionUID = 1L;
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		String codeString = req.getParameter("code");
		if (codeString == null || codeString.isEmpty()) {
			out.println("<h2>Invalid hotel code</h2>");
			return;
		}
		int code;
		try {
			code = Integer.parseInt(codeString);
		} catch (NumberFormatException e) {
			out.println("<h2>Hotel code must be numeric</h2>");
			return;
		}
		HotelManager hotelManager = new HotelManager();
		Hotel hotel = hotelManager.getHotel(code);
		if (hotel == null) {
			out.printf("<h2>Hotel %d does not exists</h2>", code);
			return;
		}
		addHotelInfo(out, hotel);
		addComentsArea(out, hotel);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	private void addHotelInfo(PrintWriter out, Hotel hotel) {
		out.println("<a href='" + ServletName.LIST_HOTELS + "'><< Volver</a>");
		out.printf("<h2>Hotel %s - %d stars</h2>", hotel.getName(), hotel.getRating());
		out.println("<ul>");
		out.printf("<li>Address: %s</li>", hotel.getAddress());
		out.printf("<li>Price: %s</li>", hotel.getPrice());
		out.printf("<li>Description: %s</li>", hotel.getDescription());
		out.println("</ul>");
		out.println("<h4>User comments:</h4>");
		out.println("<table border=\"1\">");
		for (Comment comment : hotel.getComments().getAll()) {
			out.println("<tr>");
			out.println("<th>" + comment.getAuthor() + "</th>");
			out.println("<th>" + comment.getDetails() + "</th>");
			out.println("</tr>");
		}
		out.println("</table>");
	}
	
	private void addComentsArea(PrintWriter out, Hotel hotel) {
		out.println("<form method='POST' action='" + ServletName.ADD_COMMENT + "'>");
		out.println("<h3>Share your experience!</h3>");
		out.print("<textarea rows='5' cols='20' name='comment'>Comment here</textarea>");
		out.printf("<input name='code' type='hidden' value='%s'/>", hotel.getCode());
		out.println("<br/><input type='submit' value='Submit'/>");
		out.println("</form>");
	}
}
