(function(){
/*
 * Dashboard controller
 */
'use strict';
var controllerModule = angular.module('myApp.controller', []);

controllerModule.controller('AddOrUpdateController', ['$scope, MobileService', function($scope, mobileService) {
    
	var vm = this;
	
	vm.currentOperation = null;
	

	vm.addOperationHandler = function(){
		vm.validateOperation();
		
		mobileService.create(vm.currentOperation, function(operation){
			console.log('ok');
		}, function(errorCode){
			console.log('Pas ok !!! '+errorCode);
		});
		
	};
	
	vm.updateOperationHandler = function(){
		vm.validateOperation(true, function(){
			mobileService.update(vm.currentOperation, function(operation){
				console.log('ok');
			}, function(errorCode){
				console.log('Pas ok !!! '+errorCode);
			});
		}, function(errorCode){
			console.log("Validation error : "+errorCode);
		});
		
	};
	
	/**
	 * Validates operation regarding specs of an operation.
	 */
	vm.validateOperation = function(isUpdate, callbackOk, CALLBACKKo){
		if (isUpdate){
			if (!vm.currentOperation.id || vm.currentOperation.id <= 0){
				CALLBACKKo('id.null');
			}
		}
		
		if (vm.currentOperation.amount > Constants.MAX_AMOUNT_OPERATION || vm.currentOperation.amount < - Constants.MAX_AMOUNT_OPERATION){
			CALLBACKKo('amount.mini.maxi');
		}
		
		
		callbackOk();
	};
	
    }]);

})();