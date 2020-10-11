package kr.co.devst.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.devst.model.BoardVO;
import kr.co.devst.model.CommentVO;
import kr.co.devst.model.UserVO;
import kr.co.devst.service.BoardService;
import kr.co.devst.utils.Utils;
import net.sf.json.JSONObject;

@Controller
public class BoardController {
	
	private static final Log log = LogFactory.getLog(BoardController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "/devst", method = RequestMethod.GET)
	public String goIdx(Model model) {
		log.debug("********* 인덱스 페이지 *********");
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> param = new HashMap<String, String>();
		String category = "날짜순";
		
		list = boardService.getMainBoardList10(category);		
		
		model.addAttribute("boardList",list);
		
		
		
		return "/index.tilesAll";
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/devst", method = RequestMethod.POST, produces ="application/text; charset=utf8" )
	public String goIdxAjax(@RequestParam(value = "category", required = false, defaultValue = "날짜순")String category ) {//navigation으로 카테고리르 바꿀때 카드레아웃을 바꿈
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		System.out.println("category : "+category);
		list = boardService.getMainBoardList10(category);
		
		JSONObject jsonData = new JSONObject();
	
		
		jsonData.put("jsonData", list);
		
		
		
		System.out.println(jsonData.toString());
		
		return jsonData.toString();
	}
	
	
	
	
	@RequestMapping(value = "/devst/board/regmod", method = RequestMethod.GET)
	public String goBoardRegMod(Model model) {
		log.debug("********* 게시판 작성 페이지  *********");
		return "/user/board/regMod.tilesAll";
	}
	
	@RequestMapping(value = "/devst/board/regmod",headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	public void doBoardRegMod(Model model, HttpServletRequest request,@Valid BoardVO param, BindingResult bindReulst ,HttpServletResponse response, @RequestParam(value = "multiFile")MultipartFile multiFile, Principal principal, Authentication authentication) throws Exception{
		log.debug("********* 게시판 작성 @@실행@@  *********");
		RequestDispatcher rd =  request.getRequestDispatcher("/WEB-INF/views/user/board/regMod.jsp");
		UserVO loginUser = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(bindReulst.hasErrors() || loginUser == null) {//클라이언트 전송에러 or 비로그인자가 글 쓰기한경우
			List<ObjectError> list =  bindReulst.getAllErrors();
			String msg = "글 쓰기에 실패했습니다";
            for(ObjectError e : list) {
            	msg+="\n"+e.getDefaultMessage();
                 System.out.println("error : "+e.getDefaultMessage());
                 System.out.println(loginUser);
                 System.out.println("vo : "+param.getMemId());
            } 
			
			request.setAttribute("msg", msg);
			rd.forward(request, response);
			return;
		}
		
		System.out.println("여기지남");
		
		String dbFileNm =  Utils.uploadFile(multiFile, request,Utils.parseToStr(loginUser.getMemId(),"15"));//에러시 15관리자 폴더에 저장
		param.setBrdImage(dbFileNm);
		
		
		Map<String,String> map = new HashMap<String, String>();
		map = Utils.ObjToMap(param);
		log.debug("로그인정보 :::::"+principal.getName());	
	
		
		

		
		
		map.put("memId", Utils.parseToStr(loginUser.getMemId(),"15"));
			
		
		int result = boardService.doWrite(map);
		if(result != 1) {//글쓰기 실패 db에러
			model.addAttribute("msg","글 쓰기에 실패했습니다.");
			request.setAttribute("msg", "글 쓰기에 실패했습니다.");
			rd.forward(request, response);
			return;
		}
		response.sendRedirect("/devst/");
	}
	
	//게시물 수정
	@RequestMapping(value = "/devst/board/detail/update", method = RequestMethod.POST) 
	public void goBoardModify(HttpServletRequest request, HttpServletResponse response, BoardVO vo) throws IOException, ServletException {
		String id = request.getParameter("id");//게시물번호
		String no = request.getParameter("no");//게시물 카테고리
		log.debug("게시물번호id :::: "+id);
		log.debug("카테고리no :::: "+no);
		log.debug("게시물 정보 카테고리:::: "+vo.getBrdCategory());
		log.debug("게시물 정보 내용:::: "+vo.getBrdContent());
		log.debug("게시물 정보 제목:::: "+vo.getBrdTitle());
		UserVO loginUser = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		vo.setMemId(loginUser.getMemId());
		vo.setBrdId(Utils.parseToInt(id, 0));
		
		
		if(id == null || no == null || ("1").equals(no) && ("2").equals(no)) {//비정상적인 경로로 접근 한 경우
			response.sendRedirect("/");
			return;
		}
		
		
		int result = boardService.boardModify(vo);
		if(result != 1) {//수정실패
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.print("<script>");
			writer.print("alert('수정에 실패했습니다.');");
			writer.print("history.back()");
			writer.print("</script>");
			return;
		}
		
		response.sendRedirect("/devst/board/detail/category?id="+id+"&no="+no);
		
		return;
		
		
		
	}
	
	@RequestMapping(value = "/devst/board/category", method = RequestMethod.GET)
	public String goBoardShow(Model model,@RequestParam(value = "pageNum",required = false, defaultValue = "1")int pageNum,
											@RequestParam(value = "no", required = false, defaultValue = "0")int no, RedirectAttributes rtta) {
		log.debug("********* 게시판 각각 세부사항 페이지  *********");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>(); //어떤 게시물을 담을 그릇
		String category = null;
		Integer pageMaxNum = null;
		String currentTime = Utils.getCurrentDate("yyyyMMdd");
		System.out.println("pageNum : "+pageNum);
		
		
			switch(no) {
			case 0://잘못된 접근 메인으로
				return "redirect:/devst/";
				
			case 1://일반게시판
				list = boardService.getBoardNomalList((pageNum-1)*10, 10);
				pageMaxNum = boardService.boardMaxPageNum("일반");
				
				category = "일반게시판";
				break;
			case 2://스터디게시판
				
				list = boardService.getBoardStudyList((pageNum-1)*10, 10);
				pageMaxNum = boardService.boardMaxPageNum("스터디구인");
				category = "스터디게시판";
				break;
			case 3://???
				break;
			case 4://???
				break;
			default:
				return "redirect:/devst/";
		}
			
			//없는페이지 접근시 예외처리 지금은 다른작업때문에 주석중
//			if(pageNum>pageMaxNum.intValue()) {
//				System.out.println("pageNum : "+pageNum);
//				System.out.println("pageMaxNum : "+pageMaxNum);
//				rtta.addFlashAttribute("error","잘못된 접근입니다.");
//				return "redirect:/devst/board/category?no="+no;
//			}
			
		model.addAttribute("pageNum",pageNum);//현재 페이지쪽수(시작이 0)	
		model.addAttribute("no",no);//URL에 들어가는 카테고리 
		model.addAttribute("category",category);//게시판에 들어갈 카테고리명
		model.addAttribute("pageMaxNum",pageMaxNum);//최대 페이지 수
		model.addAttribute("list",list);
		model.addAttribute("currentTime",currentTime);
		return "/user/board/board.tilesAll";
	}
	
	
		
	//작업중 삭제 ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ 안돼!!
	
	  @RequestMapping(value = "/devst/board/detail/category", method = RequestMethod.GET)
	  public String goBoardDetail(Model model, @RequestParam(value = "id", required = false, defaultValue = "0")int id,
			  									@RequestParam(value = "no", required = false, defaultValue = "0")int no ){
		  log.debug("no :::::: "+no);
		  	BoardVO param = new BoardVO();
		  
		  if(id == 0 || no == 0) {//올바르진 않은 접근
			  return "/user/board/board"; 
		  }
		  param = new BoardVO(); param.setBrdId(id);
		  param.setBrdCategory(Utils.MappingCategory(no));
		  
		  //param.setBrdId(1000);//게시물 번호가 1000번인 게시물은 없다 작업실패
		  
		  
		  HashMap<String, String> boardOneInfoMap = boardService.getBoardOneInfo(param);
		  List<Map<String, String>> commentList = boardService.getBrdComment5(0, 5,id);
		  if(boardOneInfoMap == null) {//잘못된 접근 
			  return "/"; 
		  } 
		  model.addAttribute("oneInfo",boardOneInfoMap);
		  model.addAttribute("commentList", commentList);
		  
	  
	  
		  return "/user/board/regMod.tilesAll"; 
	  }
	  
	  @RequestMapping(value = "/certified")
	  public String goCertified() {
		  return "/user/certified";
	  }
	 
	//댓글
	  @ResponseBody
	  @RequestMapping(value = "/devst/user/board/comment", method = RequestMethod.POST, produces ="application/text; charset=utf8")
	  public String doWriteBrdContent(@Valid CommentVO commentVO, BindingResult bindReulst) {
		  	
		  if(bindReulst.hasErrors() || commentVO.getBrdId() == 0 || commentVO.getMemId() == 0) {//클라이언트 전송에러
				List<ObjectError> list =  bindReulst.getAllErrors();
				String msg = "글 쓰기에 실패했습니다";
	            for(ObjectError e : list) {
	            	msg+="\n"+e.getDefaultMessage();
	                 System.out.println("error : "+e.getDefaultMessage());   
	            }
	            return "error";
			}
		  log.debug("valid방식::: "+commentVO.getBrdId());
		  log.debug("valid방식::: "+commentVO.getMemId());
		  log.debug("valid방식::: "+commentVO.getContent());
		  int result = boardService.dowriteBrdComment(commentVO);
		  System.out.println("result : "+result);
		  
		  JSONObject json = new JSONObject();
		  json.put("jsonData", commentVO);
		  
		  return json.toString();
		  
	  }
	
}
