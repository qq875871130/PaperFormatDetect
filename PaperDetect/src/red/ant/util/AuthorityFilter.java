//��������ʵ�ֳ�ע�������ȫ������
package red.ant.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthorityFilter implements Filter {
private FilterConfig config;
	public void destroy() {
		this.config=null;

	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	  HttpServletRequest requ=(HttpServletRequest) request;
	  //HttpServletResponse respon=(HttpServletResponse) response;
	  HttpSession session=requ.getSession();
	  //String requestpath=requ.getServletPath();
	  String loginpage=config.getInitParameter("loginpage");
	  String url=requ.getServletPath();
	  System.out.println("url="+url);
	  System.out.println("username="+session.getAttribute("username"));
	  //δ��¼�Ҳ���ע������Ҳ��������������----����¼����
	  if(session.getAttribute("username")==null  && url.equals("/register.jsp")==false && url.equals("/security_01.jsp")==false && url.equals("/reset.jsp")==false){
		  System.out.println("�˺�Ϊ�գ��Ҳ���ע������Ҳ��������������");
		 //requ.setAttribute("tip", "�㻹û�е�¼��");
		  request.getRequestDispatcher("/login.jsp").forward(request, response);
	  }
	  else
	  { 
		  System.out.println("�ǣ��˺�Ϊ�գ��Ҳ���ע������Ҳ�������������棩");
		  chain.doFilter(request, response);
	  }
		  /*********************************
		  System.out.println("-------------");
		
		  if(session.getAttribute("username")==null  && url.equals("/register.jsp")==true && url.equals("/security_01.jsp")==true)
		  {
			  System.out.println("--------ע�����������--------");
			  chain.doFilter(request, response);
		  }
		//ѧ����¼������ʦ�˺�
		   if(session.getAttribute("username")!=null && session.getAttribute("username").equals("201612345")==false &&url.equals("/teacher.jsp")==true )
		  {
			   System.out.println("---ѧ��ԽȨ---------");
			  request.getRequestDispatcher("/login.jsp").forward(request, response);
			  //request.getRequestDispatcher("/students.jsp").forward(request, response);
		  }
		   //��ʦ��¼����ѧ���˺�
		   else if(session.getAttribute("username").equals("201612345")==true && url.equals("/students.jsp")==true)
		  {
			   System.out.println("---��ʦԽȨ---------");
			  request.getRequestDispatcher("/login.jsp").forward(request, response);
			  //request.getRequestDispatcher("/teacher.jsp").forward(request, response);
		  }
		   else
		   {
			   System.out.println("---��һ����---------");
			   chain.doFilter(request, response);
		   }
		  
	  }
	  ******************************/
	  
	}

	public void init(FilterConfig arg0) throws ServletException {
		// System.out.println("���˳�ʼ������������������������");
		this.config=arg0;

	}
}
