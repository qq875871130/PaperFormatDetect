package red.ant.action;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import red.ant.po.Paper;
import red.ant.service.PaperService;
import red.ant.service.StudentService;
import com.opensymphony.xwork2.Action;
import net.sf.json.*;
/**
 * ���Ҽ����ʷ����ʦ���棩
 * @author �����
 *
 */
public class AllHistoryFindAction implements Action{
	private String findType;//ѡ�����ͣ�������ѧ�ţ��༶���������ƣ�ʱ�䣩
	private String findValue;//����ֵ
	private JSONObject jsonList;
	private PaperService paperService;
	private StudentService studentService;
	
	public String getFindType() {
		return findType;
	}

	public void setFindType(String findType) {
		this.findType = findType;
	}

	public String getFindValue() {
		return findValue;
	}

	public void setFindValue(String findValue) {
		this.findValue = findValue;
	}

	public JSONObject getJsonList() {
		return jsonList;
	}

	public void setJsonList(JSONObject jsonList) {
		this.jsonList = jsonList;
	}

	public PaperService getPaperService() {
		return paperService;
	}

	public void setPaperService(PaperService paperService) {
		this.paperService = paperService;
	}
	
	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public String execute() throws Exception {
		// TODO Auto-generated method stub
		List<Paper> list=new ArrayList<Paper>();
		findValue=URLDecoder.decode(findValue,"utf-8");
		//����
		if(findType.equals("0"))
		{
			list=paperService.fidByName(findValue);
		}
		//ѧ��
		else if(findType.equals("1"))
		{
			list=paperService.findByUsername(findValue);
		}
		//��������
		else if(findType.equals("2"))
		{	
			list=paperService.findByPaperName(findValue);
		}
		//�༶
		else if(findType.equals("3"))
		{
			List<String> list1=studentService.findUsernameByGrade(findValue);
			if(list1!=null)
			{
				
				for(int i=0;i<list1.size();i++)
				{
					List<Paper> list2=paperService.findByUsername(list1.get(i));
					if(list2!=null)
					{
						list.addAll(list2);
					}
				}
			}
		}
		else if(findType.equals("4"))
		{
			list=paperService.findByPtime(findValue);
		}
			
		JSONArray jsonArray = JSONArray.fromObject(list);
		jsonList = new JSONObject();//����ΪĬ�Ϸ���
		int num=list.size();
		jsonList.element("historyList", jsonArray);
		jsonList.put("total",num);
		return SUCCESS;
	}

}
