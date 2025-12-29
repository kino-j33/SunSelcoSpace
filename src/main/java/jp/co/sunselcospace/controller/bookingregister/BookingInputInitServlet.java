package jp.co.sunselcospace.controller.bookingregister;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.sunselcospace.constant.Constant;
import jp.co.sunselcospace.constant.ErrorMessage;
import jp.co.sunselcospace.entity.RoomEntity;
import jp.co.sunselcospace.exception.DataAccessException;
import jp.co.sunselcospace.form.BookingForm;
import jp.co.sunselcospace.service.BookingService;
import jp.co.sunselcospace.service.RoomService;

/**
 * Servlet implementation class BookingInputInitServlet
 */
@WebServlet("/BookingInputInitServlet")
public class BookingInputInitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingInputInitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    //Serviceの作成
    //メソッド内では不可？
	private RoomService roomService = new RoomService();
	private BookingService bookingService = new BookingService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		//エラーメッセージリストの作成
		List<String> errorMessageList = new ArrayList<>();
		
		
		//エラーメッセージリストが既にあれば追加
		//定数なのでチェックしなくてよいはず
		@SuppressWarnings("unchecked")
		List<String> errorMessageListBefore =(List<String>) request.getAttribute(Constant.ERROR_MESSAGE_LIST);
		
		if (errorMessageListBefore != null) {
		    errorMessageList.addAll(errorMessageListBefore);
		}
		
		// あれば予約日をリクエストから受け取る
		String beforeStrBookingDate = request.getParameter("bookingDate");
		
		//tryブロックスコープの問題で宣言だけ先に
		LocalDate beforeBookingDate = null;
		
		//予約日が日付に変換できないか
		if(beforeStrBookingDate != null) {
			try {
				beforeBookingDate = LocalDate.parse(beforeStrBookingDate);
			} catch (DateTimeParseException e) {
				e.printStackTrace();
				errorMessageList.add(ErrorMessage.BOOKING_DATE_FORMAT_ERROR_MESSAGE);
			}
		}
		
		//あれば利用目的を受け取る
		String beforePurpose = request.getParameter("purpose");
		
		
		// 施設管理番号。
		String id = request.getParameter("id");
		
		//施設管理番号がない場合。null、空文字の場合エラーを出す
		//共通化すべき？
		if (id == null || id.isBlank()) {
			errorMessageList.add(ErrorMessage.NOT_FOUND_ROOM_MESSAGE);
			
			// 施設一覧画面への遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/public/SpaceListInitServlet");
			dispatcher.forward(request, response);
			return;
		}
		
		
		
		//roomIDをStringからIntegerに
		Integer roomId = Integer.valueOf(id);
		
		//スコープの問題で外だし
		RoomEntity roomEntity = new RoomEntity();
		
		//利用できない日のリスト作成 スコープの問題で外だし
		List<LocalDate> bookedDateList = new ArrayList<LocalDate>();
		
		try {
			
			//施設情報の取得
			roomEntity = roomService.searchRoomById(roomId);
			
			//利用できない日の取得
			bookedDateList = bookingService.searchBookedDatesByRoomId(roomId);
			
		} catch (NumberFormatException
				   | DataAccessException
				   | NamingException e){
			e.printStackTrace();
			//失敗した場合
			errorMessageList.add(ErrorMessage.DATABASE_ERROR_MESSAGE);
		}
		
		
		//前画面のデータをFormに詰める
		//null多すぎ？
		BookingForm bookingForm = new BookingForm(null, roomId, beforeBookingDate, beforePurpose, null, null, null);
		
		//前画面のデータがあれば送る
		request.setAttribute("bookingForm", bookingForm);
		
		
		// 施設情報をrequestにset
		request.setAttribute("roomEntity", roomEntity);
		
		// 利用できない日をrequestにset
		request.setAttribute("bookedDateList", bookedDateList);
		
		
		// エラーメッセージrequestにset
		request.setAttribute(Constant.ERROR_MESSAGE_LIST, errorMessageList);
		
		// 予約入力画面への遷移
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/bookingregister/BookingInput.jsp");
		dispatcher.forward(request, response);
		
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		//本当はPostのほうが良い?
	}
}
