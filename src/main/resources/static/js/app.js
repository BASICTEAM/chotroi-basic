var app = angular.module("myApp", ['ngRoute', 'ui.bootstrap'])

.factory("posFactory", function($http) {
    var posFactory = {};
    var postingDetails = [];
    var products = [];
    var postings = [];
    var locals = [];

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

    posFactory.getPostingsByUser = function(id) {
        var promise = $http
            .get('/postings/' + id + '/find-by-user')
            .then((respone) => {
                postings = respone.data;
                return postings;
            })
            .catch((reason) => console.log(reason));
        return promise;
    };

    posFactory.getPostingsTop16SortDate = function() {
        var promise = $http
            .get('/postings/find-top16-sort-date')
            .then((respone) => {
                postings = respone.data;
                return postings;
            })
            .catch((reason) => console.log(reason));
        return promise;
    };

    posFactory.getPostingDetailsByProduct = function(url) {
        var promise = $http
            .get("/postings-detail-by-product/" + url)
            .then((respone) => {
                postingDetails = respone.data;
                return postingDetails;
            })
            .catch((reason) => console.log(reason));
        return promise;
    };

    posFactory.getPostingDetailsByProductType = function(url) {
        var promise = $http
            .get("/postings-detail-by-product-type/" + url)
            .then((respone) => {
                postingDetails = respone.data;
                return postingDetails;
            })
            .catch((reason) => console.log(reason));
        return promise;
    };

    posFactory.getLocals = function() {
        var promise = $http
            .get("/js/local.json")
            .then((respone) => {
                products = respone.data;
                return products;
            })
            .catch((reason) => console.log(reason));
        return promise;
    };

    return posFactory;
})

.controller("posting", ["$scope", "$http", "$window", "posFactory",
    function($scope, $http, $window, posFactory) {

        $scope.list_product = [];
        $scope.error = false;

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

    }
])

.controller("postingDetails", ["$scope", "$http", "$window", "posFactory", "$uibModal",
    function($scope, $http, $window, posFactory) {
        $scope.list_postingDetals = [];
        $scope.product = "Danh mục";
        $scope.keyword = "";

        // Table & Pagination
        // PAGINATION
        $scope.currentPage = 1;
        $scope.itemsPerPage = 5;

        var url = window.location.href;
        var arr = url.split("/");
        var resultUrl = arr[3];

        $scope.pageChanged = function() {
            console.log($scope.currentPage);
        };

        if (resultUrl == "loai-danh-muc") {
            posFactory.getPostingDetailsByProductType(arr[4]).then(
                data => {
                    $scope.list_postingDetals = data;
                    $scope.product = data[0].posting.product.postings.name;
                    $scope.keyword = data[0].posting.product.postings.name;
                }, reason => {
                    console.log(reason);
                }
            )
        } else if (resultUrl == "danh-muc") {
            posFactory.getPostingDetailsByProduct(arr[4]).then(
                data => {
                    $scope.list_postingDetals = data;
                    $scope.product = data[0].posting.product.name;
                    $scope.keyword = data[0].posting.product.name;
                }, reason => {
                    console.log(reason);
                }
            )
        } else {
            posFactory.getPostingDetails(resultUrl).then(
                data => {
                    $scope.list_postingDetals = data;
                    $scope.keyword = resultUrl.substr(15);
                }, reason => {
                    console.log(reason);
                }
            )
        }

        $scope.goPostingDetail = function(id) {
            console.log('/' + id);
            $window.location.href = "/" + id;
        }

    }
])

.controller("postingDetail", ["$scope", "$http", "$window", "posFactory",
        function($scope, $http, $window, posFactory) {
            $scope.postingdetail = {};
            $scope.postings = [];
            $scope.postingsByUser = []

            var url = window.location.href;
            var arr = url.split("/");
            var resultUrl = arr[3];
            var numsStr = resultUrl.replace(/[^0-9]/g, '');

            posFactory.getPostingDetail(numsStr).then(
                data => {
                    $scope.postingdetail = data;
                    // var text_element = angular.element(document.querySelector('#delhi'));
                    // text_element.html('<img id="zoom_03" src="/getimage1/' + $scope.postingdetail.id + '" data-zoom-image="/getimage1/3" width="410" />');
                    console.log($scope.postingdetail.posting.product.id);
                    $scope.getPostings($scope.postingdetail.posting.product.id);
                    if ($scope.postingdetail.posting.user != null) {
                        $scope.getPostingsByUser($scope.postingdetail.posting.user.id);
                    }

                    if ($scope.postingdetail.posting.shop != null) {
                        $scope.getPostingsByUser($scope.postingdetail.posting.shop.id);
                    }

                },
                reason => {
                    console.log(reason);
                }
            )

            $scope.getPostings = function(id) {
                posFactory.getPostingsByProductID(id).then(
                    data => {
                        $scope.postings = data;
                    }, reason => {
                        console.log(reason);
                    }
                )
            }

            $scope.getPostingsByUser = function(id) {
                posFactory.getPostingsByUser(id).then(
                    data => {
                        $scope.postingsByUser = data;
                    }, reason => {
                        console.log(reason);
                    }
                )
            }

            $scope.goPostingDetail = function(id) {
                $window.location.href = "/" + id;
            }

        }
    ])
    .controller('indexCtrl', ["$scope", "$http", "$window", "posFactory",
        function($scope, $http, $window, posFactory) {

            $scope.postings = [];
            $scope.productsType = [];

            posFactory.getPostingsTop16SortDate().then(
                data => {
                    $scope.postings = data;
                }, reason => {
                    console.log(reason);
                }
            )

            posFactory.getProducts().then(
                data => {
                    $scope.productsType = data;
                }, reason => {
                    console.log(reason);
                }
            );

            $scope.goPostingDetail = function(id) {
                console.log('/' + id);
                $window.location.href = "/" + id;
            }

        }
    ])
    .directive("owlCarousel", function() {
        return {
            restrict: 'E',
            transclude: false,
            link: function(scope) {
                scope.initCarousel = function(element) {
                    // provide any default options you want
                    var defaultOptions = {};
                    var customOptions = scope.$eval($(element).attr('data-options'));
                    // combine the two options objects
                    for (var key in customOptions) {
                        defaultOptions[key] = customOptions[key];
                    }
                    defaultOptions['responsive'] = {
                        0: {
                            items: 1
                        },
                        600: {
                            items: 2
                        },
                        1000: {
                            items: 5
                        }
                    };
                    // init carousel
                    var curOwl = $(element).data('owlCarousel');
                    if (!angular.isDefined(curOwl)) {
                        $(element).owlCarousel(defaultOptions);
                    }
                };
            }
        };
    })
    .directive('owlCarouselItem', [function() {
        return {
            restrict: 'A',
            transclude: false,
            link: function(scope, element) {
                // wait for the last item in the ng-repeat then call init
                if (scope.$last) {
                    scope.initCarousel(element.parent());
                }
            }
        };
    }])
    .directive('uibPagination', function() {
        return {
            restrict: 'A',
            require: 'uibPagination',
            link: function($scope, $element, $attr, uibPaginationCtrl) {
                uibPaginationCtrl.ShouldHighlightPage = function(currentPage) {
                    return true;
                };
            }
        }
    });