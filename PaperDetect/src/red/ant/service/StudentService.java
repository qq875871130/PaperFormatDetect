package red.ant.service;

import java.util.List;

import red.ant.po.Student;

public interface StudentService {
public String login(String username,String password);
//�޸�����֮��
public void update(String username, String password);
/**
 * ע��ʱ�����û���Ϣ
 * @param username
 * @param password1
 * @param grade
 * @param name
 * @param sex
 * @param email
 * @return
 */
public String save(String username,String password1,String grade,String name,String sex,String email);
public String save(String username,String grade,String number,String sex);
public List<Student> show(String username);
public String getName(String username);
public Student getAll(String username);
/**
 * �����û������Ҷ�ӦԪ�飬���ܱ��𰸱�������ӦԪ����
 * @param username
 * @param answer1
 * @param answer2
 * @param answer3
 */
public void setSecurity(String username,String answer1,String answer2,String answer3);
/**
 * ��������(��ʱ����)
 * @param username
 * @param password
 */
public void reset(String username,String password);
/**
 * ��֤�û��Ƿ����
 * @param userrname
 * @return
 */
public String IsHas(String username);
/**
 * ����ѧ�Ż�ȡemail
 * @param username
 * @return
 */
public String getEmail(String username);
/**
 * �����û���������֤��
 * @param username
 */
public void setIdentity(String username,String identity);
/**
 * �ҵ����ϣ�username,identity����ʵ������������Ϊpassword
 * @param username
 * @param identity
 * @param password
 * @return
 */
public String resetPassword(String username,String identity,String password);
/**
 * ��ʦҳ�����ͬ�༶��ѧ������
 * @param grade
 * @return
 */
public List<String> findUsernameByGrade(String grade);
}
