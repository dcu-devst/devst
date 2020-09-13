package kr.co.devst.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.devst.dao.BoardDao;
import kr.co.devst.model.BoardVO;


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
		return boardDao.getBoardStudyList(start, num);
	}



	@Override
	public List<Map<String, String>> getBoardNomalList(int start, int num) {
		return boardDao.getBoardNomalList(start, num);
	}
	
	@Override
	public int getPageNum(String category) {
		
		return boardDao.getPageNum(category);
	}

}
