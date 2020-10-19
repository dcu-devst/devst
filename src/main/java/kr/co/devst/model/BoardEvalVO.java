package kr.co.devst.model;

public class BoardEvalVO {//게시판 평가 tb 
	private int evalId;
	private int memId;
	private int brdId;
	private int evalCount;
	
	
	
	
	public int getEvalCount() {
		return evalCount;
	}
	public void setEvalCount(int evalCount) {
		this.evalCount = evalCount;
	}
	public int getEvalId() {
		return evalId;
	}
	public void setEvalId(int evalId) {
		this.evalId = evalId;
	}
	public int getMemId() {
		return memId;
	}
	public void setMemId(int memId) {
		this.memId = memId;
	}
	public int getBrdId() {
		return brdId;
	}
	public void setBrdId(int brdId) {
		this.brdId = brdId;
	}
	
	
	
	

}
