(function(){
/*
 * Services
 */
'use strict';
var serviceModule = angular.module('myApp.services', []);

serviceModule.factory('MobileService', ['$http', function($http) {
        return {
            create: function(operation, callbackSuccess, callbackError) {
            	$http.post('/api/mobile/account/'+operation.account+'/operation', operation)
            		.then(callbackSuccess, callbackError);
            },
            update: function(operation, callbackSuccess, callbackError) {
            	$http.put('/api/mobile/account/'+operation.account+'/operation', operation)
        		.then(callbackSuccess, callbackError);
            },
            delete: function(id, callbackSuccess, callbackError){
            	$http.delete('/api/mobile/account/'+operation.account+'/operation/'+id)
        		.then(callbackSuccess, callbackError);
            },
            list: function(callbackSuccess, callbackError){
            	$http.get('/api/mobile/account/'+operation.account+'/operations')
        		.then(callbackSuccess, callbackError);
            },
            get: function(account, id, callbackSuccess, callbackError){
            	$http.get('/api/mobile/account/'+operation.account+'operation/'+id)
        		.then(callbackSuccess, callbackError);
            	
            }
        };
    }]);
})();