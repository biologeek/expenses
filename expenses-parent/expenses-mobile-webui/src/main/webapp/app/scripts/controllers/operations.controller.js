(function(){
	/*
	 * Dashboard controller
	 */
	'use strict';
	var controllerModule = angular.module('myApp');
	
	controllerModule.controller('OperationsController', ['$scope', 'MobileService', '$translate', '$cookies', '$routeParams', '$location', 
		function($scope, MobileService, $translate, $cookies, $routeParams, $location) {
	    
		var vm = this;
		
		vm.currentOperationsList = null;
		
		vm.pager = 1;
		vm.limit = $routeParams.limit;
		vm.totalItems = null;
		/**
		*	
		* @param addition : boolean to notify if operations should be added to currentOperationsList or list erased 
		*
		*/
		vm.getOperations = function(addition){
			MobileService.list(vm.limit, vm.pager).then(function(response){
					if (addition){
						vm.currentOperationsList.push(response.data.operations);
					} else {
						vm.currentOperationsList = response.data.operations;
					}
					vm.pager = response.data.currentPage;
					vm.totalPages = response.data.totalPages;
					vm.totalOperations = response.data.totalOperations;
					vm.totalItems = response.data.totalOperations;
				}, function(response){
					console.log('Error '+response.status+' : '+response.data.key);
			});	
		};

		vm.getNextOperations = function(){
			vm.pager += 1;
			vm.getOperations();
		};
		
		
		vm.remove = function(id){
			if (id > 0){
				MobileService.delete(id).then(function(resp){},function(response){
					console.log('Error '+response.status+' : '+response.data.key);
				});
			}
		};
		
		
		vm.openOperation = function(id){
			if (id > 0){
				var op = _.find(vm.currentOperationsList, function(o){
					return o.id == id;
				});
				
				if (typeof op !== "undefined"){
					$location.path("/account/"+$routeParams.accountId+"/operation/"+op.id);
				}
			}
		};
		
		if ($routeParams.limit)
			vm.getOperations();
	}]);
	
})();