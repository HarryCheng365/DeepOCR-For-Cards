(function(angular, $) {
    var app=angular.module('myApp', ['ngFileUpload','ui.router','ngImgCrop']);

    app.run( function run( $http ){

        // For CSRF token compatibility with Django
        $http.defaults.headers.post['X-CSRFToken'] = "{{ csrf_token }}";
    });

    app.config(function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('/index');
        $stateProvider.state('team', {
            url: '/team',
            views: {
                '': {
                    templateUrl: 'page/team.html'
                }
            }
        }).state('upload',{

            url:'/upload',
            views:{
                '':{
                    templateUrl:'page/upload.html'
                }
            },
            params:{chosenData:null}//生产区管理页面
        }).state('feature',{
            url:'/feature',
            views:{
                '':{
                    templateUrl:'page/feature.html'
                }
            },
            params:{chosenData:null}//生产区管理页面
        }).state('clip',{
                url:'/clip',
                views:{
                    '':{
                        templateUrl:'page/clip.html'
                    }
                },
                params:{chosenData:null}//生产区管理页面
            }).state('index',{
            url:'/index',
            views:{
                '':{
                    templateUrl:'page/main.html'
                }
            }

        }).state('about',{
            url:'/about',
            views:{
                '':{
                    templateUrl:'page/about.html'
                }
            }
        });

    });

    app.controller('indexCtrl', ['$scope','$http','$state',function ($scope,$http,$state){//必须 引入和

        $scope.deviceFlag=true;
        $scope.init=function(){
            var sUserAgent = navigator.userAgent.toLowerCase();
            var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
            var bIsIphoneOs = sUserAgent.match(/iphone os/i) =="iphone os";
            var bIsMidp = sUserAgent.match(/midp/i) == "midp";
            var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
            var bIsUc = sUserAgent.match(/ucweb/i) === "ucweb";
            var bIsAndroid = sUserAgent.match(/android/i) == "android";
            var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
            var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
            if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {
               $scope.deviceFlag=true;
            } else {
                $scope.deviceFlag=false;
            }

        };
        $scope.GoToUpLoad=function() {
            $state.go('upload', {chosenData: null}, {reload: true})
        };
        $scope.paramsList=[];



    }]);









})(angular, jQuery);