(function(){
	/*
	 * Dashboard controller
	 */
	'use strict';
	angular.module('expenses.ui').controller('ChartsController', ChartsController);

	ChartsController.$inject = ['ChartsService', 'OperationService', 'Constants', '$cookies'];

	function ChartsController($scope, ChartsService, OperationService, Constants, $cookies) {
		var vm = this;
		vm.account = $cookies.accountId;
		vm.begin = 0;
		vm.end = 0;
		vm.intervalUnit = 'DAYS';
		vm.intervalUnits = Constants.timeIntervals;
		vm.chosenTypes = [];
		vm.types = [];
		
		/**
		 * Returns operation types and sets dropdown multiselect
		 */
		vm.getOperationTypes = function(){
			OperationService.getTypes()//
				.then(
					function(response){
						vm.types = response.data;
					}, function(response){
						console.log(response.data);
					}
				);
		}
		
		vm.processOptionsAndGenerateChartData = function(){
			if (vm.chosenTypes.length == 0){
				vm.chosenTypes = vm.types;
			}
			if (_.indexOf(vm.intervalUnits, vm.intervalUnit) == -1){
				console.log('Wrong interval unit');
			}
			// TODO
		}
	        
    };
    
})();