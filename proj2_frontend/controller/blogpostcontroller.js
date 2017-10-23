
/**
 * BlogPostController
 */
app.controller('BlogPostController',function($scope,BlogPostService,$location){
	$scope.saveBlogPost=function(){
		console.log($scope.blogPost)
		BlogPostService.saveBlogPost($scope.blogPost).then(function(response){
			alert('Blogpost added successfully and waiting for approval ')
			$location.path('/home')
		},function(response){
			if(response.status==401)
				$location.path('/login')
			else
				$location.path('/saveblogpost')
		})
	}
	
	function listOfBlogsApproved(){
	      BlogPostService.listOfBlogsApproved().then(function(response){
	    	  console.log(response.data)
	    	  $scope.blogPostsApproved=response.data;
	      },function(response){
	    	  console.log(response.data)
	    	  if(response.status==401)
	    		  $location.path('/login')
	      })
	}
	
	function listOfBlogsWaitingForApproval(){
		BlogPostService.listOfBlogsWaitingForApproval().then(function(response){
			console.log(response.data)
			$scope.blogPostsWaitingForApproval=response.data
		},function(response){
			console.log(response.data)
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	listOfBlogsApproved()
	listOfBlogsWaitingForApproval()
})

