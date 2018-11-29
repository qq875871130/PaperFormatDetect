package red.ant.dao;


import java.util.List;

import red.ant.po.Advice;

public interface AdviceDao 
{
	public Integer save(Advice advice);
	public void update(Advice advice);
	public Advice get(Integer aid);
	public List<Advice> findAll();
	/**
	 * ��ʦҳ����ҹ���
	 * @param name
	 * @return
	 */
	public List<Advice> findByName(String name);
	public List<Advice> findByUsername(String username);
	/**
	 * ɾ��adviceʵ��
	 * @param advice
	 */
	public void delete(Advice advice);
}