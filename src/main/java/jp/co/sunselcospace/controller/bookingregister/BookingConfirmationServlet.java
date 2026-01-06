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
@WebServlet("/BookingConfirmationServlet")
public class BookingConfirmationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingConfirmationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    //Serviceの作成
  	private RoomService roomService = new RoomService();
  	private BookingService bookingService = new BookingService();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		//エラーメッセージリストの作成
		List<String> errorMessageList = new ArrayList<>();
		
		
		// 施設管理番号
		String id = request.getParameter("id");
		
		//施設管理番号がない場合
		if (id == null || id.isBlank()) {
			errorMessageList.add(ErrorMessage.NOT_FOUND_ROOM_MESSAGE);
			
			// 施設一覧画面への遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/public/SpaceListInitServlet");
			dispatcher.forward(request, response);
			return;
		}
		
		
		
		//roomIDをStringからIntegerに
		Integer roomId = Integer.valueOf(id);
		
		// 予約日をリクエストから取得
		String bookingDateStr = request.getParameter("bookingDate");
		
		//tryブロックのスコープの問題で宣言だけ先に
		LocalDate bookingDate = null;
		
		
		//予約日がnullか空文字か
		if (bookingDateStr == null || bookingDateStr.isEmpty()) {
			errorMessageList.add(ErrorMessage.BOOKING_DATE_REQUIRED_MESSAGE);
		}
		
		
		//予約日が日付に変換できるか
		if(bookingDateStr != null  && !bookingDateStr.isEmpty() ) {
			try {
				bookingDate = LocalDate.parse(bookingDateStr);
			} catch (DateTimeParseException e) {
				e.printStackTrace();
				errorMessageList.add(ErrorMessage.BOOKING_DATE_FORMAT_ERROR_MESSAGE);
			}
		}
		
		//予約日が過去ではないか
		LocalDate today = LocalDate.now();
		
		if (bookingDate != null) {
			if (bookingDate.isBefore(today)) {
				errorMessageList.add(ErrorMessage.BOOKING_DATE_PAST_MESSAGE);
			}
		}
		
		
		//エラーが空なら（予約日が未入力ではない）
		if (errorMessageList.isEmpty()){
			try {
				//予約日が重複しているかのチェック
				boolean bookingDateAvailability = bookingService.validateBookingDateAvailability(roomId,bookingDate);
				if(bookingDateAvailability == false) {
					//重複している場合（利用できない日の場合）
					errorMessageList.add(ErrorMessage.BOOKING_DATE_DUPLICATE_MESSAGE);
				}
			} catch (NumberFormatException
					| DataAccessException
					| NamingException e){
				//データベースの接続エラー？
				errorMessageList.add(ErrorMessage.DATABASE_ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
		
		
		// 利用目的。エラーを出す
		String purpose = request.getParameter("purpose");
		
		//利用目的がnull
		if(purpose == null  || purpose.isBlank()) {
			//利用目的エラー
			errorMessageList.add(ErrorMessage.PURPOSE_REQUIRED_MESSAGE);
		}
		
		// 規約同意。エラーを出す
		String agree = request.getParameter("agree");
		
		//規約同意がnull
		if(agree == null ) {
			//規約同意エラー
			errorMessageList.add(ErrorMessage.TERMS_NOT_AGREED_MESSAGE);
		}
		
		
		//スコープの問題で外だし
		RoomEntity roomEntity = new RoomEntity();
		
		//nullだらけのForm作成
		BookingForm bookingForm = new BookingForm(null, roomId, bookingDate, purpose, null, null, null);
		
		
		try {
			
			//施設情報の取得
			roomEntity = roomService.searchRoomById(roomId);
			
		} catch (NumberFormatException
				   | DataAccessException e){
			//データベースの接続エラー？
			errorMessageList.add(ErrorMessage.DATABASE_ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		
		// 施設情報をrequestにset
		request.setAttribute("roomEntity", roomEntity);
		
		// 入力されていた予約情報をrequestにset
		// 詳細設計と違いbookingFormに変更
		request.setAttribute("bookingForm", bookingForm);
		
		
		// ERRORリストをrequestにset
		request.setAttribute(Constant.ERROR_MESSAGE_LIST, errorMessageList);
		
		
		//エラーがあれば入力に遷移
		if (!errorMessageList.isEmpty()) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/BookingInputInitServlet");
			dispatcher.forward(request, response);
			return;
		}
		
		
		// 予約確認画面への遷移
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/bookingregister/BookingConfirmation.jsp");
		dispatcher.forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
