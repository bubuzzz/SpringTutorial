$.noConflict();

// trigger functions // 
function initUser () {
  console.log("Init User");

  for (i = 0; i < 10; i ++) {
    jQuery("ul.userList").append(
      "<li class=\"topcoat-list__item__line-height topcoat-list__item\">" +
      "<span class=\"ng-scope\">" + i + "</span>" + 
      "</li>"
    );
  }
}

function initGroup () {
  console.log("Init Group");
  
  for (i = 0; i < 10; i ++) {
    jQuery("ul.groupList").append(
      "<li class=\"topcoat-list__item__line-height topcoat-list__item\">" + 
      "<span class=\"ng-scope\">" + i + "</span>" +
      "</li>"
    );
  }

  jQuery.ajax({
    type: "GET",
    // url: "http://localhost:8080/SpringTrainingController/user/devs/service",
    // data: "username=bubuzzz&password=12345678&groupType=DEVELOPER",
    // url : "http://localhost:8080/SpringTrainingController/hello",
    url : 'http://localhost:8080/SpringTrainingController/hello/toJson', 
    success: function(response){
        console.log(response);
    },
    error: function(msg){
      console.log("ERROR! \n" + JSON.stringify(msg));
    }
  });

}

jQuery(document).ready(function(){
  initGroup();

   // $.ajax({
   //      url: "http://rest-service.guides.spring.io/greeting"
   //  }).then(function(data) {
   //     $('.greeting-id').append(data.id);
   //     $('.greeting-content').append(data.content);
   //  });
});

function searchGroup () {

}

function searchUser () {

}

// helper functions //

myApp = angular.module('myApp', ['onsen.directives']);

myApp.controller("mainController", function ($scope) {

});

myApp.controller("userController", function ($scope, $rootScope) {
  initUser();
});

myApp.controller("groupController", function ($scope, $rootScope) {
  
});

// myApp.directive('groupInit', function() {
//     return function($scope, $element, $attrs) {                
//         $scope.$watch('ul.groupList', function(value){
//             initGroup();
//         });                
//     }
// });

myApp.directive('userInit', function() {
    return function($scope, $element, $attrs) {                
        $scope.$watch('ul.userList', function(value){
            initUser();
        });                
    }
});