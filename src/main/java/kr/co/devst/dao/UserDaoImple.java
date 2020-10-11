package kr.co.devst.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.devst.model.UserVO;


@Repository
public class UserDaoImple implements UserDao{
	
	@Autowired
	private SqlSession sqlSession;
	private static final String NAME_SPACE = "kr.co.devst.dao.UserDao";
	
	// 이메일 중복확인
	@Override
	public int emailChk(String memEmail) throws Exception {
		int result = sqlSession.selectOne(NAME_SPACE+ ".emailChk", memEmail);
		return result;
	}

	// 닉네임 중복확인
	@Override
	public int nickNameChk(String memNickName) {
		int result = sqlSession.selectOne(NAME_SPACE+ ".nickNameChk", memNickName);
		return result;
	}

	// 회원가입
	@Override
	public int doJoin(UserVO userVO) {
		return  sqlSession.insert(NAME_SPACE+".doJoin", userVO);
	}

	@Override
	public int modUserInfo(UserVO param) {
		return sqlSession.update(NAME_SPACE+".modUserInfo",param);
	}

	

}
