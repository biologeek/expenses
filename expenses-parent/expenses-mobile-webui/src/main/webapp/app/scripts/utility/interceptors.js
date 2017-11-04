(function(){
	'use strict';
	
	var app = angular.module('myApp');
	
	app.factory('AuthenticationInterceptor', ['$q', '$cookies', '$location', function($q, $cookies, $location){
		
		return {
			request: function(request){
				return request;
			}, 
			responseError: function(response){
				if (response.status == 401 || response.status == 403){
					// Can't use UserService.setAuthenticated(false);
					$cookies.remove('user');
					$cookies.remove('username');
					$cookies.remove('token');
					$location.path('/');
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