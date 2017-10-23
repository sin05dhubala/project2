/**
 * UserController
 */
app.controller('UserController',function($scope,$location,UserService,$rootScope,$cookieStore){
	/*
	 * Registration [data from registrationform.html to UserController
	 * inserting the data into user table
	 */
	$scope.registerUser=function(){
		console.log($scope.user)
		UserService.registerUser($scope.user).then(function(response){
			console.log(response.status)
			$scope.success="Registered successfully.. please login again"
			$location.path('/login')
		},function(response){
			console.log(response.status)
			console.log(response.data)
			
			$scope.error=response.data  
				if($scope.error.code==2)
					$scope.duplicateEmail=response.data
				if($scope.error.code==3)
					$scope.duplicateUsername=response.data
				if($scope.error.code==1)
					$scope.exception=response.data
			$location.path('/registration')
		})
	}
    /*
     * login 
     * data is from login.html to UserController
     * validating the user credentials
     */	
	$scope.login=function(){
		console.log($scope.user)
		UserService.login($scope.user).then(function(response){
			console.log(response.data)
			$rootScope.currentUser=response.data
			$cookieStore.put('currentUser',response.data)
			$location.path('/home')
		},function(response){
			console.log(response.data)
			$scope.loginFail=response.data
			$location.path('/login')
		})
	}
	if($rootScope.currentUser!=undefined){
	UserService.getUser().then(function(response){
		$scope.user=response.data
	},function(response){
	  if(response.status==401)
		  $location.path('/login')
	})
	}
     $scope.updateUser=function(){
		UserService.updateUser($scope.user).then(function(response){
		alert('Updated the details successfully')
		$location.path('/home')
		},function(response){
			if(response.status==401)
				$location.path('/login')
				$scope.error=response.data
				$location.path('/editprofile')
			})
		}
   })
	