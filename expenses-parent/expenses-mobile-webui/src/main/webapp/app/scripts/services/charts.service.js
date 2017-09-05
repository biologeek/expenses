(function(){
	/*
	 * Services
	 */
	'use strict';
	var serviceModule = angular.module('myApp');
	
	serviceModule.factory('ChartsService', ['$http', '$cookies', function($http, $cookies) {
	        return {
	        
	        	getTimeChartForIntervalAndTypes: function(interval, types, begin, end, account){
	        		if (typeof(account) === 'undefined'){
	        			account = $cookies.get('accountId');
	        		}
	        		
	        		return $http.get('/expenses/charts/daily/account/'+account+'/interval/'+interval+'?begin='+begin+'&end='+end+'&types='+types);
	        	},
	        	getPieChartForTypes: function(types, begin, end, account){
	        		if (typeof(account) === 'undefined'){
	        			account = $cookies.get('accountId');
	        		}
	        		
	        		return $http.get('/expenses/charts/daily/account/'+account+'?begin='+begin+'&end='+end+'&types='+types);
	        	}
	        }
	}]);
})();