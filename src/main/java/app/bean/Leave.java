package app.bean;

import java.io.Serializable;


public class Leave implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1770942125559990450L;
	private Integer id;
	private Integer student_id;
	private String info;
	private String time;
	private Integer status;
	private String remark;
	private String name;
	private String sn;
	private String isAgree;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	@Override
	public String toString() {
		return "Leave [id=" + id + ", student_id=" + student_id + ", info=" + info + ", time=" + time + ", status="
				+ status + ", remark=" + remark + ", name=" + name + ", sn=" + sn + ", isAgree=" + isAgree + "]";
	}
}
