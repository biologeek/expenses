(function(){
	'use strict';
	
	var app = angular.module('mainApp');
	
	app.factory('AuthenticationInterceptor', '$q', '$cookies', 'UserService', function($q, $cookies, UserService){
		
		return {
			request: function(request){
				request.headers.Authorization = $cookies.get('token');
			}, 
			responseError: function(response){
				if (!response || response.status === 403 || response.status === 401){
					UserService.setAuthenticated(false);
					$location.path('/login');
				}
			}
		};
		
	});
		
	app.config(function($httpProvider){
		$httpProvider.interceptors.push('AuthenticationInterceptor');
	});
})();