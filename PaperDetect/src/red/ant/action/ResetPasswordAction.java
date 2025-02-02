package red.ant.action;

import java.security.MessageDigest;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import red.ant.service.StudentService;
import red.ant.service.TeacherService;
import red.ant.util.WebLogger;
import sun.misc.BASE64Encoder;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 重置密码
 * @author Administrator
 *
 */
public class ResetPasswordAction extends ActionSupport{
	private String username;//学号
	private String identity;//验证码
	private String password;//密码
	
	private TeacherService teacherService;
	private StudentService studentService;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public TeacherService getTeacherService() {
		return teacherService;
	}
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	public StudentService getStudentService() {
		return studentService;
	}
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
	
	public void myexecute() throws Exception
	{
		WebLogger webLogger=WebLogger.getLogger();
		HttpServletRequest request=ServletActionContext.getRequest();
		String user_ip=getIpAddr(request);
		
		String flag="";
		String passwordMD5="";
		try
		{
			MessageDigest md5=MessageDigest.getInstance("MD5");//MD5加密
			BASE64Encoder base64en = new BASE64Encoder();//二进制转换，转化为可以传输的，可以显示的
			//加密后的字符串
			passwordMD5=base64en.encode(md5.digest(password.getBytes("utf-8")));
		}
		catch(Exception e)
 		{
 			 e.printStackTrace();
 		}
	if(username.equals("201612345")==true)
	{
		//验证是否存在
		flag=teacherService.resetPassword(username, identity, passwordMD5);
	}
	else
	{
		flag=studentService.resetPassword(username, identity, passwordMD5);
	}
		if(flag==null)
			//密码重置错误，用户名或者验证码错误
			ServletActionContext.getResponse().getWriter().println("0");
		else
			//密码重置成功
		{
			ServletActionContext.getResponse().getWriter().println("1");
			webLogger.log_All(user_ip, username, "邮箱验证重置密码成功");
		}
	}
	 public String getIpAddr(HttpServletRequest request) {     
	      String ip = request.getHeader("x-forwarded-for");     
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	         ip = request.getHeader("Proxy-Client-IP");     
	     }     
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	         ip = request.getHeader("WL-Proxy-Client-IP");     
	      }     
	     if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {     
	          ip = request.getRemoteAddr();     
	     }     
	     return ip;     
	}    
}
