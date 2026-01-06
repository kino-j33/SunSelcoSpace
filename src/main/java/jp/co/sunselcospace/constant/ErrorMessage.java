package jp.co.sunselcospace.constant;

/**
 * @author Yamashita
 */
public class ErrorMessage {
	public static final String NOT_FOUND_BOOKING_MESSAGE = "予約情報が取得できませんでした";

	public static final String DATABASE_ERROR_MESSAGE = "データベースへの接続に問題が発生しました。管理者にご連絡ください。";

	public static final String SQL_ERROR_MESSAGE = "データベースから情報を取得できませんでした。管理者にご連絡ください。";

	public static final String INVALID_BOOKING_ID_MESSAGE = "不正な予約情報が検出されました。";

	public static final String NOT_FOUND_ROOM_MESSAGE = "施設情報の取得に失敗しました。時間をおいて再度お試しください。";

	public static final String NOT_LOGIN_MESSAGE = "該当画面はログインする必要があります。ログインしてから、再度操作してください";

	public static final String DIFFERENT_ID_PASSWORD_MESSAGE = "ログインID、または、パスワードが違います。";
	/**
	* 画面別エラーメッセージ（入力工程 9-1/9-2）
	*  * @author member
	*/

	// 入力値の形式誤り
	public static final String ID_INPUT_ERROR = "規則外の値が入力されました。最初から入力をやり直してください。";

	// 確認後〜登録前の重複使用
	public static final String ID_DUPLICATE_ON_CONFIRM = "IDが別の方に利用されています。最初から入力をやり直してください。";

	/**
	 * 予約機能周りのエラー
	 * @author member
	 */

	public static final String BOOKING_DATE_REQUIRED_MESSAGE = "予約日が入力されていません。";
	public static final String BOOKING_DATE_FORMAT_ERROR_MESSAGE = "予約日の形式が正しくありません。";
	public static final String BOOKING_DATE_PAST_MESSAGE = "予約日が過去の日付です。";
	public static final String BOOKING_DATE_DUPLICATE_MESSAGE = "指定した予約日は既に予約されています。";
	public static final String PURPOSE_REQUIRED_MESSAGE = "利用目的が入力されていません。";
	public static final String TERMS_NOT_AGREED_MESSAGE = "利用規約に同意しないと利用できません。";

}
