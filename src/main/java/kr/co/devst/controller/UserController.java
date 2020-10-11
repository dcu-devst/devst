package kr.co.devst.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.devst.model.UserVO;
import kr.co.devst.service.MailService;
import kr.co.devst.service.UserService;
import kr.co.devst.utils.Utils;

@Controller
@RequestMapping("/devst/user/*")
public class UserController {
	
	private static final Log log = LogFactory.getLog(UserController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private MailService mailService;
	
	// 이메일 인증페이지 이동
	@RequestMapping(value = "/emailCertifyPage", method = RequestMethod.GET)
	public String goEmailCertify(Model model) {
		log.debug("********** 이메일 인증 페이지 **********");
		
		return "/user/emailCertify";
	}
	
	// 이메일 인증번호 전송
	@RequestMapping(value = "/emailSend", method = RequestMethod.POST)
	@ResponseBody
	public int doEmailCertify(HttpServletRequest request, String memEmail) throws Exception {
		log.debug("********** 이메일 인증번호 전송  @@실행@@ **********");
		
		int result = 0;
		
		// 이메일 중복체크
		result = userService.emailChk(memEmail);
		if(result == 1) return result;
		
		HttpSession session = request.getSession();
		mailService.emailSend(session, memEmail);
		 
	
		return result;
	}

	// 인증번호 확인
	@RequestMapping(value = "/emailCertify", method = RequestMethod.POST)
	@ResponseBody
	public boolean doEamilCertify(HttpServletRequest request, String memEmail, String inputNum) throws Exception {
		log.debug("********** 인증번호 확인  @@실행@@ **********");
		System.out.println(memEmail + " " + inputNum);
		HttpSession session = request.getSession();
		boolean result = mailService.emailCertify(session, memEmail, Integer.parseInt(inputNum));

		return result;
	}
	
	// 회원가입 페이지 이동
	@RequestMapping(value = "/joinPage", method = RequestMethod.GET)
	public String goJoin(Model model, @RequestParam (value = "error", required = false, defaultValue = "1")int error, String memEmail) {
		log.debug("********** 회원가입 페이지 **********");
		if(error != 1) {
			model.addAttribute("msg","알수없는 에러발생 잠시후 다시시도해주세요.");
		}
		model.addAttribute("memEmail", memEmail);
		return "/user/join";
	}
	
	// 닉네임 중복확인
	@RequestMapping(value = "/nickNameChk", method = RequestMethod.POST)
	@ResponseBody
	public int doNickNameChk(String memNickName) throws Exception {
		int result = userService.nickNameChk(memNickName);
		
		return result;
	}
	
	// 회원가입
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String doJoin(Model model, UserVO userVO, RedirectAttributes rttr) throws Exception {
		String resultAddr;
		log.debug("********** 회원가입  @@실행@@ **********");
		// 프로필 사진 여부 검사
//		if(userVO.getMem_profile_image().equals("") || userVO.getMem_profile_image() == null) {
//			userVO.setMem_profile_image("no_image");
//		}
		
		
		int result = userService.doJoin(userVO);
		System.out.println(result);
		if(result != 1) 
			resultAddr = "redirect:/devst/user/joinPage?error";
		else {
			rttr.addFlashAttribute("joinSuccessMsg","가입에 성공했습니다. 로그인해주세요.");
			resultAddr = "redirect:/devst/user/loginPage";
		}
		return resultAddr;
	}
	
	@RequestMapping(value = "/devst/user/info", method = RequestMethod.GET)
	public String goMyInfo(Model model,HttpServletRequest request) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if("anonymousUser".equals(principal)) {
			log.debug("****login정보 없음**** ");
			return "redirect:/";
		} 
		UserVO loginUser = (UserVO) principal;
		log.debug("profile : "+loginUser.getMemProfileImage());
		String path = request.getSession().getServletContext().getRealPath("uploadImg");
		path+="/profile/"+loginUser.getMemId();
		model.addAttribute("path",path);
		model.addAttribute("myInfo", loginUser);
		
		
		return "/user/myInfo/userInfo";
	}
	
	@RequestMapping(value = "/devst/user/info", method = RequestMethod.POST, headers = ("content-type=multipart/*"))
	public String modMyInfo(Model model, @RequestParam(value = "multiFile")MultipartFile multiFile, UserVO myInfo, HttpServletRequest request) {
		
		String dbFileNm =  Utils.uploadFile(multiFile,"profile", request,Utils.parseToStr(myInfo.getMemId(),"15"));//에러시 15관리자 폴더에 저장
		myInfo.setMemProfileImage(dbFileNm);
		
		int result = userService.modUserInfo(myInfo);
		log.debug("result : "+result);
		
		return "/user/myInfo/userInfo";
	}
	
}
