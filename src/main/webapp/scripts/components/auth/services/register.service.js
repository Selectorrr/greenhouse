'use strict';

angular.module('greenhouseApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


