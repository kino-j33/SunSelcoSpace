package jp.co.sunselcospace.controller.login;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jp.co.sunselcospace.constant.Constant;
import jp.co.sunselcospace.constant.ErrorMessage;
import jp.co.sunselcospace.dto.AccountDto;
import jp.co.sunselcospace.entity.AccountEntity;
import jp.co.sunselcospace.form.AccountForm;
import jp.co.sunselcospace.service.AccountService;
import jp.co.sunselcospace.util.Utility;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/public/LoginServlet")
public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		String password = request.getParameter("password");
		AccountForm loginForm = new AccountForm();
		loginForm.setId(id);
		loginForm.setPassword(password);

		//アカウント再チェック
		HttpSession session = request.getSession();
		AccountEntity accountEntity = (AccountEntity) session.getAttribute(Constant.SESSION_KEY_LOGIN_ACCOUNT);
		if (accountEntity != null) {
			//施設一覧画面へ遷移する
			response.sendRedirect(request.getContextPath());
			return;
		}

		AccountDto accountDto = new AccountDto();
		accountDto.setId(id);
		try {
			String passwordHash = Utility.digest(password);
			accountDto.setPasswordHash(passwordHash);
			AccountService accountService = new AccountService();
			accountEntity = accountService.getAccountByIdPassword(accountDto);
			if (accountEntity != null) {
				//■ログイン成功
				//■ログイン状態（情報）をセッションスコープに保存する
				session = request.getSession();
				session.setAttribute(Constant.SESSION_KEY_LOGIN_ACCOUNT, accountEntity);

				//  リダイレクトで遷移する。
				//  画面再読み込によるログイン処理の再実行を防止する
				//　施設一覧画面へ
				response.sendRedirect(request.getContextPath());
			} else {
				//■ログイン失敗
				List<String> errorMessageList = new ArrayList<String>();
				errorMessageList.add(ErrorMessage.DIFFERENT_ID_PASSWORD_MESSAGE);

				request.setAttribute(Constant.ERROR_MESSAGE_LIST, errorMessageList);

				//■ログイン画面へ遷移する
				RequestDispatcher dispatcher = request.getRequestDispatcher("/public/LoginInitServlet");
				dispatcher.forward(request, response);
			}

		} catch (NoSuchAlgorithmException | NamingException | SQLException e) {
			e.printStackTrace();
		}
	}
}
