package jp.co.sunselcospace.controller.bookingstatus;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.sunselcospace.controller.base.ControllerAbstract;
import jp.co.sunselcospace.dto.BookingDto;
import jp.co.sunselcospace.entity.AccountEntity;
import jp.co.sunselcospace.entity.BookingEntity;
import jp.co.sunselcospace.exception.DataAccessException;
import jp.co.sunselcospace.service.BookingService;
import jp.co.sunselcospace.util.Convertor;
import jp.co.sunselcospace.util.Utility;

@WebServlet("/BookingStatusInitServlet")
public class BookingStatusInitServlet extends ControllerAbstract {
	private static final String ERROR_FORWARD_PATH = "/SpaceListInitServlet";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		AccountEntity loginAccountEntity = Utility.getLoginAccountEntity(request);
		BookingService bookingService = new BookingService();

		try {
			List<BookingEntity> bookingEntities = bookingService
					.searchBookingByAccountId(loginAccountEntity.getId());

			// @formatter:off
			List<BookingDto> bookingStatusList = 										Convertor.bookingEntityToBookingDto(bookingEntities);
			// @formatter:on

			request.setAttribute("bookingStatusList", bookingStatusList);

			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/WEB-INF/bookingstatus/bookingStatus.jsp");
			dispatcher.forward(request, response);

		} catch (NumberFormatException | DataAccessException e) {

			// @formatter:off
			// エラーメッセージの追加とフォワード処理
			processException(e.getMessage()
									   , ERROR_FORWARD_PATH
									   , request
									   , response);
			// @formatter:on
		}
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
