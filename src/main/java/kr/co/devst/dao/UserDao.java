package kr.co.devst.dao;

import kr.co.devst.model.UserVO;
public interface UserDao {

	// 이메일 중복확인
	public int emailChk(String memEmail) throws Exception;

	// 닉네임 중복확인
	public int nickNameChk(String memNickName);
	
	// 회원가입
	public int doJoin(UserVO userVO) throws Exception;

	public int modUserInfo(UserVO param);
	
}
