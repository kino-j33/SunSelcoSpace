package jp.co.sunselcospace.controller.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.sunselcospace.constant.Constant;

/**
 * @author Yamashita
 */
public abstract class ControllerAbstract extends HttpServlet {

	/**
	 * エラーメッセージ（List<String>）をrequestにセットし、フォワードする。
	 * @author Yamashita
	 *
	 * @param errorMessageList エラーメッセージ
	 * @param forwardPath フォワード先
	 * @param request
	 * @param response
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void processException(List<String> errorMessageList, String forwardPath, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute(Constant.ERROR_MESSAGE_LIST, errorMessageList);

		processForward(forwardPath, request, response);
	}

	/**
	 * エラーメッセージ（String）をrequestにセットし、フォワードする。
	 * @author Yamashita
	 *
	 * @param errorMessage エラーメッセージ
	 * @param forwardPath フォワード先
	 * @param request
	 * @param response
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void processException(String errorMessage, String forwardPath, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<String> errorMessageList = new ArrayList<>();
		errorMessageList.add(errorMessage);

		request.setAttribute(Constant.ERROR_MESSAGE_LIST, errorMessageList);

		processForward(forwardPath, request, response);
	}

	/**
	 * フォワードします
	 * @author Yamashita
	 *
	 * @param forwardPath フォワード先
	 * @param request
	 * @param response
	 *
	 * @throws ServletException
	 * @throws IOException
	 */
	private static void processForward(String forwardPath, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request, response);
	}
}