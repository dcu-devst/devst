package kr.co.devst.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.devst.dao.BoardDao;
import kr.co.devst.model.BoardEvalVO;
import kr.co.devst.model.BoardVO;
import kr.co.devst.model.CommentVO;


@Service
public class BoardServiceImple implements BoardService{
	@Autowired
	private BoardDao boardDao;
		
	
	
	@Override
	public int doWrite(Map<String, String> map) {
		System.out.println("boardService");
		return boardDao.doWrite(map);
	}



	@Override
	public List<BoardVO> getBoardListAll() {
		return boardDao.getBoardListAll();
	}



	@Override
	public List<Map<String, String>> getBoardStudyList(int start, int num) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		list = boardDao.getBoardStudyList(start, num);
		for(int i=0;i<list.size();i++) {
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
			String date = transFormat.format(list.get(i).get("brd_update_date"));
			list.get(i).put("brd_update_date", date);

		}
				
		
		return list;
	}



	@Override
	public List<Map<String, String>> getBoardNomalList(int start, int num) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		list = boardDao.getBoardNomalList(start, num);
	
		for(int i=0;i<list.size();i++) {
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
			String date = transFormat.format(list.get(i).get("brd_update_date"));
			list.get(i).put("brd_update_date", date);

		}
				
		
		return list;
	}
	
	



	
	@Transactional
	@Override
	public HashMap<String, String> getBoardOneInfo(BoardVO param) {
		
		boardDao.boardUpHits(param.getBrdId());	//조회수 증가 작업
		return boardDao.getBoardOneInfo(param);	//해당 게시물 select 작업
	}



	@Override
	public List<Map<String, String>> getMainBoardList10(String category) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		list = boardDao.getMainBoardList10(category);
	
		for(int i=0;i<list.size();i++) {
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
			String date = transFormat.format(list.get(i).get("brd_update_date"));
			list.get(i).put("brd_update_date", date);

		}
		
		
		return list;
	}



	@Override
	public int boardModify(BoardVO param) {

		return boardDao.boardModify(param);
	}



	@Override
	public int boardUpHits(int brdId) {

		return boardDao.boardUpHits(brdId);
	}



	@Override
	public int boardMaxPageNum(String category) {
		return boardDao.boardMaxPageNum(category);
	}



	@Override
	public int dowriteBrdComment(CommentVO param) {
		return boardDao.dowriteBrdComment(param);
	}



	@Override
	public List<Map<String, String>> getBrdComment5(int start, int num, int brdNum) {
		return boardDao.getBrdComment5(start, num, brdNum);
	}


	
	@Transactional
	@Override
	public int boardEval(BoardEvalVO param) {
		
			
			int result1 = boardDao.boardEval(param);
			int result2 = boardDao.modBoardLikeOrDisLike(param.getEvalCount());
			
		return result1 * result2;
	}

	
	@Override
	public int modBoardLikeOrDisLike(int eval) {
		return boardDao.modBoardLikeOrDisLike(eval);
	}



	@Override
	public int getCurrentBrdEval(Map<String, Integer> map) {
		return boardDao.getCurrentBrdEval(map);
	}

}
