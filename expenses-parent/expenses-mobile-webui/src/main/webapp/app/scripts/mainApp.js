(function() {
	'use strict';
	var myApp = angular.module('myApp', [ 'ngRoute', // Route service
		'ngCookies',
		'ngSanitize',
		'ui.bootstrap.datepickerPopup',
		'ui.bootstrap.timepicker',
		'ui.bootstrap.accordion',
		'ui.bootstrap.typeahead',
		'pascalprecht.translate',// angular-translate
        'tmh.dynamicLocale',// angular-dynamic-locale
        'ui.bootstrap.pagination'
	]);

	myApp.config([ '$routeProvider', function($routeProvider) {
		// Login
		$routeProvider.when('/', {
			templateUrl : 'partials/connection.html',
			controller : 'LoginController'
		});

		$routeProvider.when('/add', {
			templateUrl : 'partials/addOrUpdate.html',
			controller : 'AddOrUpdateController'
		});

		$routeProvider.when('/account/:accountId/operation/:opId', {
			templateUrl : 'partials/addOrUpdate.html',
			controller : 'AddOrUpdateController'
		});
		$routeProvider.when('/account/:accountId/operation/add', {
			templateUrl : 'partials/addOrUpdate.html',
			controller : 'AddOrUpdateController'
		});

		$routeProvider.when('/accounts', {
			templateUrl : 'partials/accounts.html',
			controller : 'AccountController'
		});

		$routeProvider.when('/accounts/:userId', {
			templateUrl : 'partials/accounts.html',
			controller : 'AccountController'
		});	

		$routeProvider.when('/account/:accountId/', {
			templateUrl : 'partials/operations.html',
			controller : 'OperationsController'
		});	

		$routeProvider.when('/account/:accountId/operations/list/:limit', {
			templateUrl : 'partials/operations.list.html',
			controller : 'OperationsController'
		});	

		$routeProvider.when('/account/:accountId/operation/:opId/edit', {
			templateUrl : 'partials/addOrUpdate.html',
			controller : 'OperationsController'
		});	

	/*	$routeProvider.otherwise({
			templateUrl : 'partials/connection.html',
			controller : 'LoginController'
		});*/
	} ]);
	
	myApp.config(function ($translateProvider) {
        $translateProvider.useStaticFilesLoader({
            prefix: 'i18n/',// path to translations files
            suffix: '.json'// suffix, currently- extension of the translations
        });
        $translateProvider.preferredLanguage('fr_FR');// is applied on first load
        $translateProvider.useLocalStorage();// saves selected language to localStorage
        $translateProvider.useMissingTranslationHandlerLog();
        $translateProvider.useSanitizeValueStrategy('escape');

    });
	
	myApp.config(function (tmhDynamicLocaleProvider) {
        tmhDynamicLocaleProvider.localeLocationPattern('vendors/angular-i18n/angular-locale_{{locale}}.js');
    });
    
    myApp.run(['$http', '$cookies', function($http, $cookies){
     	$http.defaults.headers.common.Authorization = $cookies.get("token");
     	
	}]);
})();