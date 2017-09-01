(function(){/*
	 * Contants
	 */
	'use strict';
	var constantsModule = angular.module('expenses.ui');
	
	constantsModule.constant('Constants', {
		timeIntervals: ['MILLISECONDS', 'SECONDS', 'MINUTES', 'HOURS', 'DAYS', 'MONTHS', 'YEARS', 'ERA']
	});
})();