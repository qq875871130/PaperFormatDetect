package red.ant.util;
//��������û�õ�
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;

import red.ant.service.StudentService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorityInterceptor extends AbstractInterceptor {
    private StudentService studentService;


	public StudentService getStudentService() {
		return studentService;
	}
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
	
	public String intercept(ActionInvocation arg0) throws Exception {
		 System.out.println("���أ���������������������");
		//ActionContext act=arg0.getInvocationContext();//ȡ��������ص�ActionContextʵ��
		Map<String,?> session=arg0.getInvocationContext().getSession();
		String username=(String) session.get("username");
		System.out.println("!!!!!!"+username+"-------");
		if(username!=null)
			return arg0.invoke();
		else{arg0.getInvocationContext().put("tip","�㻹û�е�¼��");
		return Action.LOGIN;
		}
}
}