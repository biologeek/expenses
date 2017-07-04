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

				vm.currentOperation = null;
				/**
				 * Selected items
				 */
				vm.nomenc = [];
				vm.nomencInNumbers = [];
				/**
				 * Stores categories displayed in each field;
				 */
				vm.categoryLevels = new Array(4);
				vm.show = [];


				/**
				 * Returns categories for certain level
				 */
				vm.getSubCategories = function(level) {
					var selectedItem = vm.nomenc[level];
					// Call service
					CategoryService.getCategoryByNomenclature(
							vm.nomenc[level].nomenclature, function(listOfCats) {
								// Add retrieved categories to list and show
								// corresponding select item
								if (!vm.categoryLevels[level+1]){
									vm.categoryLevels[level+1] = listOfCats;
								}
								vm.show[level+1] = true;
							}, function(error) {
								vm.show[level+1] = false;
								console.log(error.message);
							});
				};
				
				vm.saveOrUpdateOperation = function(form){
					// Check validity of form
					if (form.$valid()){						
						vm.currentOperation.category = getCategory().id;	
						
					}
					
					
				};
				
				var getCategory = function (){
					var lastCat = null;
					for(var category in vm.nomenc){
						if (category){
							lastCat = category;
						}
					}
					return lastCat;
				}

				/*
				 * EXECUTED AT PAGE LOAD
				 */

				vm.initUpdate = function() {
					MobileService.getOperationById($routeParams.operationId, function(
							operation) {
						vm.currentOperation = operation;
						// Feeds first category select
						vm.nomenc[0] = operation.category;
					}, function(error) {
						// TODO
					});
				};

				vm.initCreate = function() {
					CategoryService.list(0, function(categoryList){
						vm.categoryLevels[0] = categoryList;
						console.log(categoryList);
					}, function(error){
						console.log(error);
					});
					console.log("Blabla");

				};
				
				
				CategoryService.getTypes(function(data) {
					vm.categoryTypes = data;
				}, function(response) {
					console.log(response.data);
				});	
				
				if ($routeParams.operationId) {
					vm.initUpdate();
				} else {
					vm.initCreate();
				}

			} ]);
})();