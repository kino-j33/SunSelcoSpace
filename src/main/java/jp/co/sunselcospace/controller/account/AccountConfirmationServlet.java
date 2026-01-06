package jp.co.sunselcospace.controller.account;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
import jp.co.sunselcospace.form.AccountForm;

/**
* アカウント登録機能_確認サーブレット
* - 入力値(name, id, password)を受領しバリデーション
* - ID重複使用チェック
* - 問題なければ確認JSPへ、エラー時は入力画面へ戻す
* * * @author member
*/
@WebServlet("/public/AccountConfirmationServlet")
public class AccountConfirmationServlet extends HttpServlet {

	// 半角英数字のみ
	private static final Pattern ALNUM = Pattern.compile("^[A-Za-z0-9]+$");

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

		// 1) バリデーション
		validate(form, errors);

		// 2) 重複チェック（エラーがなければ実施）
		if (errors.isEmpty()) {
			try {
				AccountDao dao = new AccountDao();
				boolean exists = dao.existsById(id);
				if (exists) {
					errors.add(ErrorMessage.ID_DUPLICATE_ON_CONFIRM);
				}
			} catch (NamingException | SQLException e) {
				//例外は共通メッセージで扱う
				errors.add(ErrorMessage.DATABASE_ERROR_MESSAGE);
				e.printStackTrace();
			}
		}

		// 3) 振り分け
		if (!errors.isEmpty()) {
			request.setAttribute(Constant.ERROR_MESSAGE_LIST, errors);
			// 入力画面へフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/public/AccountInputInitServlet");
			dispatcher.forward(request, response);
			return;
		}

		// 成功：確認画面へ（表示用はname,idのみ、passwordは非表示テキスト）
		request.setAttribute("AccountForm", form);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/account/AccountConfirmation.jsp");
		dispatcher.forward(request, response);
	}

	private void validate(AccountForm form, List<String> errors) {
		// name: 必須・半角英数字・最大15
		if (isBlank(form.getName()) || !ALNUM.matcher(form.getName()).matches() || form.getName().length() > 15) {
			errors.add(ErrorMessage.ID_INPUT_ERROR);
		}
		// id: 必須・半角英数字・8のみ可
		if (isBlank(form.getId()) || !ALNUM.matcher(form.getId()).matches() || form.getId().length() != 8) {
			errors.add(ErrorMessage.ID_INPUT_ERROR);
		}
		// password: 必須・半角英数字・最大20
		if (isBlank(form.getPassword()) || !ALNUM.matcher(form.getPassword()).matches()
				|| form.getPassword().length() > 20) {
			errors.add(ErrorMessage.ID_INPUT_ERROR);
		}
	}

	private boolean isBlank(String s) {
		return s == null || s.trim().isEmpty();
	}
}
