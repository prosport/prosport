(function() {

	var app = angular.module('adminPanelApp', ['ngRoute', 'ngCkeditor']);

	app.config(function($routeProvider) {

		$routeProvider
			.when("/", {
				controller: "AdminIndexController",
				templateUrl: "/assets/templates/admin/index.html"
			})
			.when("/pages", {
				controller: "PagesListController",
				templateUrl: "/assets/templates/admin/pages_list.html"
			})
			.when("/pages/edit/:pageId", {
				controller: "PagesEditController",
				templateUrl: "/assets/templates/admin/pages_edit.html"
			}).
			when("/pages/create/", {
				controller: "PagesCreateController",
				templateUrl: "/assets/templates/admin/pages_create.html"
			}).
			// when("/categories", {
			// 	redirectTo: "/"
			// }).
			// when("/media", {
			// 	redirectTo: "/"
			// }).
			otherwise({
				templateUrl: "/assets/templates/admin/404.html"
			});
	});

})();
