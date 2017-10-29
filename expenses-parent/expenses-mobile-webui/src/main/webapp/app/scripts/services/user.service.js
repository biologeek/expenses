(function() {
	/*
	 * Authentication and user service
	 */
	'use strict';
	var serviceModule = angular.module('myApp');

	serviceModule.factory('UserService', [
			'$http',
			'$cookies',
			function($http, $cookies) {
				return {
					getUser: function(id, callbackSuccess, callbackError){
						$http.get('/expenses/user/'+id).then(function(response) {
							callbackSuccess(response.data);
						},function(response){
							callbackError(response);
						});
					},
					isAuthenticated: function() {
						if ($cookies.get("user") && $cookies.get("token")){
							return true;
						}
						return false;
					},
					setAuthenticated : function(isAuthenticated, username,
							token, userId, account) {
							$cookies.put("token", token, {path: "/"});
							$cookies.put("user", userId, {path: "/"});
							$cookies.put("account", account, {path: "/"});
					},
					logout: function(){
						$cookies.remove("token");
						$cookies.remove("account");
						$cookies.remove("user");
					},
					getAccounts: function(userId, callbackSuccess, callbackError){
						$http.get('/expenses/user/'+userId+'/accounts').then(function(response) {
							callbackSuccess(response.data);
						},function(response){
							callbackError(response);
						});
					},
					login: function(login, password){
						return $http.post('/expenses/user/login', {"login": login, "password": password});
					}
				};
			} ]);

})();