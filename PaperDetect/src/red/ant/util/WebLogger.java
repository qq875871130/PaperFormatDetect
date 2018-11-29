package red.ant.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class WebLogger {
	private volatile static WebLogger webLogger = null;
	private static Logger logger = null;
	
	private WebLogger(){
		logger=Logger.getLogger(WebLogger.class.getName()); 
		
        // ��õ�ǰĿ¼·��
        String filePath = this.getClass().getResource("/").getPath();
        // �ҵ�log4j.properties�����ļ����ڵ�Ŀ¼(�Ѿ�������)
        filePath = filePath.substring(1).replace("bin", "src");
        PropertyConfigurator.configure(filePath + "log4j.properties");
        
		//PropertyConfigurator.configure("loginlog4j.properties");
	}
	
	public static WebLogger getLogger(){
		if(webLogger == null){
			synchronized(WebLogger.class){
				if(webLogger == null){
					webLogger = new WebLogger();
				}
			}
		}
		return webLogger;
	}
	
	public void log_Login(String ip,String account,String �������){
		logger.error(ip  + "  "+account + "  ��¼  " + "  " + ������� );
	}
	
	public void log_LoginFailure(String ip,String account){
		logger.error(ip +"  "+ account + "  ��¼  "  + "  " + "��¼ʧ�� " );
	}
	
	public void log_All(String ip,String account, String ...strings){
		String s = ip + "  " + account + "  ";
		for(String a:strings){
			s = s + a + "  ";
		}
		logger.error(s);
	}
	
}
