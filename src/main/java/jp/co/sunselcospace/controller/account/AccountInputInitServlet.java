package jp.co.sunselcospace.controller.account;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.sunselcospace.constant.Constant;

/**
* アカウント登録機能_入力サーブレット
* 画面設計書/詳細設計書に基づき、入力画面(AccountInput.jsp)へ遷移する。
* - 確認工程から渡されたエラーメッセージ(ERROR_MESSAGE_LIST)をそのままJSPに渡す。
* * * @author member
*/
@WebServlet("/public/AccountInputInitServlet")
public class AccountInputInitServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// 確認工程から送られてくるエラーメッセージ（任意）
		Object errors = request.getAttribute(Constant.ERROR_MESSAGE_LIST);
		if (errors != null && errors instanceof List<?>) {
			request.setAttribute(Constant.ERROR_MESSAGE_LIST, (List<String>) errors);
		}

		// 入力画面へフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/account/AccountInput.jsp");
		dispatcher.forward(request, response);
	}
}
