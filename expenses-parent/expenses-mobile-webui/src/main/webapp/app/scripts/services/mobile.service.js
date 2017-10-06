(function(){
/*
 * Services
 */
'use strict';
var serviceModule = angular.module('myApp');

serviceModule.factory('MobileService', ['$http', '$cookies', function($http, $cookies) {
        return {
            create: function(operation, callbackSuccess, callbackError) {
            	$http.post('/expenses/mobile/account/'+operation.account+'/operation', operation)
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
            deleteOperation: function(id){
            	return $http.delete('/expenses/mobile/account/'+operation.account+'/operation/'+id);
            },
            list: function(limit, pager){
            	var pageParam = pager == null ? null : '/page/'+pager;
            	return $http.get('/expenses/mobile/account/'+$cookies.get('account')+'/operations'+pageParam+'?limit='+limit+'&orderBy=date&reverse=true');        		
            },
            getOperation: function(account, id, callbackSuccess, callbackError){
            	$http.get('/expenses/mobile/account/'+$cookies.get('account')+'/operation/'+id)
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
            },
            getAccount: function(id){
            	return $http.get('/expenses/mobile/account/'+id);
            }
        };
    }]);
})();