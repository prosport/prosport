(function() {

	// Angular application object
	var app = angular.module('adminPanelApp');

	// Page List Controller
	app.controller('PagesListController', ["$scope", "$http", function($scope, $http) {

		// Declare pages list
		$scope.pages = [];

		// Loading pages
		$scope.load = function() {
			$http.get('/api/pages', {}).
				success(function(data, status, headers, config) {
					$scope.pages = data;
					console.log(data);
				}).
				error(function(data, status, headers, config) {
					console.log("Error while loading pages list");
				});
		};

		// Call load function
		$scope.load();
	}]);


	// Page edit controller
	app.controller('PagesEditController', ["$scope", "$http", "$routeParams", function($scope, $http, $routeParams) {

		// Declare page object
		$scope.editablePage = {};

		// Loading page
		$scope.load = function() {
			$http.get('/url/to/get/page/by/id?page_id='+$routeParams.pageId).
				success(function(data, status, headers, config) {
					$scope.editablePage = data;
				}).
				error(function(data, status, headers, config) {
					console.log("Error while loading page");
				});
		};

		// Submit edited page
		$scope.submit = function() {
			$http.put('/url/to/submit/page', $scope.editablePage).
				success(function(data, status, headers, config) {
					console.log("Edit Success");
					location.href="/admin2/pages/";
				}).
				error(function(data, status, headers, config) {
					console.log("Error while editing page");
				});
		};

		// Call load function
		$scope.load();

	}]);

	// Page create controller
	app.controller('PagesCreateController', ["$scope", "$http", function($scope, $http) {

		// Declare new page object
		$scope.newPage = {};

		// Submit new page function
		$scope.submit = function() {
			$http.post('/url/to/submit/new/page', $scope.newPage).
				success(function(data, status, headers, config) {
					console.log("Page Create Success");
					location.href="/admin2/pages/";
				}).
				error(function(data, status, headers, config) {
					console.log("Error while creating page");
				});
		};

	}]);

})();