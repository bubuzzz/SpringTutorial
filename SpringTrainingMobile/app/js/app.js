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

  $.post( 
      "http://localhost:8080/SpringTrainingController-0.0.1/user/list", 
      {username: "bubuzzz", password: "12345678"}
      
    ).done(function( data ) {
      alert( "Data Loaded: " + data );
    }
  );

  jQuery.get("http://localhost:8080/SpringTrainingController-0.0.1/hello/toJson", function(data) {
    console.log("get something");
    console.log(data);
  }).done(function() {
    alert( "second success" );
  }).fail(function() {
    alert( "error" );
  }).always(function() {
    alert( "finished" );
  });
}

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

myApp.directive('groupInit', function() {
    return function($scope, $element, $attrs) {                
        $scope.$watch('ul.groupList', function(value){
            initGroup();
        });                
    }
});

myApp.directive('userInit', function() {
    return function($scope, $element, $attrs) {                
        $scope.$watch('ul.userList', function(value){
            initUser();
        });                
    }
});