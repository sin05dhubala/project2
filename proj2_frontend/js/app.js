/**
 * Angular Js module
 */
var app=angular.module("ngApp",['ngRoute','ngCookies'])
app.config(function($routeProvider){
	$routeProvider
	.when('/home',{
		templateUrl:'views/home.html',
		controller:'HomeController'
	})
	.when('/registration',{
		templateUrl:'views/registrationform.html',
		controller:'UserController'
	})
	.when('/login',{
		templateUrl:'views/login.html',
		controller:'UserController'
	})
	.when('/editprofile',{
		templateUrl:'views/updateprofile.html',
		controller:'UserController'
	})
	.when('/savejob',{
		templateUrl:'views/jobform.html',
		controller:'JobController'
	})
	.when('/getalljobs',{
		templateUrl:'views/listofjobs.html',
		controller:'JobController'
	})
	.when('/saveblogpost',{
		templateUrl:'views/blogpostform.html',
		controller:'BlogPostController',
	})
	.when('/getallblogs',{
		templateUrl:'views/listofblog.html',
		controller:'BlogPostController'
	})
	.when('/blogpostforapproval/:id',{
		templateUrl:'views/blogpostapprovalform.html',
		controller:'BlogPostDetailController'
	})
	.when('/blogpostdetail/:id',{
		templateUrl:'views/blogpostdetails.html',
		controller:'BlogPostDetailController'
	})
	.when('/approvalstatus/:id',{
		templateUrl:'views/blogpoststatus.html',
		controller:'BlogPostDetailController'
	})
	.when('/uploadprofilepic',{
		templateUrl:'views/uploadprofilepic.html'
	})
	.when('/suggestedusers',{
		templateUrl:'views/suggestedusers.html',
		controller:'FriendController' //From C to V
	})
	.when('/pendingrequests',{
		templateUrl:'views/pendingRequests.html',
		controller:'FriendController'//From C to V
	})
	.when('/getfriends',{
		templateUrl:'views/listoffriends.html',
		controller:'FriendController'//From C to V
	})
	 .when('/chat',{
		templateUrl:'views/chat.html',
		controller:'ChatController'
	})


	.otherwise({
		templateUrl:'views/home.html'
	})
})
/*
 * when the page gets refreshed this app.run function will get executed
 * restore the user datails to $rootScope user property [$cookieStore]
 * add logout function to $rootScope.
 * add proprerty user , logout function to $rootScope 
 */
app.run(function($rootScope,$cookieStore,UserService,BlogPostService,$location){
	$rootScope.logout=function(){
		UserService.logout().then(function(response){
			delete $rootScope.currentUser;
			$cookieStore.remove('currentUser')
			$location.path('/login')
		},function(response){
			console.log(response.status)
		})
	}
	
	
	
	if($rootScope.currentUser==undefined)
		$rootScope.currentUser=$cookieStore.get('currentUser')
	
})
