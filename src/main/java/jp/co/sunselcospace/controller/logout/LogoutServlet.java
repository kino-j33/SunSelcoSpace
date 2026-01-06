package jp.co.sunselcospace.controller.logout;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

	//ログアウト処理を GET / POST のどちらでも出来るようにする処理
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	//ログアウト処理
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//セッションを取得
		HttpSession session = request.getSession();
		//セッションを破棄
		session.invalidate();

		//■コンテキストルート（施設一覧）へ遷移する
		response.sendRedirect(request.getContextPath());
	}
}
