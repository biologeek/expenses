(function() {
	/*
	 * Services
	 */
	'use strict';
	var serviceModule = angular.module('myApp');

	serviceModule.factory('CategoryService', [
			'$http',
			function($http) {
				return {
					getCategoryByNomenclature : function(nomenclature,
							callbackSuccess, callbackError) {
						$http.get('/expenses/category/' + nomenclature).then(
								function(response) {
									callbackSuccess(response.data);
								}, function(response) {
									callbackError(response);
								});

					},
					list : function(level) {
						return $http.get('/expenses/category/level/'+level);
					},
					listAll : function() {
						return $http.get('/expenses/category/');
					}
				};
			} ]);
})();