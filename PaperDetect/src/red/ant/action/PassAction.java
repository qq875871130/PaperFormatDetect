package red.ant.action;

import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * ��֤�����Ƿ������ֺ���ĸ�����
 * @author Administrator
 *
 */
public class PassAction extends ActionSupport{

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void myexecute() throws Exception
	{
		int flag=0;
		for(int i=0;i<password.length();i++)
		{
			char ch=password.charAt(i);
			if((ch>='0' && ch<='9') || (ch>='A' && ch<='Z') || (ch>='a' && ch<='z'))
			{
				continue;
			}
			else
			{
				flag=1;//����ĸ����
				break;
			}
		}
		if(flag==1)
			ServletActionContext.getResponse().getWriter().println("1");
		else
			ServletActionContext.getResponse().getWriter().println("0");
	}
}
