(function(angular, $) {
    var app=angular.module('myApp', ['ngFileUpload','ui.router']);

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
            },
        }).state('contact',{

            url:'/contact',
            views:{
                '':{
                    templateUrl:'page/contact.html'
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

    app.controller('indexCtrl', ['$scope','$http',function ($scope,$http,$state){


    }]);









})(angular, jQuery);