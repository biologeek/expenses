(function(){
/*
 * Dashboard controller
 */
'use strict';
var controllerModule = angular.module('myApp');

controllerModule.controller('ListLastController', ['$scope', 'MobileService', '$translate', '$cookies', 
	function($scope, MobileService, $translate, $cookies) {
    
	var vm = this;
	
	vm.currentOperation = null;
	

	vm.addOperationHandler = function(){
		
		vm.completeOperation(false);
		vm.validateOperation();
		
		MobileService.create(vm.currentOperation, function(operation){
			console.log('ok');
		}, function(errorCode){
			console.log('Pas ok !!! '+errorCode);
		});
		
	};
	
	vm.updateOperationHandler = function(){
		vm.completeOperation(true);
		vm.validateOperation(true, function(){
			MobileService.update(vm.currentOperation, function(operation){
				console.log('ok');
			}, function(errorCode){
				console.log('Pas ok !!! '+errorCode);
			});
		}, function(errorCode){
			console.log("Validation error : "+errorCode);
		});
		
	};
	
	
	vm.completeOperation = function(isUpdate){
		if (!isUpdate && vm.currentOperation.id){
			vm.currentOperation.id = null;
		}
		vm.currentOperation.account = $cookies.get('account');
		
		
	};
	
	/**
	 * Validates operation regarding specs of an operation.
	 */
	vm.validateOperation = function(isUpdate, callbackSuccess, callbackError){
		if (isUpdate){
			if (!vm.currentOperation.id || vm.currentOperation.id <= 0){
				callbackError('id.null');
			}
		}
		
		if (vm.currentOperation.amount > Constants.MAX_AMOUNT_OPERATION || vm.currentOperation.amount < - Constants.MAX_AMOUNT_OPERATION){
			callbackError('amount.mini.maxi');
		}
		callbackSuccess();
	};
	
    }]);

})();