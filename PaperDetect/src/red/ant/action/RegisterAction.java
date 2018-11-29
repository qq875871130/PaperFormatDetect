package red.ant.action;

import java.net.URLDecoder;
import java.security.MessageDigest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

import red.ant.service.StudentService;
import red.ant.util.WebLogger;
import sun.misc.BASE64Encoder;
/**
 * ע��
 * @author Administrator
 *
 */
public class RegisterAction extends ActionSupport{
	//�û����������
	private String sex;//�Ա�
	private String username;//ѧ��
	private String name;//����
	private String grade;//�༶
	private String password;//����
	private String email;//�û�����
	//private AdminService adminService;
	private StudentService studentService;
	//�������Զ�Ӧ��get��set����
	public String getSex()
	{
		return sex;
	}
	public void setSex(String sex)
	{
		this.sex=sex;
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username=username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password=password;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getGrade()
	{
		return grade;
	}
	public void setGrade(String grade)
	{
		this.grade=grade;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
	public StudentService getStudentService() {
		return studentService;
	}
	public void myexecute() throws Exception {	
			WebLogger webLogger = WebLogger.getLogger();
			HttpServletRequest request=ServletActionContext.getRequest();
			String user_ip=getIpAddr(request);
			if(studentService.IsHas(username).equals("1")==true)
			{
				//�û��Ѵ���
				ServletActionContext.getResponse().getWriter().println("-1");
			}
			else
			{
				name=URLDecoder.decode(name,"utf-8");
				grade=URLDecoder.decode(grade,"utf-8");
				String passwordMD5="";
				try
				{
					MessageDigest md5=MessageDigest.getInstance("MD5");
					BASE64Encoder base64en = new BASE64Encoder();
					//���ܺ���ַ���
					passwordMD5=base64en.encode(md5.digest(password.getBytes("utf-8")));
				}
				catch(Exception e)
	     		{
	     			 e.printStackTrace();
	     		}
				HttpSession session=ServletActionContext.getRequest().getSession();
				session.setAttribute("username", username);
				studentService.save(username,passwordMD5,grade,name,sex,email);
				//�ɹ�ע��
				System.out.println("ע��ɹ�---");
				webLogger.log_All(user_ip, username, "ע��");
				ServletActionContext.getResponse().getWriter().println("1");
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

