package red.ant.po;

public class Advice {
	    private Integer aid;//����
		private String sid;//ѧ��ѧ��
		private String advice;//�������
		private String email;//����
		private String name;//����
		public Advice(){
			
		}
		public Advice(Integer aid,String sid,String advice,String email,String name)
		{
			this.aid=aid;
			this.sid=sid;
			this.advice=advice;
			this.email=email;
			this.name=name;
		}
		
		public Integer getAid() {
			return aid;
		}
		public void setAid(Integer aid) {
			this.aid = aid;
		}
		public String getSid() {
			return sid;
		}
		public void setSid(String sid) {
			this.sid = sid;
		}
		public String getAdvice() {
			return advice;
		}
		public void setAdvice(String advice) {
			this.advice = advice;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
}
