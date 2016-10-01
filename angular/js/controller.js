application.controller('controller', ['$scope', '$rootScope', 'ImageService',

    function ($scope, $rootScope, ImageService) {

        $scope.formData = {};

        $rootScope.processSuccess = false;
        $rootScope.processing = false;

        $scope.processFile = function () {
            $rootScope.processing = true;
            
            console.log("process " + $scope.formData.fileLocation);

            ImageService.get($scope.formData.fileLocation)
                .success(function (data) {
                    $rootScope.processing = false;

                    
                    if (data.code == 0) {

                        $rootScope.processSuccess = true;

                        console.log("Images get successful!");

                        $rootScope.resolutions = data.resolutions;
                    }
                })
                .error(function (error) {
                    $rootScope.processing = false;

                    console.log(":Error  " + error);
                });
        };

    }])
;

