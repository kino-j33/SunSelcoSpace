package jp.co.sunselcospace.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.naming.NamingException;

import jp.co.sunselcospace.dao.AccountDao;
import jp.co.sunselcospace.dto.AccountDto;
import jp.co.sunselcospace.entity.AccountEntity;
import jp.co.sunselcospace.form.AccountForm;
import jp.co.sunselcospace.util.Utility;
//2件　member追加

public class AccountService {
	public AccountEntity getAccountByIdPassword(AccountDto accountDto)
			throws NoSuchAlgorithmException, NamingException, SQLException {
		//■パスワードを平文からハッシュ値に変換して登録する
		//String passwordHash = Utility.digest(accountDto.getPassword());
		//■検索条件
		//		AccountEntity accountCondition = new AccountEntity();
		//		accountCondition.setId(accountDto.getId());
		//		accountCondition.setPassword(passwordHash);

		//■検索処理
		AccountDao accountDao = new AccountDao();
		AccountEntity accountEntity = accountDao.getAccountByIdAndPassword(accountDto);
		return accountEntity;
	}

	/**
	* 完了工程向け: ID重複再確認
	* @member追記
	*/

	public boolean checkDuplicateId(AccountForm form) throws NamingException, SQLException {
		AccountDao dao = new AccountDao();
		return dao.existsById(form.getId());
	}

	/**
	* 完了工程向け: パスワードハッシュ化（MD5）
	*  @member追記
	*/
	public String hashPassword(AccountForm form) throws NoSuchAlgorithmException {
		return Utility.digest(form.getPassword());
	}

	/**
	* 完了工程向け: DTOへ詰め替え
	*/
	public AccountDto convertToAccountDto(AccountForm form, String hashedPassword) {
		AccountDto dto = new AccountDto();
		dto.setName(form.getName());
		dto.setId(form.getId());
		dto.setPasswordHash(hashedPassword);
		return dto;
	}

}
