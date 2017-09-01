(function(){
	/*
	 * Services
	 */
	'use strict';
	var serviceModule = angular.module('expenses.ui');
	
	serviceModule.factory('OperationService', ['$http', '$cookies', function($http, $cookies) {
	        return {	        
	        	getTypes: function(){
	        		return $http.get('/expenses/operation/types');
	        	}
	        };
	}]);
})();