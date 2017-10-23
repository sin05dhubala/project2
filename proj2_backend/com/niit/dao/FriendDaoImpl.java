package com.niit.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Friend;
import com.niit.model.User;
@Repository
@Transactional
public class FriendDaoImpl implements FriendDao {
	@Autowired
private SessionFactory sessionFactory;
	public List<User> getAllSuggestedUsers(String username) {
		Session session=sessionFactory.getCurrentSession();		
		SQLQuery query=session.createSQLQuery(
	"select * from user_batch4 where username in (select username from user_batch4 where username!=? minus (select fromId from friend_batch4 where toId=? union select toId from friend_batch4 where fromId=?))");
       query.setString(0, username);
       query.setString(1, username);
       query.setString(2, username);
       query.addEntity(User.class);
       List<User> suggestedUsers=query.list();
       System.out.println("Size of suggested Users list is "+ suggestedUsers);
       return suggestedUsers;
	}
	public void friendRequest(Friend friend) {
		Session session=sessionFactory.getCurrentSession();
		session.save(friend);//insert into friend 
	}
	public List<Friend> pendingRequests(String username) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Friend where toId=? and status='P'");
		query.setString(0, username);
		List<Friend> pendingRequests=query.list();
		return pendingRequests;
	}
	public void updatePendingRequest(Friend friend) {
		Session session=sessionFactory.getCurrentSession();
		if(friend.getStatus()=='A')
			session.update(friend);//update the status from P to A
		else
			session.delete(friend);//delete the record
		
	}
	public List<Friend> listOfFriends(String username) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Friend where status='A' and (fromId=? or toId=?)");
		query.setString(0, username);
		query.setString(1, username);
		return query.list();
	}

}
