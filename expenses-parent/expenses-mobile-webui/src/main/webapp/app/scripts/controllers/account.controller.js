(function() {
	/*
	 * Dashboard controller
	 */
	'use strict';
	var controllerModule = angular.module('myApp');

	controllerModule.controller('AccountController', [
			'$scope',
			'$cookies',
			'$location',
			'$routeParams',
			'UserService',
			function($scope, $cookies, $location, $routeParams, UserService) {

				var vm = this;

				vm.accountName = null;
				vm.availableAccounts = null;

				vm.getAccounts = function() {
					var user = $cookies.get('userId') || $routeParams.userId;
					
					if (user && user > 0) {
						UserService.getUser(user, function(data) {
							if (data && data.id) {
								UserService.getAccounts(data.id,
										function(data) {
											vm.availableAccounts = data;
										}, function(response) {
											console.log('Error '+ resonse.status + ', '+ response.data);
										});
							} else {
								console.log('No user found');
							}
						}, function(response){
							console.log('No user found');
						});
					}
				};

				vm.selectAccount = function() {
					_.each(vm.availableAccounts, function(o) {
						if (o.name == vm.accountName.name) {
							// FIXME Boooowwww baaaaad !!!
							$cookies.put("account", o.id, {path: "/"});
							$location.path("/account/"+o.id+"/operations/list/20");
						}
					});
				};

				vm.getAccounts();
	} ]);
})();