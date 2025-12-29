package jp.co.sunselcospace.controller.cancel;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.sunselcospace.constant.ErrorMessage;
import jp.co.sunselcospace.controller.base.CancelAbstract;
import jp.co.sunselcospace.entity.AccountEntity;
import jp.co.sunselcospace.entity.BookingEntity;
import jp.co.sunselcospace.entity.RoomEntity;
import jp.co.sunselcospace.exception.AccountMismatchException;
import jp.co.sunselcospace.exception.DataAccessException;
import jp.co.sunselcospace.service.BookingService;
import jp.co.sunselcospace.service.RoomService;
import jp.co.sunselcospace.util.Utility;

/**
 * @author Yamashita
 */
@WebServlet("/BookingCancelConfirmationServlet")
public class BookingCancelConfirmationServlet extends CancelAbstract {
	private static final String FORWARD_PATH = "/BookingStatusInitServlet"; // フォワード先

	private BookingService bookingService = new BookingService();
	private RoomService roomService = new RoomService();

	/**
	 *予約情報を取得し、予約キャンセル確認画面に遷移
	 *予約情報の取得が出来なかった場合、予約一覧のサーブレットに遷移
	 *@author Yamashita
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 予約番号。bookingIdがnull、空文字の場合NumberFormatExceptionがthrowされる
		String bookingId = request.getParameter("bookingId");

		try {
			// DBの予約情報の取得
			BookingEntity bookingEntity = bookingService.searchBookingById(bookingId);

			// ログイン中のアカウントを取得
			AccountEntity loginAccountEntity = Utility.getLoginAccountEntity(request);

			// 予約情報のアカウントIDとログイン中のアカウントのアカウントIDが一致しているか
			if (checkAccountMatch(loginAccountEntity, bookingEntity)) {

				// 施設情報の取得
				int roomId = bookingEntity.getRoomId();

				RoomEntity roomEntity = roomService.searchRoomById(roomId);

				// requestにset
				request.setAttribute("bookingEntity", bookingEntity);

				request.setAttribute("roomEntity", roomEntity);

				// 予約キャンセル画面への遷移
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/WEB-INF/cancel/bookingCancelConfirmation.jsp");
				dispatcher.forward(request, response);

			} else { // アカウントIDが一致していない場合
				throw new AccountMismatchException(ErrorMessage.INVALID_BOOKING_ID_MESSAGE);
			}

		// @formatter:off
		} catch (NumberFormatException
				   | DataAccessException
				   | AccountMismatchException e) {
		// @formatter:on

			// @formatter:off
			// エラーメッセージの追加とフォワード処理
			processException(ErrorMessage.NOT_FOUND_BOOKING_MESSAGE
									   , FORWARD_PATH
									   , request
									   , response);
			// @formatter:on
			return;
		}
	}
}
