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

		$scope.delete = function(page) {
			$http.delete('/api/pages/'+page.id).
			success(function(data, status, headers, config) {
					$scope.load();
				}).
				error(function(data, status, headers, config) {
					console.log("Error while removing page");
				});		
		};

		// Call load function
		$scope.load();
	}]);


	// Page edit controller
	app.controller('PagesEditController', ["$scope", "$http", "$routeParams", function($scope, $http, $routeParams) {

		// Declare page object
		$scope.editablePage = {};

		// CKEditor config
		$scope.editorOptions = {
    		language: 'ru',
    		'extraPlugins': "imagebrowser,mediaembed",
    		imageBrowser_listUrl: '/api/v1/ckeditor/gallery',
            filebrowserBrowseUrl: '/api/v1/ckeditor/files',
            filebrowserImageUploadUrl: '/api/v1/ckeditor/images',
            filebrowserUploadUrl: '/api/v1/ckeditor/files',
            toolbarLocation: 'top',
            toolbar: 'full',
            toolbar_full: [
                { name: 'basicstyles', items: [ 'Bold', 'Italic', 'Strike', 'Underline' ] },
                { name: 'paragraph', items: [ 'BulletedList', 'NumberedList', 'Blockquote' ] },
                { name: 'editing', items: ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] },
                { name: 'links', items: [ 'Link', 'Unlink', 'Anchor' ] },
                { name: 'tools', items: [ 'SpellChecker', 'Maximize' ] },
                { name: 'clipboard', items: [ 'Undo', 'Redo' ] },
                { name: 'styles', items: [ 'Format', 'FontSize', 'TextColor', 'PasteText', 'PasteFromWord', 'RemoveFormat' ] },
                { name: 'insert', items: [ 'Image', 'Table', 'SpecialChar', 'MediaEmbed' ] },'/',
            ]
		};		

		// Loading page
		$scope.load = function() {
			$http.get('/api/pages/'+$routeParams.pageId).
				success(function(data, status, headers, config) {
					$scope.editablePage = data;
				}).
				error(function(data, status, headers, config) {
					console.log("Error while loading page");
				});
		};

		// Submit edited page
		$scope.submit = function() {
			$http.put('/api/pages', $scope.editablePage).
				success(function(data, status, headers, config) {
					console.log("Edit Success");
					location.href="/admin2#pages";
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

		// CKEditor config
		$scope.editorOptions = {
    		language: 'ru',
    		'extraPlugins': "imagebrowser,mediaembed",
    		imageBrowser_listUrl: '/api/v1/ckeditor/gallery',
            filebrowserBrowseUrl: '/api/v1/ckeditor/files',
            filebrowserImageUploadUrl: '/api/v1/ckeditor/images',
            filebrowserUploadUrl: '/api/v1/ckeditor/files',
            toolbarLocation: 'top',
            toolbar: 'full',
            toolbar_full: [
                { name: 'basicstyles', items: [ 'Bold', 'Italic', 'Strike', 'Underline' ] },
                { name: 'paragraph', items: [ 'BulletedList', 'NumberedList', 'Blockquote' ] },
                { name: 'editing', items: ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ] },
                { name: 'links', items: [ 'Link', 'Unlink', 'Anchor' ] },
                { name: 'tools', items: [ 'SpellChecker', 'Maximize' ] },
                { name: 'clipboard', items: [ 'Undo', 'Redo' ] },
                { name: 'styles', items: [ 'Format', 'FontSize', 'TextColor', 'PasteText', 'PasteFromWord', 'RemoveFormat' ] },
                { name: 'insert', items: [ 'Image', 'Table', 'SpecialChar', 'MediaEmbed' ] },'/',
            ]
		};	

		// Submit new page function
		$scope.submit = function() {
			$http.post('/api/pages', $scope.newPage).
				success(function(data, status, headers, config) {
					console.log("Page Create Success");
					location.href="/admin2#pages";
				}).
				error(function(data, status, headers, config) {
					console.log("Error while creating page");
				});
		};

	}]);

})();