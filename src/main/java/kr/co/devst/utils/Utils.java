package kr.co.devst.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.devst.model.BoardVO;

public class Utils {
	
	public static String getCurrentDate(String str) {//출력받을 날짜형태입력(ex yyyy-MM-dd, yyyy/MM/dd)
		SimpleDateFormat format = new SimpleDateFormat(str);
		String currentDate = null;
		try {
			currentDate = format.format(System.currentTimeMillis());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return currentDate;
	}
	
	
	public static String parseToStr(int num, String str) {
		String result = str;
		
		try {
			result = Integer.toString(num);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("파싱된 숫자는 : "+num);
		
		return result;
	}
	
	
	public static int parseToInt(String str, int num) {//문자열 "123"을 숫자로 파싱하는 유틸
		int result = num;
		
		
		try {
			int parse = Integer.parseInt(str);
			result = parse;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	//이미지, 파일경로, depth는 유저별 폴더를 분류하는경우
	/* How to use */
	//     1. String path = request.getSession().getServletContext().getRealPath("uploadImg");에 기본 업로드패스가 지정된다.
	//     2. 사용자는 parameter중 filePath와 depth만 신경쓰면된다.
	//	   3. filePath는 대분류를 나타낸다. ex)profile, product, 등등 
	//	   4. depth는 소분류(user_pk)를 나타낸다. ex)profile중 user_pk가 2인 사람의 이미지들
	//	   5. 다형성을 통해 대분류, 소분류가 각각 필요하거나 필요하지 않은경우를 null이 아닌 parameter 자체를 안받고 사용가능하게 함
	public static String uploadFile(MultipartFile multiFile, String filePath, HttpServletRequest request, String depth) {
		String fileName = multiFile.getOriginalFilename();//db저장될 파일명
		System.out.println("mulitFile : "+fileName);
		String path = request.getSession().getServletContext().getRealPath("/resources/uploadImg");
		if(filePath != null) {
			path= path+"/"+filePath;
		}
		if(depth != null){
			path= path+"/"+depth;
		}
		path+="/";	
		System.out.println("path : "+path);
		
		File file = new File(path);
		if(!file.exists()) {//없으면 생성
			System.out.println("경로 : "+path);
			file.mkdirs();
		}
		
		byte[] bytes;
		file = new File(path,multiFile.getOriginalFilename());
		System.out.println("fileName : "+multiFile.getOriginalFilename());
		
		
		
		try {
			bytes = multiFile.getBytes();
			FileCopyUtils.copy(bytes, file);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}
	
	//다형성으로 폴더세분화, 유저별폴더정리 정의
	public static String uploadFile(MultipartFile multiFile,  HttpServletRequest request) {
		 return uploadFile( multiFile, null,  request, null);
	}
	
	public static String uploadFile(MultipartFile multiFile, String filePath, HttpServletRequest request) {
		 return uploadFile( multiFile, filePath,  request, null);
	}
	public static String uploadFile(MultipartFile multiFile, HttpServletRequest request,String depth) {
		 return uploadFile( multiFile, null,  request, depth);
	}
	
	
	
	
	//object를 map형태로 변환
	public static Map ObjToMap(Object obj){
	    Map<String,Object> map = new HashMap<String, Object>();
	    Field[] fields = obj.getClass().getDeclaredFields();
	    for(int i=0; i <fields.length; i++){
	        fields[i].setAccessible(true);
	        try{
	            map.put(fields[i].getName(), fields[i].get(obj));
	           
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	      
	        System.out.println("map의 정보 : "+map.toString());
	    }
	    return map;
	}
	
	public static String MappingCategory(int num) {//case에 따른 카테고리 매핑
		
		
		String category = null;
		switch(num) {
			case 1:
				category = "일반";
				break;
			case 2:
				category = "스터디구인";
				break;
			case 3:
				break;
			case 4:
				break;
				
		}
		
		
		return category;
	}
	
	

	
}
