var app = angular.module("myApp", ["ngRoute"]);

app.controller("posting", function($scope, $http) {

	$scope.list_product = [];
	
	$http.get("/products-type/find-all").then(function(res) {
		$scope.list_product = res.data;
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
