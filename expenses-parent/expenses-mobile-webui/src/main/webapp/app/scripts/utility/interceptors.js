(function(){
	'use strict';
	
	var app = angular.module('myApp');
	
	app.factory('AuthenticationInterceptor', ['$q', '$cookies', function($q, $cookies){
		
		return {
			request: function(request){
				request.headers = request.headers || {};
				request.headers = { 
					 'Authorization' : $cookies.get('token') || '',
					 'Content Type' : "application/json",
					 'Accept': "application/json",
					 'user': $cookies.get('user') || ''
			      };
			      
			      console.log(request.headers);
				return request;
			}, 
			responseError: function(response){
				if (response.status === 401){
					// Can't use UserService.setAuthenticated(false);
					$cookies.remove('user');
					$cookies.remove('username');
					$cookies.remove('token');
					$location.path('/login');
				}
				return response;
			}
		};
		
	}]).config([ '$httpProvider', function($httpProvider){
		$httpProvider.interceptors.push('AuthenticationInterceptor');
	}]);
	
	app.factory('AccountInterceptor', ['$cookies', function($cookies) {
		return {
			request: function(request) {
				request.url.replace('${operation.account}', $cookies.get('account'));
				return request;
			}
		};
	}]).config([ '$httpProvider', function($httpProvider){
		$httpProvider.interceptors.push('AccountInterceptor');
	}]);	
})();