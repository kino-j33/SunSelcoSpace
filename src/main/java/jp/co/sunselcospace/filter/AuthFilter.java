package jp.co.sunselcospace.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jp.co.sunselcospace.constant.Constant;
import jp.co.sunselcospace.constant.ErrorMessage;
import jp.co.sunselcospace.entity.AccountEntity;

/**
 * 認証必須ページかの確認
 */
//@WebFilter("/*")
//すべてのリクエストにフィルタをかける
public class AuthFilter implements Filter {

	//認証処理対象外URI
	private static final String[] excludeUris = {
			"^/$",
			"^/public/.*",
			"^/static/.*" };

	/**
	 * 初期化処理
	 * インスタンス作成後、自動で実行される
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse,
			FilterChain filterChain)
			throws IOException, ServletException {

		//Httpが使える方に変換
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		//リクエストURLを取得する(加工もしてる)
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		contextPath = "^" + contextPath;
		uri = uri.replaceAll(contextPath, "");

		//認証対象かどうかを判定
		if (isFilterTarget(uri)) {
			//ログインユーザ情報をセッションスコープから取得する
			HttpSession session = request.getSession();
			AccountEntity entity = (AccountEntity) session.getAttribute(Constant.SESSION_KEY_LOGIN_ACCOUNT);
			//ログインしていなければ、ログイン画面へ遷移する
			if (entity == null) {
				//ArrayListをインスタンス化し、エラーメッセージを追加
				List<String> errorMessageList = new ArrayList<>();
				errorMessageList.add(ErrorMessage.NOT_LOGIN_MESSAGE);
				//エラーメッセージをリクエストにセット
				request.setAttribute("ERROR_MESSAGE_LIST", errorMessageList);
				//LoginInitServletへフォワード
				RequestDispatcher dispatcher = request.getRequestDispatcher("/public/LoginInitServlet");
				dispatcher.forward(request, response);
				//return; で呼び出し元に返す
				return;
			}
		}
		//ログインしていればサーブレット／JSPを実行する
		filterChain.doFilter(servletRequest, servletResponse);
	}

	//ログイン必須かどうかを除外リスト（"^/$", "^/public/.*", "^/static/.*"）で判定
	private boolean isFilterTarget(String uri) {
		for (String excludeUri : excludeUris) {
			if (uri.matches(excludeUri)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 後処理
	 * インスタンスを破棄する前、自動で実行される
	 */
	@Override
	public void destroy() {

	}

}
