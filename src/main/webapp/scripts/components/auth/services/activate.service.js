'use strict';

angular.module('greenhouseApp')
    .factory('Activate', function ($resource) {
        return $resource('api/activate', {}, {
            'get': {method: 'GET', params: {}, isArray: false}
        });
    });


