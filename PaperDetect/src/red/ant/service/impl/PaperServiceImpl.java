package red.ant.service.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import red.ant.dao.PaperDao;
import red.ant.po.Paper;
import red.ant.service.PaperService;

public class PaperServiceImpl implements PaperService {
    private PaperDao paperDao;
    
	public PaperDao getPaperDao() {
		return paperDao;
	}

	public void setPaperDao(PaperDao paperDao) {
		this.paperDao = paperDao;
	}

	public String save( String paper_path,String sid,String name,String paper_id) {
		// TODO Auto-generated method stub
		String pid;
		List<Paper> list=paperDao.findAll();
		int i;
		int max=0;
		if(list!=null && list.size()!=0)
		{
			for(i=0;i<list.size();i++)
			{
				if(Integer.parseInt(list.get(i).getPid())>max)
					max=Integer.parseInt(list.get(i).getPid());
			}
			//pid=list.get(list.size()-1).getPid();//��ȡ���һ����pid
			pid=max+1+"";//pid+1;
		}
		else
		{
			pid="0";//���ı�Ŵ�0��ʼ
		}
		System.out.println(pid+paper_path+sid);
		//������

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		String ptime=df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		System.out.println("����save���ڣ�������������");
		System.out.println(ptime+name+paper_id);
		Paper paper=new Paper(pid,paper_path,ptime,sid,name,paper_id);
		System.out.println("save��ɣ�������");
		paperDao.save(paper);
		return pid;//����ɹ���������
	}

	public void update(String username, String password) {
		// TODO Auto-generated method stub
		
	}
	public List<Paper> findByUsername(String username)
	{
		List<Paper> list=paperDao.findByUsername(username);
		return list;
	}
	//����pid��ȡ��������·��
	public String getPath(String pid)
	{
		String paper_id=paperDao.get(pid).getPaper_id();
		int i;
		String str="";
		/**
		for(i=0;i<paper_id.length();i++)
		{
			if(paper_id.charAt(i)=='.')
				break;
		}
		str=paper_id.substring(0, i);
		**/
		str=paper_id.substring(0, paper_id.length()-5);
		return "\\Papers\\"+str+"\\report.txt";
	}
	//����id��ȡ��������ĵ�·��
	public String getTestPath(String pid)
	{
		return paperDao.get(pid).getPaper_path();
	}
	public String getPaper_id(String pid)
	{
		return paperDao.get(pid).getPaper_id();
	}

	public List<Paper> show() {
		// TODO Auto-generated method stub
		List<Paper> list=paperDao.findAll();
		if(list!=null && list.size()!=0)
		{
			return list;
		}
		return null;
	}
	public Integer Sum()
	{
		List<Paper> list=show();
		if(list!=null && list.size()!=0)
		{
			return list.size();
		}
		else
			return 0;
	}

	public List<Paper> fidByName(String name) {
		// TODO Auto-generated method stub
		List<Paper> list=paperDao.findByName(name);
		return list;
	}

	public List<Paper> findByPaperName(String paperName) {
		// TODO Auto-generated method stub
		List<Paper> list=paperDao.findByPaperName(paperName);
		return list;
	}

	public List<Paper> findByPtime(String ptime) {
		// TODO Auto-generated method stub
		List<Paper> list=paperDao.findByPtime(ptime);
		return list;
	}

	public void delete(String pid) {
		// TODO Auto-generated method stub
		String[] data=pid.split(" ");
		Paper paper=new Paper();
		String paperPath="";
		String reportPath="";
		for(String str:data)
		{
			System.out.println("pidΪ��"+str);
			paper=paperDao.get(str);
			paperPath=paper.getPaper_path();
			File paperFile=new File(paperPath);
			if(paperFile.exists())
			{
				//ɾ������
				paperFile.delete();
			}
			File tempFile = new File("Papers"); 
			String paperId=paper.getPaper_id();
			//��ȡ���������ļ���
			String paperReport=paperId.substring(0, paperId.length()-5);
			reportPath = tempFile.getAbsolutePath()+"\\"+paperReport;
			File reportFile=new File(reportPath);
			if(reportFile.exists() && reportFile.isDirectory() )
			{
				//ɾ������
				File[] files=reportFile.listFiles();
				for(int i=0;i<files.length;i++)
				{
					files[i].delete();
				}
				reportFile.delete();
			}
			//ɾ�����ݿ�
			paperDao.delete(paper);
		}
		
	}
}
