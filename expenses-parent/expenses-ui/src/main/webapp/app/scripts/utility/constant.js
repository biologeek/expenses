/*
 * Contants
 */
'use strict';
var constantsModule = angular.module('myApp');

constantsModule.constant('Constants', {
	timeIntervals: ['MILLISECONDS', 'SECONDS', 'MINUTES', 'HOURS', 'DAYS', 'MONTHS', 'YEARS', 'ERA']
});