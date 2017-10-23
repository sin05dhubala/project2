package com.niit.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.ProfilePicture;
@Repository
@Transactional
public class ProfilePictureDaoImpl implements ProfilePictureDao{
    @Autowired
	private SessionFactory sessionFactory;
	
     public void saveProfilePicture(ProfilePicture profilePicture) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.getCurrentSession();
		session.saveOrUpdate(profilePicture);
		
	}

	public ProfilePicture getProfilePic(String username) {
		Session session=sessionFactory.getCurrentSession();
		//select * from profilepicture where username='admin'
		ProfilePicture profilePicture=(ProfilePicture)session.get(ProfilePicture.class, username);
		return profilePicture;
	}

}
