/**
 * BlogPostDetail Controller
 * http://localhost:8082/project2_frontend/index.html#/blogpostforapproval/302
 * http://localhost:8082/project2_frontend/index.html#/blogpostdetail/304
 */

app.controller('BlogPostDetailController',function($scope,$location,BlogPostService,$routeParams){
	var id=$routeParams.id
	$scope.showRejectionTxt=false
	
	BlogPostService.getBlogPostById(id).then(function(response){
		$scope.blogPost=response.data
		console.log(response.data)
		},function(response){
			console.log(response.status)
			if(response.status==401)
				$location.path('/login')
	})
	
	$scope.updateApproval=function(){
		console.log($scope.blogPost)
		BlogPostService.updateApproval($scope.blogPost).then(function(response){
			$location.path('/getallblogs')
			},function(response){
				if(response.data==401)
					$location.path('/login')
				else
					$location.path('/blogpostforapproval/'+id)
		})
	}
	
	$scope.setValueForRejectionTxt=function(val){
		$scope.showRejectionTxt=val
	}
	
	
	$scope.addComment=function(){
		
	console.log('before assigning blogPost to blogComment.blogPost is'+ $scope.blogComment)
	$scope.blogComment.blogPost=$scope.blogPost
	console.log('after assigning blogPost to blogComment.blogPost is'+ $scope.blogComment)
	BlogPostService.addComment($scope.blogComment).then(function(response){
		
		$scope.blogComment.commentText=''
		console.log(response.data)//BlogComment
		getBlogComments()
		},function(response){
			console.log(response.data)//Error
		
	})
	
	}
	
	
	function getBlogComments(){
		
		BlogPostService.getBlogComments(id).then(function(response){
			$scope.blogComments=response.data//List<BlogComment>
		},function(response){
			console.log(response.status)
		
		})
	}
	
	  getBlogComments()
	})
	