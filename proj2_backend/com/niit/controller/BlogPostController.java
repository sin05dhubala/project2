package com.niit.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.BlogPostDao;
import com.niit.dao.UserDao;
import com.niit.model.BlogPost;
import com.niit.model.Error;
import com.niit.model.User;
@Controller
public class BlogPostController {
	@Autowired
private BlogPostDao blogPostDao;
    @Autowired
	private UserDao userDao;
@RequestMapping(value="/saveblogpost",method=RequestMethod.POST)
public ResponseEntity<?> saveBlogPost(@RequestBody BlogPost blogPost,HttpSession session){
	if(session.getAttribute("username")==null){
		Error error=new Error(5,"Unauthorized access");
		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	}
	String username=(String)session.getAttribute("username");
	try{
		blogPost.setPostedOn(new Date());
		User postedBy=userDao.getUserByUsername(username);
		blogPost.setPostedBy(postedBy);//value for foreign key
	blogPostDao.saveBlogPost(blogPost);
	return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}catch(Exception e){
		Error error=new Error(6,"Unable to insert blogpost details " + e.getMessage());
		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
/*
 * if [/getallblogs/0], approved=0, return list of blog posts waiting for approval - admin
 * if [/getallblogs/1], approved=1, return list of blog posts which are approved - all users
 */
@RequestMapping(value="/getallblogs/{approved}",method=RequestMethod.GET)
public ResponseEntity<?> getAllBlogs(@PathVariable int approved,HttpSession session){
	if(session.getAttribute("username")==null){
		Error error=new Error(5,"Unauthorized access");
		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	}
	List<BlogPost> blogPosts=blogPostDao.getAllBlogs(approved);
	return new ResponseEntity<List<BlogPost>>(blogPosts,HttpStatus.OK);
}
@RequestMapping(value="/getblogpost/{id}")
public ResponseEntity<?> getBlogPost(@PathVariable int id,HttpSession session){
	if(session.getAttribute("username")==null){
		Error error=new Error(5,"Unauthorized access");
		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	}
	BlogPost blogPost=blogPostDao.getBlogPost(id);
	return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
}
@RequestMapping(value="/updateblogpost",method=RequestMethod.PUT)
public ResponseEntity<?> updateBlogPost(@RequestBody BlogPost blogPost,HttpSession session){
	if(session.getAttribute("username")==null){
		Error error=new Error(5,"Unauthorized access");
		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	}
	try{
	if(!blogPost.isApproved()&& blogPost.getRejectionReason()==null)
		blogPost.setRejectionReason("Not Mentioned");//blogpost is reject but reason not mentioned
	blogPostDao.updateBlogPost(blogPost);//update the value for approval property 0/1
	return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}catch(Exception e){
		Error error=new Error(6,"Unable to update blogpost");
		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@RequestMapping(value="/blogpostapprovalstatus",method=RequestMethod.GET)
public ResponseEntity<?> getBlogPostApprovalStatus(HttpSession session){
	if(session.getAttribute("username")==null){
		Error error=new Error(5,"Unauthorized access");
		return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
	}
	String username=(String)session.getAttribute("username");
	List<BlogPost> blogPosts=blogPostDao.getApprovalStatus(username);
	return new ResponseEntity<List<BlogPost>>(blogPosts, HttpStatus.OK);
}
}
