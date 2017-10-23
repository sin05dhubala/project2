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

import com.niit.dao.JobDao;
import com.niit.dao.UserDao;
import com.niit.model.Job;
import com.niit.model.User;
import com.niit.model.Error;

@Controller
public class JobController {

	@Autowired
	private JobDao jobDao;
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value="/savejob",method=RequestMethod.POST)
	public ResponseEntity<?> saveJob(@RequestBody Job job,HttpSession session){
		
		if(session.getAttribute("username")==null){ //user is not authenticated [login method is not executed]
			Error error=new Error(5,"Unauthorized access");
			//Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//2nd call back function
		}
		//How to check role?
		String username=(String)session.getAttribute("username");
		//String username="Meera";
		User user=userDao.getUserByUsername(username);
		if(user.getRole().equals("ADMIN")){
			try{
				job.setPostedOn(new Date());
				jobDao.saveJob(job);//jobTitle=null
				return new ResponseEntity<Job>(job,HttpStatus.OK);//1st call back function
			}
			catch(Exception e){
				Error error=new Error(7,"Unable to insert job details"+ e.getMessage());
				return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);//2nd call back function
			}
			
		}
		else{
			Error error=new Error(6,"Access Denied");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);//2nd call back function
		}
	}
	@RequestMapping(value="/getalljobs",method=RequestMethod.GET)
	public ResponseEntity<?> getAllJobs(HttpSession session){
		
		if(session.getAttribute("username")==null){ 
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		List<Job> jobs=jobDao.getAllJobs();
		return new ResponseEntity<List<Job>>(jobs,HttpStatus.OK);
	}

	@RequestMapping(value="/getjobbyid/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> getJobById(@PathVariable int id,HttpSession session){
		
		if(session.getAttribute("username")==null){ 
			Error error=new Error(5,"Unauthorized access");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		Job job=jobDao.getJobById(id);
		return new ResponseEntity<Job>(job,HttpStatus.OK);
	}
	
}
