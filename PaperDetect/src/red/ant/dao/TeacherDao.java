package red.ant.dao;

import red.ant.po.Teacher;

public interface TeacherDao {
public String save(Teacher teacher);
public void update(Teacher teacher);
public Teacher get(String username);
public Teacher findByNameAndPass(String username, String password);
/**
 * Ѱ��ͬʱƥ��ѧ�ź���֤���ʵ���Ƿ����,���ڷ���ʵ���������ڷ���null
 * @param username
 * @param identity
 * @return
 */
public Teacher userIdentity(String username,String identity);
}

