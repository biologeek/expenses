(function(){
	/*
	 * Dashboard controller
	 */
	'use strict';
	var chartsController = angular.module('expenses.ui').controller('ChartsController', ChartsController);

	chartsController.$inject = ['ChartsService', 'OperationService', 'Constants', '$cookies'];

	function ChartsController($scope, ChartsService, OperationService, Constants, $cookies) {
		var vm = this;
		vm.account = $cookies.accountId;
		vm.begin = 0;
		vm.end = 0;
		vm.intervalUnit = 'DAYS';
		vm.intervalUnits = Constants.timeIntervals;
		vm.chosenTypes = [];
		vm.types = [];
		
		vm.xLabels=[];
		vm.series = [];
		vm.dataSeries = [];
		
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
		};
		
		/**
		 * 
		 * From options input by user, get Chart data and generate chart
		 * 
		 */
		vm.processOptionsAndGenerateChartData = function(){
			if (vm.chosenTypes.length == 0){
				vm.chosenTypes = vm.types;
			}
			if (_.indexOf(vm.intervalUnits, vm.intervalUnit) == -1){
				console.log('Wrong interval unit');
			}
			// TODO
			var rawChartData = [];
			
			ChartsService.getTimeChartForIntervalAndTypes(vm.intervalUnit, vm.chosenTypes, vm.begin, vm.end, vm.account)
				.then(function(response){
					rawChartData = response.data;
				}, function(response){
					console.log("Error "+response.statusText+" : "+response.data);
				});
			
			vm.generateChartData(rawchartData);
		};
		
		vm.generateChartData = function(raw){
			
			for(var chartIndex in raw){
				
			}			
		};
	        
    }
    
})();