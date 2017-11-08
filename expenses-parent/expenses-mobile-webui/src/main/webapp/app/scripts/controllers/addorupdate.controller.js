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
			'OperationService',
			'MobileService',
			'$routeParams',
			'$location',
			'$translate',
			function($scope, CategoryService, OperationService, MobileService, $routeParams, $location, $translate) {

				var vm = this;

				vm.errors = [];
				vm.currentOperation = null;
				/**
				 * Selected category item
				 */
				vm.nomenc = {};
				
				vm.categoryLevels = [];

				vm.categoryTypes = [];
				vm.categoryTypesNames = [];
				
				vm.datePopup = {};
				vm.popupDate = new Date();
				vm.popupTime = new Date();
				vm.datePopup = new Date();
				vm.timePopup = new Date();
				vm.computedDate = new Date();
				vm.isOpenDatePanel = false;
				vm.isOpenAmountPanel = true;
				vm.typeName = "";
				/**
				 * Returns categories for certain level
				 */
				vm.getSubCategory = function(level) {
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
					vm.currentOperation.account = parseInt($routeParams.accountId);
					
					var cat = getCategory();
					if (cat != null){
						vm.currentOperation.category = getCategory();
						rebuildNomenclature(function(){
							vm.currentOperation.effectiveDate = vm.extractDate();
							vm.currentOperation.effectiveDate = vm.currentOperation.effectiveDate.getTime();
							var isInt = parseInt($routeParams.opId); 
							if ($routeParams.accountId && $routeParams.opId && Number.isInteger(isInt)){
								MobileService.update(vm.currentOperation, function(data) {
									vm.currentOperation = data;
									$location.path('/account/'+$routeParams.accountId+'/operations/list/20');
								}, function(response) {
									vm.errors.push(response.data);
								});
							} else {
								MobileService.create(vm.currentOperation, function(data) {
									vm.currentOperation = data;									
									$location.path('/account/'+$routeParams.accountId+'/operations/list/20');
								}, function(response) {
									vm.errors.push(response.data);
								});
							}
						});
					} else {
						vm.errors.push("error.missing.category");
					}					
				};
				
				vm.setTypeNameAndProperties = function(){
					vm.currentOperation.type = _.find(vm.categoryTypes, function(o){
						return vm.typeName == o.name;
					});
					
					if (vm.currentOperation.type.regular){
						vm.currentOperation.interval = {
							interval : 1,
							unit : 'MONTH',
							first : new Date(),
							last : new Date(),
						};
					}
				};
				
				
				vm.extractDate = function(){
					var date = vm.datePopup;
					
					date.setHours(vm.timePopup.getHours());
					date.setMinutes(vm.timePopup.getMinutes());
					
					vm.computedDate = date;
					return date;
				};
				
				vm.changeOperationTypeAndAmountSign = function(){
					vm.setTypeNameAndProperties();
					vm.currentOperation.amount = Math.abs(vm.currentOperation.amount) * vm.currentOperation.type.sign;
				};
				var getCategory = function (){
					
					return _.find(vm.categoryLevels, function(o){
						return o.name == vm.nomenc;
					});
				};
				
				//vm.changeFieldsToReadOnlyIfNecesary = function(isModifiable){
				//	if (!isModifiable){
				//		var elts = document.querySelector(".modifiable-element");
				//		for (var i = 0 ; i < elts.length ; i++){
				//			angular.element(elts[i]).attr("readonly", "readonly");
				//		}
				//	}
				//};

				/*
				 * EXECUTED AT PAGE LOAD
				 */

				vm.initUpdate = function() {
					MobileService.getOperationById($routeParams.opId, function(operation) {
						vm.currentOperation = operation;
						// Feeds first category select
						vm.nomenc = operation.category.name;
						vm.typeName = vm.currentOperation.type.name;
						console.log(vm.currentOperation);
						vm.datePopup = new Date(vm.currentOperation.effectiveDate);
						vm.timePopup = new Date(vm.currentOperation.effectiveDate);
						vm.popupDate = new Date(vm.currentOperation.effectiveDate);
						vm.popupTime = new Date(vm.currentOperation.effectiveDate);
						//vm.changeFieldsToReadOnlyIfNecesary(operation.modifiable);
					}, function(error) {
						// TODO
					});
				};

				vm.initCreate = function() {
					CategoryService.listAll().then(function(categoryList){
						vm.categoryLevels = categoryList.data;
						console.log(categoryList);
					}).then(function(error){
						console.log(error);
					});
					console.log("Blabla");

				};
				
				
				OperationService.getTypes().then(function(response) {
					vm.categoryTypes = response.data;
					vm.categoryTypesNames = _.map(vm.categoryTypes, 'name');
				}).catch(function(response) {
					console.log(response.data);
				});	
				
				/**
				 * Rebuilds the nomenclature of the operation when saving or updating 
				 *
				 */
				var rebuildNomenclature = function(callback){
						var currentOperationNomenclature = [];
						var nomenclatureStr = _.find(vm.categoryLevels, function(o){
								return o.name == vm.nomenc;
						}).nomenclature;
						vm.currentOperation.nomenclature = [];
						var nomencArray = _.split(nomenclatureStr, '-');
						
						var rebuiltString = nomencArray[0];
						_.forEach(nomencArray, function(o){
							if(rebuiltString != o ){
								rebuiltString = rebuiltString + "-" + o;
							}
							
							var foundCategory = _.find(vm.categoryLevels, function(p){
										return rebuiltString == p.nomenclature;
										});	
							
							currentOperationNomenclature.push(foundCategory);				
						});
						vm.currentOperation.nomenclature = currentOperationNomenclature;
						callback();
				};
				
				
				CategoryService.listAll().then(function(categoryList){
					vm.categoryLevels = categoryList.data;
				}).then(function(error){
					console.log(error);
				});
				if ($routeParams.accountId && $routeParams.opId && Number.isInteger(parseInt($routeParams.opId))) {
					vm.initUpdate();
				} else {
					vm.initCreate();
				}
				
				
				vm.goToPrevious = function(){
					$location.path('/account/'+$routeParams.accountId+'/operations/list/20');
				};
				
				/*
				 * Date and time widgets configuration
				 */
				
				
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