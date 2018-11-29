package red.ant.action;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import red.ant.po.Paper;
import red.ant.service.PaperService;
import red.ant.util.JsonUtil;

import com.opensymphony.xwork2.ActionSupport;
/**
 * myexecute(����pid���ؼ�ⱨ��)��myexecute2(ֱ�ӷ��ر��û����µļ�ⱨ��)��ѧ�����棩
 * @author �����
 *
 */
public class ReportAction extends ActionSupport{

	private String pid=ServletActionContext.getRequest().getParameter("pId");
	private PaperService paperService;
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public PaperService getPaperService() {
		return paperService;
	}

	public void setPaperService(PaperService paperService) {
		this.paperService = paperService;
	}
	/**
	 * ���µļ�鱨��
	 * @throws Exception
	 */
	public void myexecute2() throws Exception
	{
		HttpSession session=ServletActionContext.getRequest().getSession();
		String username=(String)session.getAttribute("username");
		String name=(String)session.getAttribute("name");
		List<Paper> paperlist=paperService.findByUsername(username);
		int i,flag=0;//��ʶ�����µļ�ⱨ��
		int max=0;
		for(i=0;i<paperlist.size();i++)
		{
			if(Integer.parseInt(paperlist.get(i).getPid())>max)
			{
				max=Integer.parseInt(paperlist.get(i).getPid());
				flag=i;
			}
		}
		String newp_id=paperlist.get(flag).getPid();
		String newpaper_id=paperlist.get(flag).getPaper_id();
		String relativePath=paperService.getPath(newp_id);//����paper_idѰ�����·��
		//���ɵı���·��
		File tempfile = new File("."); 
		String realPath = tempfile.getAbsoluteFile().getParent() + relativePath;
		realPath=("D:\\PaperFormatDetection\\Reports\\"+newpaper_id).replaceAll(".docx", ".txt").replaceAll(".doc",".txt");
		System.out.println("rrrrrrrrrrr    "+realPath);
		String s,str;
		List<String> list=new ArrayList<String>();
		//ֻ��ʾ��������,list[1]Ϊ��##���ġ���ⱨ��
		//list[2]Ϊʱ������Ҷ���
		//��һ�о���,�ڶ����ֺ�СһЩ
		//��������б�߶�����ת��ԭ����˼��ǰ������б��ת�嵽ǰ��ֻʣһ����б�ߣ�ǰ�˶�ȡʱ��Ҫ�ٶ����Ž���һ��ת��
		list.add(newpaper_id);
		int startIndex=name.length()+username.length()+2;
		String data2=newpaper_id.substring(startIndex,newpaper_id.length()-25);
		list.add("\\\""+""+data2+"\\\""+" ��ⱨ��");
		String[] data=newpaper_id.split("_");
		int length=data.length;
		list.add(data[length-1].substring(0, data[length-1].length()-5));
		BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(realPath),"UTF-8"));
		int j;
		if(in!=null)
		{
			while((s=in.readLine())!=null)
			{
				for(j=0;j<s.length();j++)
				{
					if(s.charAt(j)=='"'|| s.charAt(j)=='\''|| s.charAt(j)=='\\')
					{
						str=s.substring(0, j)+"\\"+s.substring(j);
						j++;//Դ�ַ�������һλ�ַ�
						s=str;//s���¸�ֵ
					}
					s=s.replaceAll("<", "&lt;");
					s=s.replaceAll(">", "&gt;");
				}
				list.add(s);
			}
			in.close();
			System.out.println("listΪ��"+list);
			String sb="{\"report\":"+JsonUtil.listToJson(list)+"}";
			HttpServletResponse response =  ServletActionContext.getResponse();
			response.setCharacterEncoding("GBK");
			response.setContentType("text/html;charset=GBK;");
			//��ǰ�û����µļ�ⱨ��
			response.getWriter().println(sb);
		}
	}
	/**
	 * ѧ����Ӧpid�ļ�鱨��
	 * @throws Exception
	 */
	public void myexecute() throws Exception {
		// TODO Auto-generated method stub
		HttpSession session=ServletActionContext.getRequest().getSession();
		String username=(String)session.getAttribute("username");
		String name=(String)session.getAttribute("name");
		System.out.println("pid====="+pid);
		String relativePath=paperService.getPath(pid);//���·��
		String paper_id=paperService.getPaper_id(pid);
		//���ɵı���·��
		File tempfile = new File("."); 
		String realPath = tempfile.getAbsoluteFile().getParent() + relativePath;
		
		String s,str;
		List<String> list=new ArrayList<String>();
		//��paper_id��������
		System.out.println(paper_id);
		realPath=("D:\\PaperFormatDetection\\Reports\\"+paper_id).replaceAll(".docx", ".txt").replaceAll(".doc",".txt");
		list.add(paper_id);
		int startIndex=name.length()+username.length()+2;
		String data2=paper_id.substring(startIndex,paper_id.length()-25);
		list.add("\\\""+""+data2+"\\\""+" ��ⱨ��");
		String[] data=paper_id.split("_");
		int length=data.length;
		list.add(data[length-1].substring(0, data[length-1].length()-5));
		BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(realPath),"UTF-8"));
		int i;
		if(in!=null)
		{
			while((s=in.readLine())!=null)
			{
				for(i=0;i<s.length();i++)
				{
					if(s.charAt(i)=='"'|| s.charAt(i)=='\''|| s.charAt(i)=='\\')
					{
						str=s.substring(0, i)+"\\"+s.substring(i);
						i++;//Դ�ַ�������һλ�ַ�
						s=str;//s���¸�ֵ
					}
					s=s.replaceAll("<", "&lt;");
					s=s.replaceAll(">", "&gt;");
				}
				list.add(s);
			}
			in.close();
			String sb="{\"report\":"+JsonUtil.listToJson(list)+"}";
			HttpServletResponse response =  ServletActionContext.getResponse();
			response.setCharacterEncoding("GBK");
			response.setContentType("text/html;charset=GBK;");
			response.getWriter().println(sb);
		}
	}
}
