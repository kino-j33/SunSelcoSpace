package jp.co.sunselcospace.controller.cancel;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.sunselcospace.constant.ErrorMessage;
import jp.co.sunselcospace.controller.base.CancelAbstract;
import jp.co.sunselcospace.entity.AccountEntity;
import jp.co.sunselcospace.entity.BookingEntity;
import jp.co.sunselcospace.exception.AccountMismatchException;
import jp.co.sunselcospace.exception.DataAccessException;
import jp.co.sunselcospace.form.BookingForm;
import jp.co.sunselcospace.service.BookingService;
import jp.co.sunselcospace.util.Utility;

/**
 * 予約のキャンセル処理の実行を行う
 * キャンセルが完了した場合は予約キャンセル画面に遷移
 * キャンセルが失敗した場合は予約一覧のサーブレットに遷移
 * @author Yamashita
 *
 */
@WebServlet("/BookingCancelCompleteServlet")
public class BookingCancelCompleteServlet extends CancelAbstract {
	private static final String FORWARD_PATH = "/BookingStatusInitServlet";

	private BookingService bookingService = new BookingService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		try {
			// requestのパラメータをもとにBookingFormをインスタンス化
			BookingForm bookingForm = getBookingForm(request);

			BookingEntity bookingEntity = bookingService.searchBookingById(String.valueOf(bookingForm.getId()));

			// ログイン中のアカウントを取得
			AccountEntity loginAccountEntity = Utility.getLoginAccountEntity(request);

			// 予約情報のアカウントIDとログイン中のアカウントのアカウントIDが一致しているか
			if (checkAccountMatch(loginAccountEntity, bookingEntity)) {
				// キャンセル処理
				bookingService.cancelBookingById(bookingForm.getId());

				request.setAttribute("bookingForm", bookingForm);

				// 予約キャンセル画面への遷移
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/WEB-INF/cancel/bookingCancelComplete.jsp");
				dispatcher.forward(request, response);
			} else {
				throw new AccountMismatchException(ErrorMessage.NOT_FOUND_BOOKING_MESSAGE);
			}

		} catch (NumberFormatException | DateTimeParseException e) {
			e.printStackTrace(); // デバック用
			// @formatter:off
			// エラーメッセージをリストとしてセットし、遷移する
			processException(e.getMessage()
									   , FORWARD_PATH
									   , request
									   , response);
			// @formatter:on
			return;

			// @formatter:off
			} catch (DataAccessException
					   | AccountMismatchException e) {
			// @formatter:on
			e.printStackTrace(); // デバック用
			processException(e.getMessage(), FORWARD_PATH, request, response);
			return;
		}
	}

	/**
	 * requestからBookingFormをインスタンス化し返す
	 * @author Yamashita
	 * @param request
	 * @return bookingForm
	 */
	private BookingForm getBookingForm(HttpServletRequest request) {
		// @formatter:off
		String id = request.getParameter("id");						// 予約番号
		String bookingDate = request.getParameter("date");	// 予約日
		String purpose = request.getParameter("purpose");		// 利用目的
		String name = request.getParameter("name");			// 施設名
		String location = request.getParameter("location");		// 場所
		String fee = request.getParameter("fee");					// 料金
		// @formatter:on

		// @formatter:off
		return new BookingForm(id
									 	   , bookingDate
									 	   , purpose
									 	   , name
									 	   , location
									 	   , fee);
		// @formatter:on
	}
}
