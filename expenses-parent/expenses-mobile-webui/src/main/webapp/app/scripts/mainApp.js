(function() {
	'use strict';
	var myApp = angular.module('myApp', [ 'ngRoute', // Route service
		'ngCookies',
		'ngSanitize',
		'pascalprecht.translate',// angular-translate
        'tmh.dynamicLocale'// angular-dynamic-locale
	]);

	myApp.config([ '$routeProvider', function($routeProvider) {
		// Login
		$routeProvider.when('/', {
			templateUrl : 'partials/welcome.html',
			controller : 'WelcomeController'
		});

		$routeProvider.when('/add', {
			templateUrl : 'partials/addOrUpdate.html',
			controller : 'AddOrUpdateController'
		});

		$routeProvider.when('/update/:operationId', {
			templateUrl : 'partials/addOrUpdate.html',
			controller : 'AddOrUpdateController'
		});

		// Default
		$routeProvider.otherwise({
			redirectTo : '/page1'
		});		
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
})();