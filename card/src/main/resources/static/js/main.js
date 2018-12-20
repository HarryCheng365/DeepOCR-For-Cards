(function( angular, $) {


    angular.module('myApp').controller("APICtrl",['$scope','$http','$state','Upload',function($scope,$http,$state,Upload) {
        $scope.upload = function () {
            console.log($scope.paramsList);
            Upload.upload(
                {
                    url: 'upload/image',
                    params:{
                        'guid':$scope.paramsList[0].guid,
                        'user':$scope.paramsList[0].user
                    },
                    file: $scope.paramsList[0].file
                })
                .progress(function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
                })
                .success(function (data, status, headers, config) {
                    console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
                })
                .error(function (data, status, headers, config) {
                    console.log('error status: ' + status);
                })
        };

        // $scope.$watch('file', function (file) {
        //     if(file!=null)
        //         $scope.upload($scope.file);
        // });

        $scope.reader = new FileReader();
        $scope.thumb=[];
        $scope.fileList=[];
        $scope.paramsList=[];
        $scope.thumb_default="../images/add.png";
        $scope.uploadFiles=function(file){
            console.log(file);
            var guid = (new Date()).valueOf();
            $scope.reader.readAsDataURL(file);
            $scope.reader.onload=function (ev) {
                $scope.$apply(function(){
                    $scope.thumb.push({
                        'guid':guid,
                        'imgSrc': ev.target.result
                    });
                    $scope.paramsList.push({
                        'guid':guid,
                        'user':1,
                        'file':file
                    })
                });
            }
        };
        $scope.img_del=function(key){
           $scope.thumb.splice(key,1);
           $scope.paramsList.splice(key,1);
        }



    }]);







})(angular, jQuery);