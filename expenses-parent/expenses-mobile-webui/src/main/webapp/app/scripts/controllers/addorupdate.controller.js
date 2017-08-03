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


				/**
				 * Returns categories for certain level
				 */
				vm.getSubCategories = function(level) {
					var selectedItem = vm.nomenc[level];
				
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
					console.log('Blabalrzerzer');
					// Check validity of form
					if (getCategory() != null){
						vm.currentOperation.category = getCategory().id;
						MobileService.create(vm.currentOperation, function(data) {
							vm.currentOperation = data;
						}, function(response) {
							vm.errors.push(response.data);
						});
					} else {
						vm.errors.push("error.missing.category");
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
				};

				/*
				 * EXECUTED AT PAGE LOAD
				 */

				vm.initUpdate = function() {
					MobileService.getOperationById($routeParams.opId, function(operation) {
						vm.currentOperation = operation;
						// Feeds first category select
						vm.reverseCategoryTree(operation.category);
						
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
				
				vm.reverseCategoryTree = function(category){
					var currentCategory = _.cloneDeep(category);
					var final = [];
					// FIXME : Got a problem here !!
					// Need to do synchrone loop
						while(typeof currentCategory !== "undefined"){
							CategoryService.list(currentCategory.level).then(function(categoryList){
								var level = categoryList[0].level;
								vm.categoryLevels[level] = [];
								vm.categoryLevels[level].push(categoryList);
							}).then(function(error){
								console.log(error);
							});
							
							final.push(currentCategory);
							currentCategory = currentCategory.parent;
						}					
					
					vm.nomenc = _.reverse(final);
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

			} ]);
})();