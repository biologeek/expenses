(function(){
/*
 * Services
 */
'use strict';
var serviceModule = angular.module('myApp');

serviceModule.factory('MobileService', ['$http', function($http) {
        return {
            create: function(operation, callbackSuccess, callbackError) {
            	$http.post('/expenses/mobile/account/${operation.account}/operation', operation)
            		.then(function(response) {
            			callbackSuccess(response.data);
            		}, function(response) {
						callbackError(response);
            		});
            },
            update: function(operation, callbackSuccess, callbackError) {
            	$http.put('/expenses/mobile/account/'+operation.account+'/operation', operation)
        		.then(function(response) {
        			callbackSuccess(response.data);
        		}, function(response) {
					callbackError(response);
        		});
            },
            delete: function(id, callbackSuccess, callbackError){
            	$http.delete('/expenses/mobile/account/'+operation.account+'/operation/'+id)
        		.then(function(response) {
        			callbackSuccess(response.data);
        		}, function(response) {
					callbackError(response);
        		});
            },
            list: function(callbackSuccess, callbackError){
            	$http.get('/expenses/mobile/account/'+operation.account+'/operations')
        		.then(function(response) {
        			callbackSuccess(response.data);
        		}, function(response) {
					callbackError(response);
        		});
            },
            getOperation: function(account, id, callbackSuccess, callbackError){
            	$http.get('/expenses/mobile/account/'+operation.account+'operation/'+id)
        		.then(function(response) {
        			callbackSuccess(response.data);
        		}, function(response) {
					callbackError(response);
        		});
            	
            },
            getOperationById: function(id, callbackSuccess, callbackError){
            	$http.get('/expenses/mobile/operation/'+id)
        		.then(function(response) {
        			callbackSuccess(response.data);
        		}, function(response) {
					callbackError(response);
        		});            	
            }
        };
    }]);
})();