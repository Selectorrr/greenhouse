'use strict';

angular.module('greenhouseApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/main/main.html',
                        controller: 'MainController'
                    }
                },
                resolve: {
                    chartsData:  function (RowService) {
                        return RowService.query().$promise;
                    }
                }
            });
    });
