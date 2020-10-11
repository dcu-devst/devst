package kr.co.devst.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.devst.dao.UserDao;
import kr.co.devst.model.UserVO;


@Service
public class UserServiceImple implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder pwEncoder;

	// 이메일 중복체크
	@Override
	public int emailChk(String memEmail) throws Exception {
		int result = userDao.emailChk(memEmail);
		return result;
	}
	
	// 닉네임 중복체크
	@Override
	public int nickNameChk(String memNickName) throws Exception {
		int result = userDao.nickNameChk(memNickName);
		return result;
	}
	
	// 회원가입
	@Override
	public int doJoin(UserVO userVO) throws Exception {
		// 사용자가 입력한 비밀번호 암호화
		userVO.setMemPassword(pwEncoder.encode(userVO.getMemPassword()));
		return userDao.doJoin(userVO);
	}

	@Override
	public int modUserInfo(UserVO param) {
		return userDao.modUserInfo(param);
	}

	




}
