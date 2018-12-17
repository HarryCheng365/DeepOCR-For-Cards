(function(angular, $) {
    var app=angular.module('myApp', ['ngFileUpload','ui.router']);
    app.controller('indexCtrl', ['$scope','$http',function ($scope,$http){
    }]);

    app.run( function run( $http ){

        // For CSRF token compatibility with Django
        $http.defaults.headers.post['X-CSRFToken'] = "{{ csrf_token }}";
    });

    app.config(function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('/index');
        $stateProvider.state('workspace', {
            url: '/workspace',
            views: {
                '': {
                    templateUrl: 'page/workspace/main.html'
                }
            },
            params:{page:""},
            onExit:function () {
                $('#context-menu').remove();
            }
        }).state('monitor',{

            url:'/monitor',
            views:{
                '':{
                    templateUrl:'page/productionArea/Monitor.html'
                }
            },
            params:{chosenData:null, TaskId:null,processInstanceId:null, processDefinitionId:null,JobInfo:null}//生产区管理页面
        }).state('doservice',{
            url:'/doservice',
            views:{
                '':{
                    templateUrl:'page/productionArea/doservice.html'
                }
            },
            params:{chosenData:null}//生产区管理页面
        }).state('index',{
            url:'/index',
            views:{
                '':{
                    templateUrl:'page/productionArea/AreaPage.html'
                }
            }

        });

    });







})(angular, jQuery);