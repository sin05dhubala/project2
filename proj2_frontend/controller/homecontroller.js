/**
 * HomeController
 */
app.controller('HomeController',function($scope,$rootScope,BlogPostService){
	function getApprovalStatus(){
		BlogPostService.getApprovalStatus().then(function(response){
			/*
			 * list of blog posts which are approved /rejected by admin and user is not yet notified
			 */
			$rootScope.approvalStatus=response.data
			console.log($rootScope.approvalStatus.length)
		},function(response){
			console.log(response.status)
		})
	}
	
	getApprovalStatus();
})
