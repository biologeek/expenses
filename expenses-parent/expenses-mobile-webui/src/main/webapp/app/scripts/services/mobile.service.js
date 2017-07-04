(function(){
/*
 * Services
 */
'use strict';
var serviceModule = angular.module('myApp');

serviceModule.factory('MobileService', ['$http', function($http) {
        return {
            create: function(operation, callbackSuccess, callbackError) {
            	$http.post('/mobile/account/'+operation.account+'/operation', operation)
            		.then(callbackSuccess(response.data), callbackError(response));
            },
            update: function(operation, callbackSuccess, callbackError) {
            	$http.put('/mobile/account/'+operation.account+'/operation', operation)
        		.then(callbackSuccess, callbackError);
            },
            delete: function(id, callbackSuccess, callbackError){
            	$http.delete('/mobile/account/'+operation.account+'/operation/'+id)
        		.then(callbackSuccess, callbackError);
            },
            list: function(callbackSuccess, callbackError){
            	$http.get('/mobile/account/'+operation.account+'/operations')
        		.then(callbackSuccess, callbackError);
            },
            getOperation: function(account, id, callbackSuccess, callbackError){
            	$http.get('/mobile/account/'+operation.account+'operation/'+id)
        		.then(callbackSuccess, callbackError);
            	
            },
            getOperationById: function(id, callbackSuccess, callbackError){
            	$http.get('/mobile/operation/'+id)
        		.then(callbackSuccess, callbackError);
            	
            }
        };
    }]);
})();