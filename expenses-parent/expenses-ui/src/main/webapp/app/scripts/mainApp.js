(function (){
	'use strict';

var myApp = angular.module('expenses.ui', [
    'ngRoute', // Route service
    'ngCookies', // Cookies service
    'ui.bootstrap',
    'btorfs.multiselect'
]);

myApp.config(['$routeProvider', function($routeProvider) {
        // Login
        $routeProvider.when('/', {
            templateUrl: 'partials/connection.html',
            controller: 'LoginController'
        }).when('/balance/:period', {
            templateUrl: 'partials/balance.html',
            controller: 'ChartsController'
        }).when('/charts', {
            templateUrl: 'partials/charts.html',
            controller: 'ChartsController'
        });
    }]);
})();