'use strict';

angular.module('greenhouseApp')
    .controller('MainController', function ($scope, RowService, $interval) {
        $scope.charts = [];
        $scope.updateInterval = 360;
        var commonConfig = {
            options: {
                chart: {
                    zoomType: 'x'
                },
                tooltip: {
                    style: {
                        padding: 10,
                        fontWeight: 'bold'
                    }
                },
                plotOptions: {
                    area: {
                        //fillColor: {
                        //  linearGradient: {x1: 0, y1: 0, x2: 0, y2: 1},
                        //  stops: [
                        //    [0, Highcharts.getOptions().colors[0]],
                        //    [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                        //  ]
                        //},
                        marker: {
                            radius: 2
                        },
                        lineWidth: 1,
                        states: {
                            hover: {
                                lineWidth: 1
                            }
                        },
                        threshold: null
                    }
                }
            },
            //subtitle: {
            //  text: document.ontouchstart === undefined ?
            //    'Click and drag in the plot area to zoom in' :
            //    'Pinch the chart to zoom in'
            //},
            loading: false,
            xAxis: {
                type: 'datetime',
                dateTimeLabelFormats: { // don't display the dummy year
                    month: '%e. %b',
                    year: '%b'
                },
                title: {
                    text: 'Время'
                }
            },
            legend: {
                enabled: false
            }
        };

        function getRequestParams() {
            return {
                from: $scope.startDate ? moment($scope.startDate, 'DD.MM.YYYY HH:mm').toISOString() : null,
                to: $scope.endDate ? moment($scope.endDate, 'DD.MM.YYYY HH:mm').toISOString() : null
            };
        }

        function load() {
            RowService.query(getRequestParams()).$promise.then(function (result) {
                $scope.chartsData = result;
                $scope.charts = [];
                for (var i = 0; i < $scope.chartsData.length; i++) {
                    $scope.charts.push($.extend({}, commonConfig, {
                        title: {
                            text: $scope.chartsData[i].name
                        },
                        yAxis: {
                            title: {
                                text: "Значение"
                            }
                        },
                        series: [{
                            type: 'area',
                            name: 'Влажность',
                            data: $scope.chartsData[i].wetness
                        }, {
                            type: 'area',
                            name: 'Температура',
                            data: $scope.chartsData[i].temperature
                        }]
                    }));
                }
            });
        }

        load();
        var stop = $interval(load, $scope.updateInterval * 1000);
        $scope.$watch('updateInterval', function (val) {
            if (angular.isDefined(stop)) {
                $interval.cancel(stop);
                stop = $interval(load, val * 1000);
            }
        });
    });
