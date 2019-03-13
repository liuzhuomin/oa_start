package cn;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Version {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String version;

	private Long size;

	private Date addTime;

	private String downLoadUrl;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getDownLoadUrl() {
		return downLoadUrl;
	}

	public void setDownLoadUrl(String downLoadUrl) {
		this.downLoadUrl = downLoadUrl;
	}

}
