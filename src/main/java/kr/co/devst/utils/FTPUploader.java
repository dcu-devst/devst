package kr.co.devst.utils;





import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;





public class FTPUploader {
  
  
	  FTPClient ftp = null;
	    
	    //param( host server ip, username, password )
	    public FTPUploader(String host, String user, String pwd) throws Exception{
	        ftp = new FTPClient();
	        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
	        int reply;
	        ftp.connect(host);//호스트 연결
	        reply = ftp.getReplyCode();
	        if (!FTPReply.isPositiveCompletion(reply)) {
	            ftp.disconnect();
	            throw new Exception("Exception in connecting to FTP Server");
	        }
	        
	        ftp.login(user, pwd);//로그인
	        ftp.setFileType(FTP.BINARY_FILE_TYPE);
	        ftp.enterLocalPassiveMode();
	    }
	    //param( 보낼파일경로+파일명, 호스트에서 받을 파일 이름, 호스트 디렉토리, 저장할 폴더명, 저장할 userId(pk) )
	    public void uploadFile(String localFileFullName, String fileName, String hostDir,String category, String depth) throws Exception {
	        try(InputStream input = new FileInputStream(new File(localFileFullName))){
	        	if(category != null && depth != null) {
	        		this.ftp.storeFile(hostDir+"/"+category+"/"+depth+fileName, input);
	        	}
	        	if(depth == null) {
	        		this.ftp.storeFile(hostDir+"/"+category+"/"+fileName, input);
	        	}
	        	if(category == null && depth == null) {
	        		this.ftp.storeFile(hostDir+"/"+fileName, input);
	        	}
	        	
		        //storeFile() 메소드가 전송하는 메소드
	        } catch (Exception e) {
				e.printStackTrace();
				System.out.println("여기문제");
			}
	    }
	    public void uploadFile(String localFileFullName, String fileName, String hostDir) throws Exception{
	    	uploadFile(localFileFullName,  fileName,  hostDir, null, null);
	    }
	 
	    public void disconnect(){
	        if (this.ftp.isConnected()) {
	            try {
	                this.ftp.logout();
	                this.ftp.disconnect();
	                
	            } catch (IOException f) {
	                f.printStackTrace();
	            }
	        }
	    }
	    public static void main(String[] args) throws Exception {
	        System.out.println("Start");
	        //FTPUploader ftpUploader = new FTPUploader("34.64.185.43", "root", "dltmdgns1!");
	        FTPUploader ftpUploader = new FTPUploader("34.64.185.43", "user", "user");
	        //ftpUploader.uploadFile("C:/Users/img.jpg", "123.jpg", "/home/user", "profile", "13");
	        ftpUploader.uploadFile("C:/Users/img.jpg", "123.jpg", "/home/user");
	        ftpUploader.disconnect();
	        System.out.println("Done");
	    }



}