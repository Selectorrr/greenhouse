'use strict';

angular.module('greenhouseApp')
    .factory('Item', function ($resource) {
        return $resource('api/items/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.title = new Date(data.title);
                    data.createDate = new Date(data.createDate);
                    data.modifiedDate = new Date(data.modifiedDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
