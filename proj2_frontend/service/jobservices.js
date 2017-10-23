/**
 * JobService
 */

app.factory('JobService',function($http){
	var jobService={}
	var BASE_URL="http://localhost:8082/project2_backend"
		jobService.saveJob=function(job){
		console.log(job)
		return $http.post(BASE_URL+"/savejob",job)
	}
	
	jobService.listOfJobs=function(){
		return $http.get(BASE_URL+"/getalljobs")
	}
	jobService.getJobById=function(id){
		return $http.get(BASE_URL+ "/getjobbyid/"+id)
	}
	
	return jobService;
})