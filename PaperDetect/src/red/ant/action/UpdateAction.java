package red.ant.action;

import java.security.MessageDigest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import red.ant.service.StudentService;
import red.ant.service.TeacherService;
import red.ant.util.WebLogger;
import sun.misc.BASE64Encoder;
/**
 * ��������
 * @author Administrator
 *
 */
public class UpdateAction extends ActionSupport{
	//�������ڷ�װ�û��������������
	private String password;
	private String newpassword;
//ϵͳ�����ҵ���߼����
	private StudentService studentService;
	private TeacherService teacherService;
//��ҵ���߼����ע������������
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	
	public StudentService getStudentService() {
		return studentService;
	}
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
	
	public TeacherService getTeacherService() {
		return teacherService;
	}
	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	public void myexecute() throws Exception {
		WebLogger webLogger=WebLogger.getLogger();
		HttpSession session=ServletActionContext.getRequest().getSession();
		String user_ip=(String)session.getAttribute("ip");
		String username=(String) session.getAttribute("username");
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
		String student_update=studentService.login(username, passwordMD5);
		String teacher_update=teacherService.login(username, passwordMD5);
		if(student_update==null && teacher_update==null)
		{
			ServletActionContext.getResponse().getWriter().println("0");//�������
		}
		else//������ȷ
		{
			String newpasswordMD5="";
			try
			{
				MessageDigest md5=MessageDigest.getInstance("MD5");
				BASE64Encoder base64en = new BASE64Encoder();
				//���ܺ���ַ���
				newpasswordMD5=base64en.encode(md5.digest(newpassword.getBytes("utf-8")));
			}
			catch(Exception e)
	 		{
	 			 e.printStackTrace();
	 		}
		
			 if(student_update!=null )//ѧ���˺�
			 {	 
				studentService.update(username, newpasswordMD5);
				//����ֵΪ1��ʾ��������ȷ
				webLogger.log_All(user_ip, username, "student","�޸�����ɹ�");
		    	 ServletActionContext.getResponse().getWriter().println("1");
		    }
			 if(teacher_update!=null)//��ʦ�˺�
			 {
				teacherService.update(username, newpasswordMD5);
				webLogger.log_All(user_ip,username,"teacher","�޸�����ɹ�");
				 ServletActionContext.getResponse().getWriter().println("1");
			 }
		}
	}
}
