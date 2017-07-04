(function() {
	/*
	 * Dashboard controller
	 */
	'use strict';
	var controllerModule = angular.module('myApp');

	controllerModule.controller('AuthentController', [
			'$scope',
			'$routeParams',
			function($scope, CategoryService, MobileService, $routeParams) {

				var vm = this;

				vm.accountName = null;
				
				vm.authent = function(){
					
				}
			} ]);
})();