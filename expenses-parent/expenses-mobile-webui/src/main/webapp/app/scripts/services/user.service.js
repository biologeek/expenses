(function() {
	/*
	 * Authentication and user service
	 */
	'use strict';
	var serviceModule = angular.module('myApp');

	serviceModule.factory('UserService', '$cookies', [
			'$http',
			function($http, $cookies) {
				return {
					isAuthenticated: function() {
						if ($cookies.get("username") && $cookies.get("token")){
							return true;
						}
						return false;
					},
					setAuthenticated : function(isAuthenticated, username,
							password, token, userId) {
						if (!isAuthenticated) {
							$cookies.put("username", username);
							$cookies.put("token", token);
							$cookies.put("userId", userId);
						}
					},
					logout: function(){
						$cookies.remove("username");
						$cookies.remove("token");
					},
					getAccounts: function(userId, callbackSuccess, callbackError){
						$http.get('/expenses/user/'+userId+'/accounts').then(function(response) {
							callbackSuccess(response.data);
						},function(response){
							callbackError(respone);
						})
					}
				};
			} ]);

})();