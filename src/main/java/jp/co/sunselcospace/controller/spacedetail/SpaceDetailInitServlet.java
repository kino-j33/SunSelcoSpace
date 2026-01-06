package jp.co.sunselcospace.controller.spacedetail;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.sunselcospace.entity.RoomEntity;
import jp.co.sunselcospace.exception.DataAccessException;
import jp.co.sunselcospace.service.RoomService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/public/SpaceDetailInitServlet")
public class SpaceDetailInitServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String roomId = request.getParameter("room-id");

		RoomService roomService = new RoomService();
		RoomEntity roomEntity;
		//施設情報を持ってくる
		try {
			roomEntity = roomService.searchRoomById(Integer.valueOf(roomId));
			request.setAttribute("roomEntity", roomEntity);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/spaceDetail/spaceDetail.jsp");
			requestDispatcher.forward(request, response);
			//施設情報がなければ前の画面へ遷移する
		} catch (NumberFormatException | DataAccessException e) {
			e.printStackTrace();

			//遷移先前の画面
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/public/SpaceListInitServlet");
			requestDispatcher.forward(request, response);

		}

	}

}
