(function() {
	/*
	 * Dashboard controller
	 */
	'use strict';
	var controllerModule = angular.module('myApp');

	controllerModule.controller('AuthentController', [
			'$scope',
			'$routeParams',
			'$cookies', 
			function($scope, CategoryService, MobileService, $routeParams, $cookies) {

				var vm = this;

				vm.accountName = null;
				vm.availableAccounts = null;
				
				vm.authent = function(){
					_.each(vm.availableAccounts, function(o){
						if (o.name == vm.accountName){
							// FIXME Boooowwww baaaaad !!!
							$cookies.put("accountId", o.id);
						}
					})
				}
			} ]);
})();