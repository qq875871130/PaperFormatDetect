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
 * ��������
 * @author Administrator
 *
 */
public class ResetPasswordAction extends ActionSupport{
	private String username;//ѧ��
	private String identity;//��֤��
	private String password;//����
	
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
			MessageDigest md5=MessageDigest.getInstance("MD5");//MD5����
			BASE64Encoder base64en = new BASE64Encoder();//������ת����ת��Ϊ���Դ���ģ�������ʾ��
			//���ܺ���ַ���
			passwordMD5=base64en.encode(md5.digest(password.getBytes("utf-8")));
		}
		catch(Exception e)
 		{
 			 e.printStackTrace();
 		}
	if(username.equals("201612345")==true)
	{
		//��֤�Ƿ����
		flag=teacherService.resetPassword(username, identity, passwordMD5);
	}
	else
	{
		flag=studentService.resetPassword(username, identity, passwordMD5);
	}
		if(flag==null)
			//�������ô����û���������֤�����
			ServletActionContext.getResponse().getWriter().println("0");
		else
			//�������óɹ�
		{
			ServletActionContext.getResponse().getWriter().println("1");
			webLogger.log_All(user_ip, username, "������֤��������ɹ�");
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
