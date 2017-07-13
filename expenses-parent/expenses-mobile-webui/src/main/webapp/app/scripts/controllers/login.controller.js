/**
 * 
 */
(function() {
	/*
	 * Login controller
	 */
	'use strict';
	var controllerModule = angular.module('myApp');

	controllerModule.controller('LoginController', [
			'$scope',
			'$cookies',
			'$location',
			'$routeParams',
			'UserService',
			function($scope, $cookies, $location, $routeParams, UserService) {
				
				var vm = this;
				
				vm.loginToApp = function() {
					if (vm.login && vm.password){
						UserService.login(login, password, function(response){
							
						});
					}
				}
			}]);
	
})();