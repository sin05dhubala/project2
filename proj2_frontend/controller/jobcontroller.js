/**
 * JobController
 */

app.controller('JobController',function($scope,$location,JobService){
	$scope.showJobDetails=false;
	$scope.saveJob=function(){
		JobService.saveJob($scope.job).then(function(response){
			console.log(response.data)
			alert("Added job details successfully")
		},function(response){
			$scope.error=response.data
			console.log(response.status)
			if(response.status==401)
				$location.path('/login')
			$location.path('/savejob')
		
		})
	}
	
	$scope.getJobById=function(id){
		console.log("Job Id Is" +id);
		$scope.showJobDetails=true
		JobService.getJobById(id).then(function(response){
			$scope.job=response.data
		},function(response){
			console.log(response.data)
			if(response.status==401)
				$location.path('/login')
		
		})
	}
	
	function listOfJobs(){
		JobService.listOfJobs().then(function(response){
			console.log(response.data)
			console.log(response.status)
			$scope.jobs=response.data
		},function(response){
			console.log(response.status)
			if(response.status==401)
				$location.path('/login')
		
		})
	}
	listOfJobs()
})