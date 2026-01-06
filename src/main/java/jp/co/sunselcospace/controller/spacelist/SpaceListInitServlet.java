package jp.co.sunselcospace.controller.spacelist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.sunselcospace.entity.RoomEntity;
import jp.co.sunselcospace.service.RoomService;

/**
 * spaceListInitServletからRoomServiceに施設一覧を取得するように依頼
 */

@WebServlet("/public/SpaceListInitServlet")
public class SpaceListInitServlet extends HttpServlet {
	//警告回避のために書かれる?
	private static final long serialVersionUID = 1L;
	
	//roomServiceをインスタンス化
	private RoomService roomService = new RoomService();

    //RoomDaoを使ってRoomリストを取得
	protected void doGet(HttpServletRequest request, 
						HttpServletResponse response) 
							throws ServletException, IOException {
		
		//DBから施設リストを取得
		List<RoomEntity> roomList;
		try {
			roomList = roomService.getRoomList();
			//spaceListServletはRoomServiceから受け取った施設情報リストを
			//requestにセットする
			request.setAttribute("roomList", roomList);
			
			//リストをspaceList.jspに渡して遷移（JSPにフォワード）する
			RequestDispatcher rd = 
			request.getRequestDispatcher("/WEB-INF/spaceList/spaceList.jsp");
				rd.forward(request, response);
				
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}

//http:localhost:8080/SunSelcoSpace/