package red.ant.dao;


import java.util.List;

import red.ant.po.Student;

public interface StudentDao {
public String save(Student student);
public void update(Student student);
public Student get(String username);
public List<Student> findByName(String name);
public Student findStudentByNameAndPass(String name,String password);
public String findName(String username);
//�޸�����֮��
public Student findByUsername(String username);
/**
 * ��֤�û��Ƿ����	
 * @return
 */
public List<Student> findAll();
/**
 * Ѱ��ͬʱƥ��ѧ�ź���֤���ʵ���Ƿ����
 * @param username
 * @param identity
 * @return
 */
public Student userIdentity(String username,String identity);
/**
 * ���ݰ༶����
 * @param grade
 * @return
 */
public List<Student> findByGrade(String grade);

}

