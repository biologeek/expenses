(function(){
	'use strict';
	
	var app = angular.module('myApp');
	
	app.factory('AuthenticationInterceptor', ['$q', '$cookies', function($q, $cookies){
		
		return {
			request: function(request){
				request.headers.Authorization = $cookies.get('token');
				request.headers.user = $cookies.get('user');
			}, 
			responseError: function(response){
				if (!response || response.status === 401){
					// Can't use UserService.setAuthenticated(false);
					$cookies.remove('user');
					$cookies.remove('username');
					$cookies.remove('token');
					$location.path('/login');
				}
			}
		};
		
	}]);
	
	app.factory('AccountInterceptor', ['$cookies', function($cookies) {
		return {
			request: function(request) {
				request.url.replace('${operation.account}', $cookies.get('account'));
			}
		};
	}]);
})();