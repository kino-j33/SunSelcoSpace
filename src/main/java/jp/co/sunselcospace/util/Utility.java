package jp.co.sunselcospace.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import jp.co.sunselcospace.constant.Constant;
import jp.co.sunselcospace.entity.AccountEntity;

/**
 * @author Yamashita
 */
public class Utility {
	/**
	 * ログイン中のアカウトのAccountEntityを返します。
	 * @author Yamashita
	 *
	 * @param request
	 * @return　AccountEntity
	 * @throws UnsupportedEncodingException
	 */
	public static AccountEntity getLoginAccountEntity(HttpServletRequest request) throws UnsupportedEncodingException {
		HttpSession session = request.getSession();
		return (AccountEntity) session.getAttribute(Constant.SESSION_KEY_LOGIN_ACCOUNT);
	}

	public static String digest(String password) throws NoSuchAlgorithmException {
		//■パスワードを平文からハッシュ値に変換して登録する
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		//■MD5形式のハッシュ値を計算する => 128bit（16バイト）
		byte[] digest = messageDigest.digest(password.getBytes());
		//■128bit（16バイト） => 32桁の16進数文字列に変換する
		String passwordHash = HexFormat.of().formatHex(digest);
		return passwordHash;
	}

}
