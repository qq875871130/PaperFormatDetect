package red.ant.util; 
import java.beans.IntrospectionException;  
import java.beans.Introspector;  
import java.beans.PropertyDescriptor;  
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;  
  
/** 
 * <p> 
 * @author ChenTao 
 * <p> 
 * @Date 2009-5-18 ����05:47:27 
 * <p> 
 */  
public class JsonUtil {  
  
    /** 
     * @param object 
     *            ������� 
     * @return java.lang.String 
     */  
    public static String objectToJson(Object object) {  
        StringBuilder json = new StringBuilder();  
        if (object == null) {  
            //json.append("\"\""); 
        	json.append("\"").append("null").append("\"");
        } else if (object instanceof String ) {  
            json.append("\"").append((String)object).append("\"");  
        }else if(object instanceof Integer) {
        	json.append("\"").append(object.toString()).append("\""); 
        } else if(object instanceof Date){
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	String datestr = sdf.format( object);
        	json.append("\"").append((String)datestr).append("\""); 
        }else {  
            json.append(beanToJson(object));  
        }  
        return json.toString();  
    }  
  
    /** 
     * ��������:��������һ�� javabean ��������һ��ָ�������ַ��� 
     *  
     * @param bean 
     *            bean���� 
     * @return String 
     */  
    public static String beanToJson(Object bean) {  
        StringBuilder json = new StringBuilder();  
        json.append("{");  
        PropertyDescriptor[] props = null;  
        try {  
            props = Introspector.getBeanInfo(bean.getClass(), Object.class)  
                    .getPropertyDescriptors();  
        } catch (IntrospectionException e) {  
        }  
        if (props != null) {  
            for (int i = 0; i < props.length; i++) {  
                try {  
                	//System.out.println("���Ը���Ϊ!!!!!!"+props.length);
                    String name = objectToJson(props[i].getName()); 
                    //System.out.println("������!!!!!!"+props[i].getName());
                    String value = objectToJson(props[i].getReadMethod().invoke(bean));
                    //System.out.println("����ֵ!!!!!!"+value);
                    //ȥ��paper���еĶ���Ԫ��
                    if(name.equals("paper_path")==false && name.equals("sid")==false)
                    {
                    	json.append(name);  
                    	json.append(":");  
                    	json.append(value);  
                    	json.append(",");  
                    }
                } catch (Exception e) {  
                }  
            }  
            json.setCharAt(json.length() - 1, '}');  
        } else {  
            json.append("}");  
        }  
        //System.out.println("Ŀǰjson����������������"+json.toString());
        return json.toString();  
    }  
  
    /** 
     * ��������:ͨ������һ���б����,����ָ���������б��е���������һ��JSON���ָ���ַ��� 
     *  
     * @param list 
     *            �б���� 
     * @return java.lang.String 
     */  
    public static String listToJson(List<?> list) {  
        StringBuilder json = new StringBuilder();  
        json.append("[");  
        if (list != null && list.size() > 0) {  
            for (Object obj : list) {  
                json.append(objectToJson(obj));  
                json.append(",");  
            }  
            json.setCharAt(json.length() - 1, ']');  
        } else {  
            json.append("]");  
        }  
        return json.toString();  
    }  
}  