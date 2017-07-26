(function(){
	/*
	 * Dashboard controller
	 */
	'use strict';
	var controllerModule = angular.module('myApp');
	
	controllerModule.controller('OperationsController', ['$scope', 'MobileService', '$translate', '$cookies', '$routeParams', 
		function($scope, MobileService, $translate, $cookies, $routeParams) {
	    
		var vm = this;
		
		vm.currentOperationsList = null;
		
		var pager = 0;
		
		vm.getOperations = function(){
			
			var limit = $routeParams.limit;
			
			MobileService.list(limit, pager).then(function(response){
				vm.currentOperationsList = response.data;
			}, function(response){
				console.log('Error '+response.status+' : '+response.data.key);
			});	
		};
		
		
		vm.remove = function(id){
			if (id > 0){
				MobileService.delete(id).then(function(resp){
					
				},function(response){
					console.log('Error '+response.status+' : '+response.data.key);
				});
			}
		};
		
		vm.getOperations();
	}]);
	
})();