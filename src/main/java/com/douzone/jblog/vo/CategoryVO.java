package com.douzone.jblog.vo;

public class CategoryVO {
	private long no;
	private String name;
	private String desc;
	private String blogId;
	public long getNo() {
		return no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getBlogId() {
		return blogId;
	}
	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}
	
	@Override
	public String toString() {
		return "CategoryVO [no=" + no + ", name=" + name + ", desc=" + desc + ", blogId=" + blogId + ", getNo()="
				+ getNo() + ", getName()=" + getName() + ", getDesc()=" + getDesc() + ", getBlogId()=" + getBlogId()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
}
