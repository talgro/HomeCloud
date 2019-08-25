package io.homecloud.homeserver.localFile;

import java.util.List;

public class DataResponse {

	private int numOfItems;
	private long totalSize;
	private List<LocalFile> content;

	public DataResponse() { }

	public DataResponse(List<LocalFile> content) {
		super();
		this.content = content;
		totalSize = content.stream().mapToLong(x -> x.getSize()).sum();
		numOfItems = content.size();
	}

	public int getNumOfItems() {
		return numOfItems;
	}

	public void setNumOfItems(int numOfItems) {
		this.numOfItems = numOfItems;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public List<LocalFile> getContent() {
		return content;
	}

	public void setContent(List<LocalFile> content) {
		this.content = content;
	}	

}
