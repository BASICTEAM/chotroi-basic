"use strict";

var app = angular
    .module("myApp.statistics", ["ngRoute"])

.config([
    "$routeProvider",
    function($routeProvider) {
        $routeProvider.when("/statistics", {
            templateUrl: "views/statistics/statistic.html",
            controller: "statisticCtrl",
        });
    },
])

.directive("fileModel", [
    "$parse",
    function($parse) {
        return {
            restrict: "A",
            link: function(scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind("change", function() {
                    scope.$apply(function() {
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            },
        };
    },
])

.directive("date", function(dateFilter) {
    return {
        require: "ngModel",
        link: function(scope, elm, attrs, ctrl) {
            var dateFormat = attrs["date"] || "yyyy-MM-dd";

            ctrl.$formatters.unshift(function(modelValue) {
                return dateFilter(modelValue, dateFormat);
            });
        },
    };
})

.controller("statisticCtrl", [
    "postingFactory",
    "$scope",
    "$http",
    function(postingFactory, $scope, $http) {
        $scope.postings = [];
        $scope.posting = {};
        $scope.obj = {};
        $scope.arr = [];

        $scope.myForm = {
            description: "",
            files: [],
        };

        $scope.pageSize = 5;
        $scope.begin = 0;
        $scope.pageCount = Math.ceil($scope.postings.length / $scope.pageSize);

        $scope.pageCountMax = 0;
        $scope.index = 0;

        $scope.repaginate = function() {
            $scope.begin = 0;
            $scope.pageCount = 0;
            $scope.index = 1;
            $scope.pageCountMax =
                Math.round($scope.postings.length / $scope.pageSize) + 1;
        };

        $scope.first = function() {
            $scope.begin = 0;
            if ($scope.pageCountMax > 0) {
                $scope.index = 1;
            }
        };

        $scope.previous = function() {
            if ($scope.begin > 0) {
                $scope.begin -= $scope.pageSize;
                if ($scope.pageCountMax > 0) {
                    $scope.index--;
                }
            }
        };

        $scope.next = function() {
            if (
                $scope.begin <
                Math.round($scope.postings.length / $scope.pageSize) * $scope.pageSize
            ) {
                $scope.begin += $scope.pageSize;
                if ($scope.pageCountMax > 0) {
                    $scope.index++;
                }
            }
        };

        $scope.last = function() {
            $scope.begin =
                Math.round($scope.postings.length / $scope.pageSize) *
                $scope.pageSize;
            if ($scope.pageCountMax > 0) {
                $scope.index = $scope.pageCountMax;
            }
        };

        postingFactory.getPostings().then(
            (data) => {
                $scope.postings = data;
                $scope.arr = data;
                $scope.pageCount = Math.ceil(
                    $scope.postings.length / $scope.pageSize
                );
                $scope.pageCountMax =
                    Math.round($scope.postings.length / $scope.pageSize) + 1;
                if ($scope.pageCountMax > 0) {
                    $scope.index = 1;
                }
            },
            (reason) => {
                console.log(reason);
            }
        );

        $scope.obj.check = function() {
            console.log("called me", $scope.fromDate);
            var d1 = new Date($scope.fromDate);
            var d2 = new Date($scope.toDate);
            if (d1.getTime() > d2.getTime()) {
                console.log("error");
            } else {
                $scope.postings = [];
                for (const i in $scope.arr) {
                    var cmp = new Date($scope.arr[i].date);
                    console.log("called me d1", d1.getTime());
                    console.log("called me d2", d2.getTime());
                    console.log("called me cmp", cmp.getTime());
                    if (d1.getTime() <= cmp.getTime() && d2.getTime() >= cmp.getTime()) {
                        console.log("Val in between");
                        $scope.postings.push($scope.arr[i]);
                    }
                }
            }
        };

        $scope.getPosting = function(id) {
            postingFactory.getPosting(id).then(
                (data) => {
                    $scope.posting = data;
                },
                (reason) => {
                    console.log(reason);
                }
            );
        };

        $scope.update = function(id) {
            postingFactory
                .updateStatusPosting(id, $scope.posting.status, $scope.posting)
                .then(
                    (data) => {
                        $scope.posting = data;
                        alert("Update status posting successfully!");
                        location.reload();
                    },
                    (reason) => {
                        alert("Update status posting failed!\nError: " + reason);
                    }
                );
        };

        $scope.delete = function(id) {
            postingFactory.deletePosting(id).then(
                (data) => {
                    $scope.posting = data;
                    alert("Delete posting successfully!");
                    location.reload();
                },
                (reason) => {
                    alert("Delete posting failed!\nError: " + reason);
                    location.reload();
                }
            );
        };

        $scope.block = function(id) {
            postingFactory.blockPosting(id, $scope.posting).then(
                (data) => {
                    $scope.posting = data;
                    alert("Block posting successfully!");
                    location.reload();
                },
                (reason) => {
                    alert("Block posting failed!\nError: " + reason);
                    location.reload();
                }
            );
        };

        $scope.doUploadFile = function() {
            var url = "http://localhost:8080/rest/uploadMultiFiles";

            var data = new FormData();

            data.append("description", "upload");
            data.append("files", $scope.myForm.files[0]);

            var config = {
                transformRequest: angular.identity,
                transformResponse: angular.identity,
                headers: {
                    "Content-Type": undefined,
                },
            };

            $http.post(url, data, config);
        };

        $scope.propertyName = "name";
        $scope.sortReverse = false;

        $scope.sortBy = function(propertyName) {
            $scope.reverse =
                $scope.propertyName === propertyName ? !$scope.reverse : false;
            $scope.propertyName = propertyName;
        };
    },
]);