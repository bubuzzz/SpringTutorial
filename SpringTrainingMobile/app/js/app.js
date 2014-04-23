$.noConflict();
jQuery( document ).ready(function( $ ) {
  initUser();
});


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
  initGroup();
});



