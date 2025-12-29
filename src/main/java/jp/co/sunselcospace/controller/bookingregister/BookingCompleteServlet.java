package jp.co.sunselcospace.controller.bookingregister;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import jp.co.sunselcospace.entity.AccountEntity;
import jp.co.sunselcospace.entity.BookingEntity;
import jp.co.sunselcospace.entity.RoomEntity;
import jp.co.sunselcospace.exception.DataAccessException;
import jp.co.sunselcospace.form.BookingForm;
import jp.co.sunselcospace.service.BookingService;
import jp.co.sunselcospace.service.RoomService;
import jp.co.sunselcospace.util.Utility;

/**
 * Servlet implementation class BookingInputInitServlet
 */
@WebServlet("/BookingCompleteServlet")
public class BookingCompleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingCompleteServlet() {
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
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
		List<String> errorMessageList = new ArrayList<>();
		
		
		// 施設管理番号。
		String id = request.getParameter("id");
		
		//施設管理番号がない場合。null、空文字の場合エラーを出す
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
		
		
		
		//スコープの問題で外だし
		AccountEntity loginAccountEntity = null;
		
		// ログイン中のアカウントを取得
		try {
			loginAccountEntity = Utility.getLoginAccountEntity(request);			
		}catch(Exception e){
			//ログイン情報にエラー？予約情報が取得できませんでしたという文言に
			errorMessageList.add(ErrorMessage.NOT_FOUND_BOOKING_MESSAGE);
		}
		
		
		//ログインIDの取得
		String strLoginId = loginAccountEntity.getId();
		
		//DateTimeの取得
		LocalDateTime now = LocalDateTime.now();
		
		//nullだらけのForm作成
		BookingForm bookingForm = new BookingForm(null, roomId, bookingDate, purpose, null, null, null);
		
		//BookingEntityの作成
		BookingEntity bookingEntity = new BookingEntity(null, roomId, strLoginId, bookingDate, purpose, "0" , now, now);
		
		
		
		//エラーがなければ登録
		if (errorMessageList.isEmpty()) {
			
			try {
				//予約のDB登録
				bookingService.createBooking(bookingEntity);
			} catch (Exception e) {
				e.printStackTrace();
				errorMessageList.add(ErrorMessage.DATABASE_ERROR_MESSAGE);
			}
		}
		
		
		
		//スコープの問題で外だし
		RoomEntity roomEntity = new RoomEntity();
		
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
		
		
		// 予約情報をrequestにset
		// bookingFormに変更 詳細設計と違う
		request.setAttribute("bookingForm", bookingForm);
		
		
		// エラーをrequestにset
		request.setAttribute(Constant.ERROR_MESSAGE_LIST, errorMessageList);
		
		
		
		//エラーがあればsetして入力に遷移
		if (!errorMessageList.isEmpty()) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/BookingInputInitServlet");
			dispatcher.forward(request, response);
			return;
		}
		
		
		// 予約完了画面への遷移
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/bookingregister/BookingComplete.jsp");
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
