/**
 * 
 */
(function() {
	/*
	 * Login controller
	 */
	'use strict';
	var controllerModule = angular.module('expenses.ui');

	controllerModule.controller('LoginController', [
		'$scope',
		'$cookies',
		'$location',
		'$routeParams',
		'UserService',
		function($scope, $cookies, $location, $routeParams, UserService) {
			
			var vm = this;
			vm.login = "";
			vm.password = "";
			
			vm.submitted = function() {
				if (vm.login && vm.password){
					UserService.login(vm.login, vm.password).then(function(response){
						UserService.setAuthenticated(true, response.data.username, response.data.sessionToken, response.data.id);
						$location.path('/accounts/'+response.data.id);
					}, function(response){
						console.log('Error '+response.status+' : '+response.data);
					});
				}
			};
	}]);
	
})();