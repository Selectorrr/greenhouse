'use strict';

angular.module('greenhouseApp')
    .factory('RowService', function ($resource) {
        return $resource('api/rows/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateTime = new Date(data.dateTime);
                    return data;
                }
            },
            'update': {method: 'PUT'}
        });
    });
