package jp.co.sunselcospace.controller.base;

import java.util.Objects;

import jp.co.sunselcospace.entity.AccountEntity;
import jp.co.sunselcospace.entity.BookingEntity;

/**
 * @author Yamashita
 */
public abstract class CancelAbstract extends ControllerAbstract {

	/**
	 * ログイン中のアカウトと予約情報のアカウトが一致しているかをIDで比較。
	 * @author Yamashita
	 *
	 * @param accountEntity
	 * @param bookingEntity
	 * @return 一致している場合：TRUE
	 */
	protected boolean checkAccountMatch(AccountEntity accountEntity, BookingEntity bookingEntity) {
		return !Objects.isNull(accountEntity) && !Objects.isNull(bookingEntity)
				&& accountEntity.getId().equals(bookingEntity.getAccountId());
	}
}