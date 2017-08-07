(function() {
	/*
	 * 
	 * Utility
	 */
	'use strict';
	var utilityModule = angular.module('myApp');
/*
	utilityModule.service('utilService', [ '$timeout', '$cookies', function(timeout, $cookies) {

		this.showLoader = function() {
			timeout(function() {
				// sample task to be done after 2000 milli sec
			}, 2000);
		};

		this.dismissProgress = function() {
			timeout(function() {
				// sample task to be done after 2000 milli sec
			}, 2000);
		};
		
		this.addHeaders = function(){
				var headers = [];
				headers.Authorization = $cookies.get('token');
				headers['Content Type'] = "application/json";
				headers.Accept = "application/json";
				headers.user = $cookies.get('user');
				return headers;
		};

	} ]);
	*/
})();