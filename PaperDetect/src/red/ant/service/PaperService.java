package red.ant.service;

import java.util.Date;
import java.util.List;

import red.ant.po.Paper;

public interface PaperService {
public void update(String username, String password);
public String save(String paper_path,String sid,String name,String paper_id);//pid��time�ڲ�����
public List<Paper> findByUsername(String username);
public String getPath(String pid);
public String getTestPath(String pid);
public String getPaper_id(String pid);
/**
 * ��ʦ�ɲ鿴����ѧ���ļ����ʷ
 * @return
 */
public List<Paper> show();
public Integer Sum();
/**
 * ��ʦҳ��Ĳ��ҹ���
 * @param name
 * @return
 */
public List<Paper> fidByName(String name);
public List<Paper> findByPaperName(String paperName);
public List<Paper> findByPtime(String ptime);
/**
 * ɾ��pid��Ӧ�����ݿ�ʵ�����ļ�������
 * @param pid
 */
public void delete(String pid);

}