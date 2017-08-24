(function (){
	'use strict';

var myApp = angular.module('expenses.ui', [
    'ngRoute', // Route service
    'ui.bootstrap',
    'btorfs.multiselect'
]);

myApp.config(['$routeProvider', function($routeProvider) {
        // Login
        $routeProvider.when('/', {
            templateUrl: 'partials/index.html',
            controller: 'WelcomeController'
        }).when('/balance/:period', {
            templateUrl: 'partials/balance.html',
            controller: 'ChartsController'
        }).when('/charts', {
            templateUrl: 'partials/charts.html',
            controller: 'ChartsController'
        });
    }]);
})();