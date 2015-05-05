'use strict';

angular.module('greenhouseApp')
    .factory('ValueService', function ($resource) {
        return $resource('api/values/?types=:types', {types: '@types'}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.lastModifiedDate = new Date(data.lastModifiedDate);
                    return data;
                }
            },
            'update': {method: 'PUT'}
        });
    });
