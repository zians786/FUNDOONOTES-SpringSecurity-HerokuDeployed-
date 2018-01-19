	var ToDo = angular.module('TODO');

ToDo.factory('adminService',function($http,$location){
	var adminData ={};
	
	
	adminData.getUsers = function(){
		
		return $http({
			method :"GET",
			url :'admin/users',
			headers: { 'accToken': localStorage.getItem('token')
					}
		});
	}
	
	

	adminData.getNotes = function(){
		
		return $http({
			method :"POST",
			url :'admin/notes',
			headers: { 'accToken': localStorage.getItem('token')
					}
		});
	}
	
adminData.getNotesCount = function(userId){
		
		return $http({
			method :"POST",
			url :'admin/note-count/'+userId,
			headers: { 'accToken': localStorage.getItem('token')
					}
		});
	}
	

adminData.getCount = function(userId){
		
		return $http({
			method :"GET",
			url :'admin/note-count/',
			headers: { 'accToken': localStorage.getItem('token')
					}
		});
	}
	
	
	return adminData;
});