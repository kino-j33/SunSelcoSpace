package jp.co.sunselcospace.controller.login;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jp.co.sunselcospace.constant.Constant;
import jp.co.sunselcospace.entity.AccountEntity;


@WebServlet("/public/LoginInitServlet")
public class LoginInitServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		AccountEntity accountEntity = (AccountEntity) session.getAttribute(Constant.SESSION_KEY_LOGIN_ACCOUNT);

		if (accountEntity == null) {
			//■ログイン画面へ遷移する
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/accountLogin.jsp");
			dispatcher.forward(request, response);
		} else {
			//施設一覧画面へ遷移する
			response.sendRedirect(request.getContextPath());
		}
	}
}

