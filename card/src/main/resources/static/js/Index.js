(function( angular, $) {


    angular.module('myApp').controller("APICtrl", function($scope,$http,$state) {
        $scope.init=function(){
            $state.go('team',{chosenData:null},{reload:true});

        }





    });







})(angular, jQuery);