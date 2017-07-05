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
			function($scope, $cookies, $location) {

				var vm = this;

				vm.accountName = null;
				vm.availableAccounts = null;

				vm.getAccounts = function() {
					var user = $cookies.get('userId');
					if (user && user > 0) {
						UserService.getUser(user, function(data) {
							if (user && user.id) {
								UserService.getAccounts(user.id,
										function(data) {
											vm.availableAccounts = data;
										}, function(response) {
											console.log('Error '
													+ resonse.status + ', '
													+ response.data);
										});
							} else {
								console.log('No user found');
							}
						}, function(response){
							console.log('No user found');
						});
					}
				};

				vm.authent = function() {
					_.each(vm.availableAccounts, function(o) {
						if (o.name == vm.accountName) {
							// FIXME Boooowwww baaaaad !!!
							$cookies.put("accountId", o.id);
							$location.path("#!/welcome");
						}
					});
				};
			} ]);
})();