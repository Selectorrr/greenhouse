/* globals $ */
'use strict';

angular.module('greenhouseApp')
    .directive('showValidation', function () {
        return {
            restrict: 'A',
            require: 'form',
            link: function (scope, element) {
                element.find('.form-group').each(function () {
                    var $formGroup = $(this);
                    var $inputs = $formGroup.find('input[ng-model],textarea[ng-model],select[ng-model]');

                    if ($inputs.length > 0) {
                        $inputs.each(function () {
                            var $input = $(this);
                            scope.$watch(function () {
                                return $input.hasClass('ng-invalid') && $input.hasClass('ng-dirty');
                            }, function (isInvalid) {
                                $formGroup.toggleClass('has-error', isInvalid);
                            });
                        });
                    }
                });
            }
        };
    }).directive('myDatePicker', function () {
        return {
            restrict: 'A',
            scope: {
                myDatePickerMinDate: '='
            },
            link: function (scope, element) {
                var format = 'DD.MM.YYYY HH:mm';
                element.bootstrapMaterialDatePicker({format: format, lang: 'ru', weekStart: 1});
                var $dtp = $('#' + element.attr('data-dtp'));
                $dtp.find('.dtp-btn-cancel').html('Отмена');
                $dtp.find('.dtp-btn-ok').html('ОК');
                scope.$watch('myDatePickerMinDate', function (val) {
                    if (val) {
                        var date = moment(val, format);
                        element.bootstrapMaterialDatePicker('setMinDate', date);
                    }
                });

            }
        };
    });
