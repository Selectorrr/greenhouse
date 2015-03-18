'use strict';

angular.module('greenhouseApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
