package red.ant.service;

public interface TeacherService {
	public String save(String username,String password,String name,String email);
	public String login(String username,String password);
	public void update(String username, String password);
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
	}