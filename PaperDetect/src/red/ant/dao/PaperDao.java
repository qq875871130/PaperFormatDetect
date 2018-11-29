package red.ant.dao;
import java.util.List;

import red.ant.po.Paper;

public interface PaperDao {
public String save(Paper paper);
public void update(Paper paper);
public Paper get(String pid);
public List<Paper> findAll();
public List<Paper> findByUsername(String username);
/**
 * ��ʦҳ����ҹ���
 * @param name
 * @return
 */
public List<Paper> findByName(String name);
/**
 * ��ʦ������ҹ���
 * @param paperName
 * @return
 */
public List<Paper> findByPaperName(String paperName);
/**
 * ��ʦ������ҹ���
 * @param ptime
 * @return
 */
public List<Paper> findByPtime(String ptime);
public void delete(Paper paper);
}
