/**
 * FriendController
 */
app.controller('FriendController',function($scope,$rootScope,FriendService,$location){
	$scope.showUserDetails=false;
	function getSuggestedUsers(){
		FriendService.suggestedUsers().then(function(response){
			$scope.suggestedUsers=response.data
		},function(response){
			if(response.status==401)
				$location.path('/login')
			else
			console.log(response.status)
		})
	}
	function pendingRequests(){
		FriendService.pendingRequests().then(function(response){
			$scope.pendingRequests=response.data//List of Friend objects [use only fromId])
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	$scope.sendFriendRequest=function(toId){
		FriendService.sendFriendRequest(toId).then(function(response){
			getSuggestedUsers();
			$location.path('/suggestedusers')
		},function(response){
			if(response.status==401)
				$location.path('/login')
			else
			console.log(response.status)
		})
	}
	$scope.updatePendingRequest=function(request,value){
		console.log('pending request ' + request)
		request.status=value //value is 'A' for accept and 'D' for delete
		console.log('after assigning value to status  ' + request)
		FriendService.updatePendingRequest(request).then(function(response){
			pendingRequests();
			$location.path('/pendingrequests')
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	$scope.getUserDetails=function(fromId){
		$scope.showUserDetails=true
		FriendService.getUserDetails(fromId).then(function(response){
			$scope.user=response.data
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	function getFriends(){
	FriendService.getFriends().then(function(response){
		$scope.friends=response.data //List<Friend> select * from friend where status='A' and (fromId=? or toId=?)
		$rootScope.noOfFriends=$scope.friends.length
	},function(response){
		if(response.status==401)
			$location.path('/login')
	})
	}
	
	getSuggestedUsers();
	pendingRequests()
	getFriends();
})

