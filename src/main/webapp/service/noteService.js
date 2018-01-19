	var ToDo = angular.module('TODO');

ToDo.factory('noteService',function($http,$location){
	var userNote ={};
	

	userNote.add = function(note){
	
		return $http({
			method :"POST",
			url :'note',
			data : note,
			headers: { 'accToken': localStorage.getItem('token')
					}
		});
	}
		
	userNote.read=function(){
		return $http({
			method:"GET",
			url:'note',
			headers:{ 'accToken':localStorage.getItem('token')
				
			}
		});
	}

	
	userNote.delete=function(noteId){
		return $http({
			method:"DELETE",
			url:"note/"+noteId,
			headers: { 'accToken': localStorage.getItem('token')
			}
		});
		
		
	}
	
	
	userNote.update=function(note){
		
		return $http({
			method:"PUT",
			url:"note",
			data:note,
			headers: {	'accToken':localStorage.getItem('token')		
			}
		});
	}
	
	
	userNote.pin=function(noteId,pin){
		return $http({
			method:"POST",
			url:"note/pin/"+noteId+"/"+pin,
			headers: {'accToken': localStorage.getItem('token')
			}
		});
	}
	
	
	
	userNote.trash=function(noteId,inTrash){
		return $http({
			method:"POST",
			url:"note/trash/"+noteId+"/"+inTrash,
			headers: {'accToken': localStorage.getItem('token')
			}
		});
	}
	
	

	userNote.archive=function(noteId,archived){
		return $http({
			method:"POST",
			url:"note/archive/"+noteId+"/"+archived,
			headers: {'accToken': localStorage.getItem('token')
			}
		});
	}
	
	userNote.color=function(noteId,color){
			return $http({
				method:"POST",
				url:"note/color/"+noteId+"/"+color,
				headers: {'accToken': localStorage.getItem('token')
				}
			});
	}
	
	
	userNote.reminder=function(note){
		return $http({
			method:"POST",
			url:"notes/reminder",
			data:note,
			headers: {'accToken': localStorage.getItem('token')
			}
		});
	}
	
	userNote.createLabel=function(labelId,noteId){
		return $http({
			method:"PUT",
			url:"notes/label/"+labelId+'/note/'+noteId,
			headers: {'accToken': localStorage.getItem('token')
			}
		});
	}
	
	
	userNote.deleteLabel=function(labelId,noteId){
		return $http({
			method:"DELETE",
			url:"notes/label/"+labelId+'/note/'+noteId,
			headers: {'accToken': localStorage.getItem('token')
			}
		});
	}
	
	
	userNote.shareOnFB=function(title,note){
		return $http({
			method:"GET",
			url:"socialShare/title/"+title+"/data/"+note,
		});
	}
	
	userNote.shareNote=function(email,noteId){
		return $http({
			method:"POST",
			url:"notes/share/email/"+email+"/note/"+noteId,
			headers: {'accToken': localStorage.getItem('token')
			}
		});
	}

	
	userNote.removeShared=function(userId,noteId){
		return $http({
			method:"POST",
			url:"notes/removeShare/user/"+userId+"/note/"+noteId,
			headers: {'accToken': localStorage.getItem('token')
			}
		});
		
	}
	
	return userNote;
});
