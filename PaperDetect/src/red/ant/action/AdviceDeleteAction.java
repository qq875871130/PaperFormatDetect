package red.ant.action;
import red.ant.service.AdviceService;
import com.opensymphony.xwork2.ActionSupport;
/**
 * ɾ�������������ʦ���棩
 * @author Administrator
 *
 */
public class AdviceDeleteAction extends ActionSupport{

	private String aid;//�������
	private AdviceService adviceService;

	
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public AdviceService getAdviceService() {
		return adviceService;
	}
	public void setAdviceService(AdviceService adviceService) {
		this.adviceService = adviceService;
	}
	public String execute() throws Exception
	{
		adviceService.delete(aid);
		return SUCCESS;
	}
}
