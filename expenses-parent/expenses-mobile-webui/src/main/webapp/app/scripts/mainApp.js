(function(){
'use strict';
var myApp = angular.module('myApp', ['ngRoute', // Route service
    'myApp.utilityService', 'myApp.constants', // MISC
    'myApp.directives', 'myApp.filters', // MISC
    'myApp.services', // Services
    'myApp.controller' // controller
]);

myApp.config(['$routeProvider', function($routeProvider) {
        // Login
    $routeProvider.when('/', {
        templateUrl: 'partials/welcome.html',
        controller: 'WelcomeController'
    });

    $routeProvider.when('/add', {
        templateUrl: 'partials/addOrUpdate.html',
        controller: 'AddOrUpdateController'
    });

    $routeProvider.when('/update', {
        templateUrl: 'partials/addOrUpdate.html',
        controller: 'AddOrUpdateController'
    });

    $routeProvider.when('/', {
        templateUrl: 'partials/welcome.html',
        controller: 'WelcomeController'
    });
    
        // Default
        $routeProvider.otherwise({
            redirectTo: '/page1'
        });
    }]);
})();