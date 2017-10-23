/**
 * UserService
 */
app.factory('UserService',function($http){
	var userService={}
	var BASE_URL="http://localhost:8082/project2_backend"
		/*
		 * user = $scope.user from controller => $scope.user is ng-model=user.x
		 */
	userService.registerUser=function(user){
		alert('entering registerUser in user service')
		return $http.post(BASE_URL + "/registration",user)
	}
	
	userService.login=function(user){
		return $http.post(BASE_URL+"/login",user)
	}
	userService.logout=function(){
		return $http.put(BASE_URL+"/logout")
	}
	userService.getUser=function(){
		return $http.get(BASE_URL+"/getuser")
	}
	userService.updateUser=function(user){
		return $http.put(BASE_URL+"/updateprofile",user)
	}
	return userService;
})