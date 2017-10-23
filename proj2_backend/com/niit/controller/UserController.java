package com.niit.controller;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.niit.model.Error;
import com.niit.dao.UserDao;
import com.niit.model.User;

@Controller
public class UserController {
	@Autowired
private UserDao userDao;
	@RequestMapping(value="/registration",method=RequestMethod.POST)
	//T -> ?  -Any Type of Entity
	public ResponseEntity<?> registration(@RequestBody User user){
		if(!userDao.isEmailValid(user.getEmail())){
			Error error=new Error(2,"Duplicate email address.. ");
			return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE); //error -function
		}
		if(!userDao.isUsernameValid(user.getUsername())){
			Error error=new Error(3,"Username already exists..please enter different username");
			return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);//error -function
		}
		try{
		userDao.registration(user);
		return new ResponseEntity<User>(user,HttpStatus.OK);//200-299 - success function
		}catch(Exception e){
			Error error=new Error(1,"Unable to register.." + e.getMessage());
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);//error -function
		}
	}
		@RequestMapping(value="/login",method=RequestMethod.POST)
		//user -> [username="john",password="123",firstname=null,lastname=null,email=null,phonenumber=null,....]
		public ResponseEntity<?> login(@RequestBody User user,HttpSession session){ 
			
			User validUser=userDao.login(user);
			//validUser -> [username="john",password="123",firstname="john",lastname="smith",online=0
			
			if(validUser==null){ //0 records, 2nd call back function in frontend controller
				Error error=new Error(4,"Invalid Username/Password");
				return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
			}
			
			validUser.setOnline(true);//set the value for online status to true
			userDao.updateUser(validUser);// update online status fro false to true
			session.setAttribute("username", validUser.getUsername());
			return new ResponseEntity<User>(validUser,HttpStatus.OK);//1st call back function in frontend controller
			
		}
		
		@RequestMapping(value="/logout",method=RequestMethod.PUT)
		
		public ResponseEntity<?> logout(HttpSession session){ 
			
			String username=(String)session.getAttribute("username");
			
			if(username==null){ //user is not logged in
				Error error=new Error(5,"please login...");
				return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
			}
		 User user=userDao.getUserByUsername(username);
		 user.setOnline(false);
		 userDao.updateUser(user);//update online status to false
		 session.removeAttribute("username");
		 session.invalidate();
		 return new ResponseEntity<User>(user,HttpStatus.OK);
		 
	}
	   
		@RequestMapping(value="/getuser",method=RequestMethod.GET)
		public ResponseEntity<?> getUser(HttpSession session){ 
			if(session.getAttribute("username")==null)
			{
				Error error=new Error(5,"Unauthorized access.....");
				return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
			}
			String username=(String)session.getAttribute("username");
			User user=userDao.getUserByUsername(username);
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		
	    @RequestMapping(value="/updateprofile",method=RequestMethod.PUT)
		public ResponseEntity<?> updateUser(@RequestBody User user,HttpSession session){
	    	if(session.getAttribute("username")==null)
			{
				Error error=new Error(5,"Unauthorized access");
				return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
			}
	    	try
	    	{
	    		userDao.updateUser(user);
	    		return new ResponseEntity<User>(user,HttpStatus.OK);
	    	}
	    	catch(Exception e)
	    	{
	    		
	    			Error error=new Error(6,"Unable to update user details"+e.getMessage());
	    			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	    
	    	}
	    }
			
	}


