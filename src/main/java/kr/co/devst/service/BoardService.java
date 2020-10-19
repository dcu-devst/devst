package kr.co.devst.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.devst.model.BoardEvalVO;
import kr.co.devst.model.BoardVO;
import kr.co.devst.model.CommentVO;


public interface BoardService {
	public int doWrite(Map<String, String> map);
	public List<BoardVO> getBoardListAll();
	public List<Map<String, String>> getBoardStudyList(int start, int num);
	public List<Map<String, String>> getBoardNomalList(int start, int num); 
	
	
	public HashMap<String, String> getBoardOneInfo(BoardVO param);
	public List<Map<String,String>> getMainBoardList10(String category);
	public int boardModify(BoardVO param);
	public int boardUpHits(int brdId);
	public int boardMaxPageNum(String category);
	public int dowriteBrdComment(CommentVO param);
	public List<Map<String, String>> getBrdComment5(int start, int num, int brdNum);
	
	public int boardEval(BoardEvalVO param);
	public int modBoardLikeOrDisLike(int eval);
	public int getCurrentBrdEval(Map<String, Integer> map);
}
