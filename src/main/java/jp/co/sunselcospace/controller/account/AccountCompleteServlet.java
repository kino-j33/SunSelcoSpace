package jp.co.sunselcospace.controller.account;

import java.io.IOException;
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

import jp.co.sunselcospace.constant.Constant;
import jp.co.sunselcospace.constant.ErrorMessage;
import jp.co.sunselcospace.dao.AccountDao;
import jp.co.sunselcospace.dto.AccountDto;
import jp.co.sunselcospace.form.AccountForm;
import jp.co.sunselcospace.service.AccountService;

/**
* アカウント登録機能_完了サーブレット
* - 確認JSPから name/id/password を受領
* - ID重複を再確認
* - パスワードをハッシュ化し、DTOに詰め替えてDB登録
* - 完了画面へフォワード（氏名・IDのみ表示）
* * @author member
*/
@WebServlet("/public/AccountCompleteServlet")
public class AccountCompleteServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String password = request.getParameter("password");

		AccountForm form = new AccountForm();
		form.setName(name);
		form.setId(id);
		form.setPassword(password);

		List<String> errors = new ArrayList<>();

		// 1) 重複再チェック（確認工程から完了工程までの間に使用されていないか）
		AccountService service = new AccountService();
		try {
			boolean duplicate = service.checkDuplicateId(form);
			if (duplicate) {
				errors.add(ErrorMessage.ID_DUPLICATE_ON_CONFIRM);
			}
		} catch (NamingException | SQLException e) {
			errors.add(ErrorMessage.DATABASE_ERROR_MESSAGE);
			e.printStackTrace();
		}

		if (!errors.isEmpty()) {
			request.setAttribute(Constant.ERROR_MESSAGE_LIST, errors);
			// 入力画面へ戻す（確認ではなく入力に戻す仕様）
			RequestDispatcher dispatcher = request.getRequestDispatcher("/public/AccountInputInitServlet");
			dispatcher.forward(request, response);
			return;
		}

		// 2) パスワードをハッシュ化（MD5）
		String hashed;
		try {
			hashed = service.hashPassword(form);
		} catch (Exception e) {
			errors.add(ErrorMessage.SQL_ERROR_MESSAGE);
			e.printStackTrace();
			request.setAttribute(Constant.ERROR_MESSAGE_LIST, errors);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/public/AccountInputInitServlet");
			dispatcher.forward(request, response);
			return;
		}

		// 3) DTOに詰め替え
		AccountDto dto = service.convertToAccountDto(form, hashed);

		// 4) DB登録
		try {
			AccountDao dao = new AccountDao();
			dao.insertAccountRecord(dto);
		} catch (NamingException | SQLException e) {
			errors.add(ErrorMessage.DATABASE_ERROR_MESSAGE);
			e.printStackTrace();
			request.setAttribute(Constant.ERROR_MESSAGE_LIST, errors);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/public/AccountInputInitServlet");
			dispatcher.forward(request, response);
			return;
		}

		// 5) 完了画面へ（表示は氏名・IDのみ）
		request.setAttribute("AccountForm", form);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/account/AccountComplete.jsp");
		dispatcher.forward(request, response);
	}
}
