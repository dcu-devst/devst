package kr.co.devst.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.devst.model.BoardEvalVO;
import kr.co.devst.model.BoardVO;
import kr.co.devst.model.CommentVO;

public interface BoardDao {
	/* public int doWrite(BoardVO param); */
	public int doWrite(Map<String, String> map);
	public List<BoardVO> getBoardListAll();
	public List<Map<String, String>> getBoardStudyList(int start, int num);
	public List<Map<String, String>> getBoardNomalList(int start, int num);
	public HashMap<String, String> getBoardOneInfo(BoardVO param);//pk와 category 두개를 받아 select
	public List<Map<String, String>> getMainBoardList10(String category); //index페이지에 navigation에 따라 표기할 10개의 리스트아이템
	public int boardModify(BoardVO param);
	public int boardUpHits(int brdId);
	public int boardMaxPageNum(String category);//해당 카테고리의 최대페이지 수
	public int dowriteBrdComment(CommentVO param);
	public List<Map<String, String>> getBrdComment5(int start, int num, int brdNum);
	public int boardEval(BoardEvalVO param);
	public int modBoardLikeOrDisLike(int eval);
	public int getCurrentBrdEval(Map<String, Integer> map);
}
