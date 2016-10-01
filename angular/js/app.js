var application = angular.module('appname', [
    'service'
]);



application.config(['$httpProvider', function ($httpProvider) {

    $httpProvider.defaults.headers.common = {};
    $httpProvider.defaults.headers.post = {};
    $httpProvider.defaults.headers.put = {};
    $httpProvider.defaults.headers.patch = {};

}]);


