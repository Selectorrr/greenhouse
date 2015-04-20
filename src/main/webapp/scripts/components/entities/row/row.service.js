'use strict';

angular.module('greenhouseApp')
    .factory('RowService', function ($resource) {
        return $resource('api/rows/?from=:from&to=:to', {from: '@from', to: '@to'}, {
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
