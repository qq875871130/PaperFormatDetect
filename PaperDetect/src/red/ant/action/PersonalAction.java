package red.ant.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import red.ant.po.Student;
import red.ant.service.StudentService;
import red.ant.util.JsonUtil;
/**
 * �������ģ�ѧ����
 * @author Jun
 *
 */
public class PersonalAction extends ActionSupport{

	private StudentService studentService;
	
	
	public StudentService getStudentService() {
		return studentService;
	}


	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}


	public void myexecute() throws Exception {
		// TODO Auto-generated method stub
	HttpSession session=ServletActionContext.getRequest().getSession();
	String username=(String)session.getAttribute("username");
	List<String> list=new ArrayList<String>();
	Student student=studentService.getAll(username);
	list.add(student.getName());
	list.add(student.getUsername());
	list.add(student.getGrade());
	list.add(student.getSex());
	String sb="{\"personal\":"+JsonUtil.listToJson(list)+"}";
	HttpServletResponse response =  ServletActionContext.getResponse();
	response.setCharacterEncoding("GBK");
	response.setContentType("text/html;charset=GBK;");
	//��ǰ�û����µļ�ⱨ��
	response.getWriter().println(sb);//��������Ϊ������ѧ�ţ��༶���Ա�ǰ̨����report������
	}
}
