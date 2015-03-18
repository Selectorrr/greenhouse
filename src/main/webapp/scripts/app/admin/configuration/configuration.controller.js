'use strict';

angular.module('greenhouseApp')
    .controller('ConfigurationController', function ($scope, ConfigurationService) {
        ConfigurationService.get().then(function (configuration) {
            $scope.configuration = configuration;
        });
    });
