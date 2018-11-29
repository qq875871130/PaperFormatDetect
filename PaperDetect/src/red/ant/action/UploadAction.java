package red.ant.action;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import red.ant.service.PaperService;
import red.ant.util.WebLogger;
import com.opensymphony.xwork2.ActionSupport;

/**
 * �ϴ�����
 * 
 * @author Administrator
 * 
 */
public class UploadAction extends ActionSupport {

	private File upload;// �ϴ��ļ�
	private String uploadContentType;// ��װ�ļ�����
	private String uploadFileName;// �ļ���
	private String savePath;// ����·��
	private String temp = ServletActionContext.getRequest()
			.getParameter("temp");// �Ƿ����δ��루1-���Σ�
	private String flag = ServletActionContext.getRequest()
			.getParameter("flag");// ˶ʿ����
	private PaperService paperService;
	// ��ȡϵͳΨһ��ǰʱ��
	private final SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd-HH-mm-ss");// �������ڸ�ʽ
	private final String paper_time = df.format(new Date());// new
															// Date()Ϊ��ȡ��ǰϵͳʱ��

	// ��ȫ�ֱ�����ǰ̨��ÿ��ajax���󶼴���һ���µ�ʵ����
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		uploadFileName = uploadFileName.replace(" ", "-");
		int i, j;
		// �ļ�����ֻ����.docx
		// �洢��׺��
		String[] data = uploadFileName.split(".");
		String str = "";
		// �洢�ļ�������ȥ��׺��
		String str2 = "";
		str = ".docx";
		str2 = uploadFileName.substring(0, uploadFileName.length() - 5);
		if(uploadFileName.endsWith(".doc")){
			str=".doc";
			str2 = uploadFileName.substring(0, uploadFileName.length() - 4);
		}
		HttpSession session = ServletActionContext.getRequest().getSession();
		String username = (String) session.getAttribute("username");
		String name = (String) session.getAttribute("name");
		// �ļ����Ʋ�����ð�ţ�:���������޷��洢�ļ�
		return name + "_" + username + "_" + str2 + "_" + paper_time + str;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getSavePath() throws IOException {
		// �����·��ת���ɾ���·��
		File file = new File(".");
		//return file.getCanonicalFile().getParent() + "\\paperFolder";
		return "D:\\PaperFormatDetection\\Papers\\";
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public PaperService getPaperService() {
		return paperService;
	}

	public void setPaperService(PaperService paperService) {
		this.paperService = paperService;
	}

	// �ϴ�action
	public String execute() throws IOException {
		// ����·��
		WebLogger webLogger = WebLogger.getLogger();
		webLogger.log_All("��ʼ", "�ϴ�",
				"ϵͳ��ǰʱ�䣺" + (Long) System.currentTimeMillis());
		HttpSession session = ServletActionContext.getRequest().getSession();
		String user_ip = (String) session.getAttribute("ip");
		String relativePath = getSavePath() + "\\" + getUploadFileName();
		String sid = (String) session.getAttribute("username");// ��ǰ�û�ѧ��
		String sname = (String) session.getAttribute("name");
		// ��ȡ�ļ����Ƶĺ�׺
		String str = uploadFileName.substring(uploadFileName.length() - 4,
				uploadFileName.length());
		if(uploadFileName.endsWith("docx"))
			str="docx";
		else if(uploadFileName.endsWith("doc"))
			str="doc";

		if (upload != null && sid != null && (str.equals("docx") || str.equals("doc"))) {
			InputStream is = null;
			try {
				is = new FileInputStream(getUpload());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				webLogger.log_All("�ļ�", "�ϴ�", "�쳣");
				e.printStackTrace();
				return null;// �ļ�Ϊ��ֱ�ӽ���
			}
			File file = new File(getSavePath());
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			OutputStream os = new FileOutputStream(getSavePath() + "\\"
					+ getUploadFileName());
			// ��InputStream���byte������OutputStream
			IOUtils.copy(is, os);
			os.flush();
			webLogger.log_All("����", "�ϴ�",
					"ϵͳ��ǰʱ�䣺" + (Long) System.currentTimeMillis());
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(os);
			//upload.delete();
			// ORA-12899: �� "PAPERUSER2016"."PAPER"."PAPER_ID" ��ֵ̫�� (ʵ��ֵ: 106,
			// ���ֵ: 100)
			// ���ݿ���varchar(100)���ַ�ռһ���ֽڣ�����ռ2���ֽڣ�GBK��������ռ3���ֽڣ�UTF-8��,���ǵı���Ϊutf-8
			try {
				myexec();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				webLogger.log_All(user_ip, sid, "��������ó����쳣:"
						+ getUploadFileName());
				e.printStackTrace();
			}

			// ��ȥ.docx���ļ���
			String reportStr = getUploadFileName().substring(0,
					getUploadFileName().length() - 5);
			if(uploadFileName.endsWith(".docx"))
				reportStr= getUploadFileName().substring(0,
						getUploadFileName().length() - 5);
			else if(uploadFileName.endsWith(".doc"))
				reportStr= getUploadFileName().substring(0,
						getUploadFileName().length() - 4);
			//File tempFile = new File("Papers");
			String realPath = (getSavePath() + getUploadFileName()).replaceAll("Papers", "Reports");
			File reportFile = new File(realPath);
			if (reportFile.exists())// ������ɱ���
			{
				paperService
						.save(relativePath, sid, sname, getUploadFileName());
				webLogger.log_All(user_ip, sid, "��ⱨ�����ɳɹ�:"
						+ getUploadFileName());
				session.setAttribute("timeFlag", "true");
			} else {
				webLogger.log_All(user_ip, sid, "δ���ɱ���:" + getUploadFileName());
				session.setAttribute("timeFlag", "false");
			}
			return SUCCESS;
		} else
			return null;
	}

	public String myexec() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		WebLogger weblogger = WebLogger.getLogger();
		String user_ip = (String) session.getAttribute("ip");
		String user_name = (String) session.getAttribute("username");
		// ���������·��
		String testPath = getSavePath() + getUploadFileName();
		String FileName = getUploadFileName();
		try{
			String s;
			String evm="D:\\PaperFormatDetection\\PaperFormatDetection\\PaperFormatDetection\\PaperFormatDetection\\bin\\Debug";
			String commond=evm+"\\PaperFormatDetection.exe  "+testPath+"  "+session.getAttribute("flag");
			Process process = Runtime.getRuntime().exec(commond);
			SequenceInputStream sis = new SequenceInputStream(
					process.getInputStream(), process.getErrorStream());
			InputStreamReader isr = new InputStreamReader(sis, "gbk");
			BufferedReader br = new BufferedReader(isr);
			while((s=br.readLine()) != null)
				System.out.println(s);
		    process.waitFor();
		}catch(Exception e){
			System.out.print(e.getMessage());
		}

		// Ӧ�ó��������ɺ�����ר��ģ��
		//tempfile.delete();
		return "success";
	}

	public void myexecute() throws Exception {
		WebLogger weblogger = WebLogger.getLogger();
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (session.getAttribute("timeFlag") != null
				&& session.getAttribute("timeFlag").equals("true")) {
			ServletActionContext.getResponse().getWriter().println("1");
			session.removeAttribute("timeFlag");
		} else if (session.getAttribute("timeFlag") != null
				&& session.getAttribute("timeFlag").equals("false")) {
			ServletActionContext.getResponse().getWriter().println("2");
			session.removeAttribute("timeFlag");
		} else {
			ServletActionContext.getResponse().getWriter().println("0");
		}

	}

	public void myexecute2() throws Exception {
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.setAttribute("temp", temp);
		session.setAttribute("flag", flag);
	}

	// ҳ�������
	public void sumPaper() throws IOException {
		int sum = paperService.Sum();
		ServletActionContext.getResponse().getWriter().println(sum);
	}
}

class TimeOut extends Thread {
	Process process;

	TimeOut(Process process) {
		this.process = process;
	}

	public void run() {
		try {
			// ��ȫ���ǣ���ʱ18����������߳�
			Thread.currentThread().sleep(18000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// �����ʱ�߳��Ѿ����������ٺ���δ�����쳣
		process.destroy();
	}
}
class TimeOut1 extends Thread {
	Process process;

	TimeOut1(Process process) {
		this.process = process;
	}

	public void run() {
		try {
			// ��ȫ���ǣ���ʱ18����������߳�
			Thread.currentThread().sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// �����ʱ�߳��Ѿ����������ٺ���δ�����쳣
		process.destroy();
	}
}