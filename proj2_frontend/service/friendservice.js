/**
 * FriendService
 */
app.factory('FriendService',function($http){
	var friendService={}
	var BASE_URL="http://localhost:8082/project2_backend"
	friendService.suggestedUsers=function(){
		return $http.get(BASE_URL + "/suggestedusers")//List of user objects
	}
	friendService.sendFriendRequest=function(toId){
		return $http.post(BASE_URL +"/friendrequest/"+toId)
	}
	
	friendService.pendingRequests=function(){
		return $http.get(BASE_URL + "/pendingrequests")
	}
	friendService.updatePendingRequest=function(request){
		return $http.put(BASE_URL + "/updatependingrequest",request)//request is Friend object with updated status(A/D).
	}
	friendService.getUserDetails=function(fromId){
		return $http.get(BASE_URL + "/getuserdetails/"+fromId)
	}
	
	friendService.getFriends=function(){
		return $http.get(BASE_URL + "/getfriends")
	}
	
	return friendService;
})
