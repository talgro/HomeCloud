package io.homecloud.synchronizedFolder.utils;

public class LocalFile {

	private String name;
	private String type;
	private long size;
	private String lastModified;
	private String createDate;

	public LocalFile() { }

	public LocalFile(String name, String type, long size, String lastModified, String createDate) {
		this.name = name;
		this.type = type;
		this.size = size;
		this.lastModified = lastModified;
		this.createDate = createDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}
