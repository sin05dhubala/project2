package com.niit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="blogpost_Batch4")
public class BlogPost {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
private int id;
private String blogTitle;
@Lob
private String description;
private Date  postedOn;
@ManyToOne
private User postedBy;
private boolean approved;

private String rejectionReason;
private boolean viewedStatus;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getBlogTitle() {
	return blogTitle;
}
public void setBlogTitle(String blogTitle) {
	this.blogTitle = blogTitle;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public Date getPostedOn() {
	return postedOn;
}
public void setPostedOn(Date postedOn) {
	this.postedOn = postedOn;
}
public User getPostedBy() {
	return postedBy;
}
public void setPostedBy(User postedBy) {
	this.postedBy = postedBy;
}
public boolean isApproved() {
	return approved;
}
public void setApproved(boolean approved) {
	this.approved = approved;
}
public String getRejectionReason() {
	return rejectionReason;
}
public void setRejectionReason(String rejectionReason) {
	this.rejectionReason = rejectionReason;
}
public boolean isViewedStatus() {
	return viewedStatus;
}
public void setViewedStatus(boolean viewedStatus) {
	this.viewedStatus = viewedStatus;
}
}
