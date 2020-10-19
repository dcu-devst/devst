package kr.co.devst.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.devst.model.BoardEvalVO;
import kr.co.devst.model.BoardVO;
import kr.co.devst.model.CommentVO;
import kr.co.devst.utils.Utils;


@Repository
public class BoardDaoImple implements BoardDao{
	
	@Autowired
	private SqlSession sqlSession;
	private static final String NAME_SPACE = "kr.co.devst.dao.BoardDao";
	
	
	
	
	@Override
	public int doWrite(Map<String, String> map) {
		 
		return sqlSession.insert(NAME_SPACE+".doWrite",map);
	}




	@Override
	public List<BoardVO> getBoardListAll() {
		
		return sqlSession.selectList(NAME_SPACE+".getBoardListAll");
	}




	@Override
	public List<Map<String, String>> getBoardStudyList(int start, int num) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("num", num);
		
		return sqlSession.selectList(NAME_SPACE+".getBoardStudyList",map);
	}




	@Override
	public List<Map<String, String>> getBoardNomalList(int start, int num) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("num", num);
		return sqlSession.selectList(NAME_SPACE+".getBoardNomalList",map);
	}



	@Override
	public HashMap<String, String> getBoardOneInfo(BoardVO param) {
		
		return sqlSession.selectOne(NAME_SPACE+".getBoardOneInfo",param);
	}




	@Override
	public List<Map<String, String>> getMainBoardList10(String category) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("category", category);
		
		return sqlSession.selectList(NAME_SPACE+".getMainBoardList10",map);
	}




	@Override
	public int boardModify(BoardVO param) {
		
		
		
		return sqlSession.update(NAME_SPACE+".boardModify",param);
	}




	@Override
	public int boardUpHits(int brdId) {

		return sqlSession.update(NAME_SPACE+".boardUpHits",brdId);
	}




	@Override
	public int boardMaxPageNum(String category) {
		
		return sqlSession.selectOne(NAME_SPACE+".boardMaxPageNum",category);
	}




	@Override
	public int dowriteBrdComment(CommentVO param) {
		return sqlSession.insert(NAME_SPACE+".dowriteBrdComment",param);
	}




	@Override
	public List<Map<String, String>> getBrdComment5(int start, int num, int brdNum) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("num", num);
		map.put("brdNum", brdNum);
		return sqlSession.selectList(NAME_SPACE+".getBrdComment5",map);
	}




	@Override
	public int boardEval(BoardEvalVO param) {
		return sqlSession.insert(NAME_SPACE+".boardEval",param);
	}




	@Override
	public int modBoardLikeOrDisLike(int eval) {
		return sqlSession.update(NAME_SPACE+".modBoardLikeOrDisLike",eval);
	}




	@Override
	public int getCurrentBrdEval(Map<String, Integer> map) {
		return sqlSession.selectOne(NAME_SPACE+".getCurrentBrdEval",map);
	}




	
}
