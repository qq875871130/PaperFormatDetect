package red.ant.action;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import red.ant.service.StudentService;
import red.ant.service.TeacherService;
import red.ant.util.WebLogger;

import com.opensymphony.xwork2.ActionSupport;

/**
 * �Զ������ʼ�
 * @author �����
 *
 */
public class ResetAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private String username;//ѧ��
	
	private TeacherService teacherService;
	private StudentService studentService;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	
	 public  void myexecute() throws Exception {
		 WebLogger webLogger=WebLogger.getLogger();
		 HttpServletRequest request=ServletActionContext.getRequest();
			String user_ip=getIpAddr(request);
		   //�����ʼ�����      
		   Properties prop = new Properties();
		   prop.setProperty("mail.host", "smtp.sohu.com");
		   prop.setProperty("mail.transport.protocol", "smtp");
		   prop.setProperty("mail.smtp.auth", "true");
		   //ʹ��JavaMail�����ʼ���5������
		   //1������session
		   Session session = Session.getInstance(prop);
		   //����Session��debugģʽ�������Ϳ��Բ鿴��������Email������״̬
		    session.setDebug(true);
		   //2��ͨ��session�õ�transport����
		    Transport ts = session.getTransport();
		   //3��ʹ��������û��������������ʼ��������������ʼ�ʱ����������Ҫ�ύ������û����������smtp���������û��������붼ͨ����֤֮����ܹ����������ʼ����ռ��ˡ�
		    ts.connect("smtp.sohu.com","redAntC110","asdfasdf1234");
		    //4�������ʼ�
		    Message message = createSimpleMail(session);
		     //5�������ʼ�
		     ts.sendMessage(message, message.getAllRecipients());
		     ts.close();
		     webLogger.log_All(user_ip,username, "������֤��������");
	}
	 
	public  MimeMessage createSimpleMail(Session session) throws Exception {
		
		
		//�û�����
		String userEmail="";
		String identity_number=get_identity();
		if(username.equals("21512345")==true)
		{
			//��ȡ�û�����
			userEmail=teacherService.getEmail(username);
			//��������ɵ���֤��洢�����ݿ�
			teacherService.setIdentity(username, identity_number);
		}
		else
		{
			//��ȡ�û�����
			userEmail=studentService.getEmail(username);
			//��������ɵ���֤��洢�����ݿ�
			studentService.setIdentity(username, identity_number);
		}
		//�����ʼ�����
		MimeMessage message = new MimeMessage(session);
		//ָ���ʼ��ķ�����
		message.setFrom(new InternetAddress("redAntC110@sohu.com"));
		//ָ���ʼ����ռ��ˣ����ݿ��ȡ�û�����
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
		//�ʼ��ı���
		message.setSubject("���ļ��ϵͳ����������");
		//�ʼ����ı�����
		//message.setContent("Ҫʹ���µ����룬�뽫�����ַ�������֤���ڣ������������Ĳ���<br/>"+identity_number,"text/html;charset=UTF-8");
		message.setContent("Ҫʹ���µ����룬�뽫�����ַ�������֤���ڣ������������Ĳ���<br/><br/>"+identity_number+"<br/><br/>"+"<a href=\"http://210.30.97.53:8090/PaperDetect/reset.jsp\">������ӽ��������������</a>","text/html;charset=UTF-8");
		//���ش����õ��ʼ�����
		return message;
	}
	/**
	 * ������λ���ֺ���ĸ��������
	 * @return
	 */
	public String get_identity()
	{
	     String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",  
	                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",  
	                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
	                "W", "X", "Y", "Z" };  
	        List list = Arrays.asList(beforeShuffle);  
	        Collections.shuffle(list); //����ԭ����˳�� 
	        StringBuilder sb = new StringBuilder();  
	        for (int i = 0; i < list.size(); i++) {  
	            sb.append(list.get(i));  
	        }  
	        String afterShuffle = sb.toString();  
	        String result = afterShuffle.substring(5, 9);  
	        return result;  

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
