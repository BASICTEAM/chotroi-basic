var app = angular.module("myApp", ["ngRoute"]);

app.controller("categories", function($scope, $http) {

	$scope.list_categories = [];
	$http.get("/json/categories.json").then(function(res) {
		$scope.list_categories = res.data;
	});

	$scope.showval = true;
	$scope.hideval = false;
	$scope.isShowHide = function(param) {
		if (param == "show") {
			$scope.showval = true;
			$scope.hideval = true;
		} else if (param == "hide") {
			$scope.showval = false;
			$scope.hideval = false;
		} else {
			$scope.showval = true;
			$scope.hideval = false;
		}
	};

});
