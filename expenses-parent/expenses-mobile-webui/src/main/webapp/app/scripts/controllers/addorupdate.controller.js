/*jshint esversion: 6 */
(function() {
	/*
	 * Dashboard controller
	 */
	'use strict';
	var controllerModule = angular.module('myApp');

	controllerModule.controller('AddOrUpdateController', [
			'$scope',
			'CategoryService',
			'MobileService',
			'$routeParams',
			function($scope, CategoryService, MobileService, $routeParams) {

				var vm = this;

				vm.errors = [];
				vm.currentOperation = null;
				/**
				 * Selected items
				 */
				vm.nomenc = new Array(4);
				vm.nomencInNumbers = new Array(4);
				/**
				 * Stores categories displayed in each field;
				 */
				vm.categoryLevels = new Array(4);
				vm.show = new Array(4);

				vm.categoryTypes = [];
				
				vm.datePopup = {};
				vm.popupDate = new Date();
				vm.popupTime = new Date();
				/**
				 * Returns categories for certain level
				 */
				vm.getSubCategories = function(level) {
					var selectedItem = vm.nomenc[level];
					if (selectedItem == null){
						vm.show[level + 1] = false;
						vm.nomencInNumbers[level] = null;
						return;
					}
				
					vm.categoryLevels[level+1] = [];
					// Call service
					CategoryService.getCategoryByNomenclature(
							vm.nomenc[level].nomenclature, function(listOfCats) {
								// Add retrieved categories to list and show
								// corresponding select item
								vm.categoryLevels[level+1] = listOfCats;
								vm.show[level+1] = true;
							}, function(error) {
								vm.show[level+1] = false;
								console.log(error.message);
							});
				};
				
				vm.saveOrUpdateOperation = function(form){
					// Check validity of form
					if (getCategory() != null){
						vm.currentOperation.category = getCategory();
						vm.currentOperation.effectiveDate = extractDate();
						if (vm.currentOperation.id){
							MobileService.update(vm.currentOperation, function(data) {
								vm.currentOperation = data;
							}, function(response) {
								vm.errors.push(response.data);
							});
						} else {
							MobileService.create(vm.currentOperation, function(data) {
								vm.currentOperation = data;
							}, function(response) {
								vm.errors.push(response.data);
							});
						}
					} else {
						vm.errors.push("error.missing.category");
					}					
				};
				
				var extractDate = function(){
					var date = vm.datePopup;
					
					date.setHours(vm.timePopup.getHours());
					date.setMinutes(vm.timePopup.getMinutes());
					return date;
				};
				
				var getCategory = function (){
					var lastCat = null;
					for(var category in vm.nomenc){
						if (vm.nomenc[category]){
							lastCat = vm.nomenc[category];
						}
					}
					return lastCat;
				};

				/*
				 * EXECUTED AT PAGE LOAD
				 */

				vm.initUpdate = function() {
					MobileService.getOperationById($routeParams.opId, function(operation) {
						vm.currentOperation = operation;
						// Feeds first category select
						vm.reverseCategoryTree(operation.nomenclature);
						
					}, function(error) {
						// TODO
					});
				};

				vm.initCreate = function() {
					CategoryService.list(0).then(function(categoryList){
						vm.categoryLevels[0] = categoryList;
						console.log(categoryList);
					}).then(function(error){
						console.log(error);
					});
					console.log("Blabla");

				};
				
				vm.reverseCategoryTree = function(categories){
					vm.nomenc = _.reverse(categories);
					for (let i = 0; i < vm.nomenc.length;i++){
						
						CategoryService.list(i).then(function(resp){
							vm.categoryLevels[i] = resp.data;
							vm.show[i] = true;
						});
					}
					vm.getSubCategories(vm.nomenc.length - 1);
				};
				
				CategoryService.getTypes(function(data) {
					vm.categoryTypes = data;
				}, function(response) {
					console.log(response.data);
				});	
				
				if ($routeParams.accountId && $routeParams.opId) {
					vm.initUpdate();
				} else {
					vm.initCreate();
				}
				
				
				vm.openDatePopup = function(){
					vm.datePopup.opened = true;				
				};
				
				// Disable weekend selection
			  	function disabled(data) {
			    	var date = data.date,
			      	mode = data.mode;
			   		return false;//mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
			  	}	
			  	function getDayClass(data) {
					var date = data.date,
					  mode = data.mode;
					if (mode === 'day') {
					  var dayToCheck = new Date(date).setHours(0,0,0,0);
					
					  for (var i = 0; i < $scope.events.length; i++) {
					    var currentDay = new Date(vm.events[i].date).setHours(0,0,0,0);
					
					    if (dayToCheck === currentDay) {
					      return $scope.events[i].status;
					    }
					  }
					}
					return '';
				}
			  	vm.inlineOptions = {
					customClass: getDayClass,
					showWeeks: true
				};
				var tomorrow = new Date();
				tomorrow.setDate(tomorrow.getDate() + 1);
				var afterTomorrow = new Date();
				afterTomorrow.setDate(tomorrow.getDate() + 1);
				
				vm.events = [
			    
		  		];
							  	
			  	vm.datePopupOptions = {
			  		dateDisabled: disabled,
			    	formatYear: 'yy',
			    	maxDate: new Date(2020, 5, 22),
			    	minDate: new Date(),
			    	startingDay: 1
			  	};
			  	
			  	vm.timeOptions = {
    				hstep: [1, 2, 3],
    				mstep: [1, 5, 10, 15, 25, 30]
  				};
  				
			  				
			  vm.hstep = 0.5;
			  vm.mstep = 0.5;
			} ]);
})();