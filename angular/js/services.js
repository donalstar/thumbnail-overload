var services = angular.module('service', []);

services.factory('ImageService', ['$http', function ($http) {
    return {
        get: function (file_name) {
            return $http.get('http://default-environment.8bmxubajyg.us-east-1.elasticbeanstalk.com/resolutions?file_name=' + file_name);
        }

    }
}]);


