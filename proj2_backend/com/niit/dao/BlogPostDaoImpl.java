package com.niit.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.BlogComment;
import com.niit.model.BlogPost;

@Repository
@Transactional
public class BlogPostDaoImpl implements BlogPostDao {


	@Autowired
	private SessionFactory sessionFactory;
	public void saveBlogPost(BlogPost blogPost) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.save(blogPost);
		
	}
	public List<BlogPost> getAllBlogs(int approved) {
		// TODO Auto-generated method stub
		
		Session session=sessionFactory.getCurrentSession();
		Query query=null;
		if(approved==1)
		    query=session.createQuery("from BlogPost where approved="+ approved);
		else
			query=session.createQuery("from BlogPost where approved=0 and rejectionReason is null");
		return query.list();
	}
	public BlogPost getBlogPost(int id) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		BlogPost blogPost=(BlogPost)session.get(BlogPost.class, id);
		return blogPost;
	}
	public void updateBlogPost(BlogPost blogPost) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.update(blogPost);//update blogpost set approved=?.... where id=?
		
		}
	public List<BlogPost> getApprovalStatus(String username) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogPost where postedBy.username=? and viewedStatus=? and (approved=1 or rejectionreason!=null)");
		query.setString(0, username);
		query.setBoolean(1, false);
		List<BlogPost> blogPosts=query.list();
		for(BlogPost bp:blogPosts){
			bp.setViewedStatus(true);
			session.update(bp);
		}
		return blogPosts;
	}
	public void addBlogComment(BlogComment blogComment) {
		// TODO Auto-generated method stub
		
		Session session=sessionFactory.getCurrentSession();
		session.save(blogComment);
		
	}
	public List<BlogComment> getBlogComments(int blogPostId) {
		// TODO Auto-generated method stub
		
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogComment where blogPost.id=?");
		query.setInteger(0, blogPostId);
		return query.list();//list of blogcomments for blogpostid
	}

}
