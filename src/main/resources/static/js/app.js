var app = angular.module("myApp", ["ngRoute"])

	.factory("posFactory", function($http) {
		var posFactory = {};
		var postingDetails = [];
		var products = [];
		var postings = [];

		posFactory.getProducts = function() {
			var promise = $http
				.get("/products-type/find-all")
				.then((respone) => {
					products = respone.data;
					return products;
				})
				.catch((reason) => console.log(reason));
			return promise;
		};

		posFactory.getPostingDetails = function(url) {
			var promise = $http
				.get("/postingdetails/" + url)
				.then((respone) => {
					postingDetails = respone.data;
					return postingDetails;
				})
				.catch((reason) => console.log(reason));
			return promise;
		};
		
		posFactory.getPostingDetail = function(id) {
			var promise = $http
				.get('/' + id + '/get-postingdetail')
				.then((respone) => {
					postingDetails = respone.data;
					return postingDetails;
				})
				.catch((reason) => console.log(reason));
			return promise;
		};
		
		posFactory.getPostingsByProductID = function(id) {
			var promise = $http
				.get('/postings/' + id + '/find-by-product')
				.then((respone) => {
					postings = respone.data;
					return postings;
				})
				.catch((reason) => console.log(reason));
			return promise;
		};

		return posFactory;
	})

	.controller("posting", ["$scope", "$http", "$window", "posFactory",
		function($scope, $http, $window, posFactory) {

			$scope.list_product = [];

			posFactory.getProducts().then(
				data => {
					$scope.list_product = data;
				}, reason => {
					console.log(reason);
				}
			);

			$scope.search = function() {
				$window.location.href = "/search?keyword=" + $scope.keyword;
			};

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

		}])

	.controller("postingDetails", ["$scope", "$http", "$window", "posFactory",
		function($scope, $http, $window, posFactory) {
			$scope.list_postingDetals = [];

			$scope.pageSize = 5;
			$scope.begin = 0;
			$scope.pageCount = Math.ceil($scope.list_postingDetals.length / $scope.pageSize);

			$scope.pageCountMax = 0;
			$scope.index = 0;

			var url = window.location.href;
			var arr = url.split("/");
			var resultUrl = arr[3];
			console.log('resultUrl: ' + resultUrl);

			posFactory.getPostingDetails(resultUrl).then(
				data => {
					$scope.list_postingDetals = data;
				}, reason => {
					console.log(reason);
				}
			)

			$scope.goPostingDetail = function(id) {
				console.log('/' + id);
				$window.location.href = "/" + id;
			}

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
					Math.round($scope.list_postingDetals.length / $scope.pageSize) * $scope.pageSize
				) {
					$scope.begin += $scope.pageSize;
					if ($scope.pageCountMax > 0) {
						$scope.index++;
					}
				}
			};

			$scope.last = function() {
				$scope.begin =
					Math.round($scope.list_postingDetals.length / $scope.pageSize) * $scope.pageSize;
				if ($scope.pageCountMax > 0) {
					$scope.index = $scope.pageCountMax;
				}
			};

		}])
		
	.controller("postingDetail", ["$scope", "$http", "$window", "posFactory", 
		function($scope, $http, $window, posFactory) {
			$scope.postingdetail = {};
			$scope.postings = [];
			
			var url = window.location.href;
			var arr = url.split("/");
			var resultUrl = arr[3];
			var numsStr = resultUrl.replace(/[^0-9]/g,'');

			posFactory.getPostingDetail(numsStr).then(
				data=>{
					$scope.postingdetail = data;
					console.log($scope.postingdetail.posting.product.id);
					$scope.getPostings($scope.postingdetail.posting.product.id);
				}, reason =>{
					console.log(reason);
				}
			)
			
			$scope.getPostings = function(id){
				posFactory.getPostingsByProductID(id).then(
				data=>{
					$scope.postings = data;
				}, reason =>{
					console.log(reason);
				}
			)
			}

		}]);