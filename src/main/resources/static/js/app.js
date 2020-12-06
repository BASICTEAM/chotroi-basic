var app = angular.module("myApp", ['ngRoute', 'ui.bootstrap'])

.factory("posFactory", function($http) {
    var posFactory = {};
    var postingDetails = [];
    var products = [];
    var postings = [];
    var postingSaveds = [];

    var quanHuyens = [];
    var tinhs = [];
    var xas = [];

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

    posFactory.getQuanHuyen = function() {
        var promise = $http
            .get('/json/quan_huyen.json')
            .then((respone) => {
                quanHuyens = respone.data;
                return quanHuyens;
            })
            .catch((reason) => console.log(reason));
        return promise;
    };

    posFactory.getTinh = function() {
        var promise = $http
            .get('/json/tinh_tp.json')
            .then((respone) => {
                tinhs = respone.data;
                return tinhs;
            })
            .catch((reason) => console.log(reason));
        return promise;
    };

    posFactory.getXa = function() {
        var promise = $http
            .get('/json/xa_phuong.json')
            .then((respone) => {
                xas = respone.data;
                return xas;
            })
            .catch((reason) => console.log(reason));
        return promise;
    };

    posFactory.getPostingSaved = function(postingSaved) {
        var promise = $http
            .get('/posting-saved/find-all')
            .then((respone) => {
                postingSaveds = respone.data;
                return postingSaveds;
            })
            .catch((reason) => console.log(reason));
        return promise;
    }

    return posFactory;
})

.controller("posting", ["$scope", "$http", "$window", "posFactory",
    function($scope, $http, $window, posFactory) {

        $scope.tinhs = [];
        $scope.quanHuyens = [];
        $scope.xas = [];

        $scope.listQuanHuyen = [];
        $scope.listXa = [];

        $scope.indexQuan = '';
        $scope.indexXa = '';

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

        posFactory.getTinh().then(
            data => {
                $scope.tinhs = data;
            }, reason => {
                console.log(reason);
            }
        );

        posFactory.getQuanHuyen().then(
            data => {
                $scope.quanHuyens = data;
            }, reason => {
                console.log(reason);
            }
        );

        posFactory.getXa().then(
            data => {
                $scope.xas = data;
            }, reason => {
                console.log(reason);
            }
        );

        $scope.selectTinh = function() {

            if ($scope.tinhSelect == null) {
                $scope.indexQuan = '';
                $scope.indexXa = '';
                if ($scope.xaSelect != null) {
                    $scope.xaSelect.name_with_type = '';
                }
                if ($scope.huyenSelect != null) {
                    $scope.huyenSelect.name_with_type = '';
                }
            }

            if ($scope.tinhSelect != null) {
                $scope.indexQuan = $scope.tinhSelect.name;
                $scope.listQuanHuyen = [];
                for (const i in $scope.quanHuyens) {
                    var parent_code = $scope.quanHuyens[i].parent_code;
                    if (parent_code == $scope.tinhSelect.code) {
                        $scope.listQuanHuyen.push($scope.quanHuyens[i]);
                    }
                }
            }

        };

        $scope.selectHuyen = function() {

            if ($scope.huyenSelect == null) {
                $scope.indexXa = '';

                if ($scope.xaSelect != null) {
                    $scope.xaSelect.name_with_type = '';
                }
                if ($scope.huyenSelect != null) {
                    $scope.huyenSelect.name_with_type = '';
                }

            }

            if ($scope.huyenSelect != null) {
                $scope.indexXa = $scope.huyenSelect.name;
                $scope.listXa = [];
                for (const i in $scope.xas) {
                    var parent_code = $scope.xas[i].parent_code;
                    if (parent_code == $scope.huyenSelect.code) {
                        $scope.listXa.push($scope.xas[i]);
                    }
                }
            }
        };

    }
])

.controller("postingDetails", ["$scope", "$http", "$window", "posFactory", "$uibModal",
    function($scope, $http, $window, posFactory) {
        $scope.list_postingDetals = [];
        $scope.postingSaveds = [];

        $scope.usernameLogin = document.getElementsByName("usernameLogin")[0].value;
        $scope.setPostingSave = false;

        $scope.product = "Danh mục";
        $scope.keyword = "";

        $scope.isDisabled = true;

        // Table & Pagination
        // PAGINATION
        $scope.currentPage = 1;
        $scope.itemsPerPage = 5;

        var url = window.location.href;
        var arr = url.split("/");
        var resultUrl = arr[3];

        $scope.url = resultUrl;

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

                    console.log($scope.list_postingDetals);
                    $scope.keyword = resultUrl.substr(15);
                }, reason => {
                    console.log(reason);
                }
            )
        }

        posFactory.getPostingSaved().then(
            data => {
                $scope.postingSaveds = [];
                for (const i in data) {
                    if (data[i].assessor == $scope.usernameLogin) {
                        $scope.postingSaveds.push(data[i]);
                    }

                }
            }, reason => {
                console.log(reason);
            }
        )

        $scope.checkButtonSave = function(id) {
            for (const i in $scope.postingSaveds) {
                if ($scope.postingSaveds[i].postingID == id) {
                    return true;
                }
            }
            return false;
        }

        $scope.postingSave = function() {
            $scope.isDisabled = false;
        }

        $scope.goPostingDetail = function(id) {
            if ($scope.isDisabled === true) {
                $window.location.href = "/" + id;
            }
        }

    }
])

.controller("postingDetail", ["$scope", "$http", "$window", "posFactory",
        function($scope, $http, $window, posFactory) {
            $scope.postingdetail = {};
            $scope.postings = [];
            $scope.postingsByUser = [];

            $scope.usernameLogin = document.getElementsByName("usernameLogin")[0].value;
            $scope.setPostingSave = false;

            var url = window.location.href;
            var arr = url.split("/");
            var resultUrl = arr[3];
            $scope.url = resultUrl;
            var numsStr = resultUrl.replace(/[^0-9]/g, '');

            posFactory.getPostingDetail(numsStr).then(
                data => {
                    $scope.postingdetail = data;
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

            posFactory.getPostingSaved().then(
                data => {
                    $scope.postingSaveds = [];
                    for (const i in data) {
                        if (data[i].assessor == $scope.usernameLogin) {
                            $scope.postingSaveds.push(data[i]);
                        }

                    }
                }, reason => {
                    console.log(reason);
                }
            )

            $scope.checkButtonSave = function(id) {
                for (const i in $scope.postingSaveds) {
                    if ($scope.postingSaveds[i].postingID == id) {
                        return true;
                    }
                }
                return false;
            }

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
    .controller('profileCtrl', ["$scope", "$http", "$window", "posFactory",
        function($scope, $http, $window, posFactory) {

            $scope.postings = [];
            $scope.keyword = "";

            // Table & Pagination
            // PAGINATION
            $scope.currentPage = 1;
            $scope.itemsPerPage = 3;

            var url = window.location.href;
            var arr = url.split("/");
            var id = arr[3];

            $scope.pageChanged = function() {
                console.log($scope.currentPage);
            };

            posFactory.getPostingsByUser(id).then(
                data => {
                    $scope.postings = data;
                }, reason => {
                    console.log(reason);
                }
            )

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