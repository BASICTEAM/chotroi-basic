"use strict";

angular.module("myApp").factory("userFactory", [
  "$http",
  function ($http) {
    var userFactory = {};
    var users = [];

    userFactory.getUsers = function () {
      var promise = $http
        .get("http://localhost:8080/users/find-all")
        .then((respone) => {
          users = respone.data;

          return users;
        })
        .catch((reason) => console.log(reason));
      return promise;
    };

    userFactory.getUsersActivated = function () {
      var promise = $http
        .get("http://localhost:8080/users/list-activated")
        .then((respone) => {
          users = respone.data;
          return users;
        })
        .catch((reason) => console.log(reason));
      return promise;
    };

    userFactory.getUsersNotActivated = function () {
      var promise = $http
        .get("http://localhost:8080/users/list-not-activated")
        .then((respone) => {
          users = respone.data;

          return users;
        })
        .catch((reason) => console.log(reason));
      return promise;
    };

    userFactory.getUsersBlock = function () {
      var promise = $http
        .get("http://localhost:8080/users/list-block")
        .then((respone) => {
          users = respone.data;

          return users;
        })
        .catch((reason) => console.log(reason));
      return promise;
    };

    userFactory.getUser = function (id, user) {
      var promise = $http
        .get("http://localhost:8080/users/" + id + "/get", user)
        .then((respone) => {
          user = respone.data;
          return user;
        })
        .catch((reason) => console.log(reason));
      return promise;
    };

    userFactory.updateUser = function (id, user) {
      var promise = $http
        .put("http://localhost:8080/users/" + id + "/update", user)
        .then((respone) => {
          user = respone.data;
          return user;
        })
        .catch((reason) => console.log(reason));
      return promise;
    };

    userFactory.deleteUser = function (id) {
      var promise = $http.delete(
        "http://localhost:8080/users/" + id + "/delete"
      );
      return promise;
    };

    userFactory.blockUser = function (id, user) {
      var promise = $http
        .put("http://localhost:8080/users/" + id + "/block", user)
        .then((respone) => {
          user = respone.data;
          return user;
        })
        .catch((reason) => console.log(reason));
      return promise;
    };

    userFactory.activeUser = function (id, user, status) {
      var promise = $http
        .put("http://localhost:8080/users/" + id + "/" + status, user)
        .then((respone) => {
          user = respone.data;
          return user;
        })
        .catch((reason) => console.log(reason));
      return promise;
    };

    return userFactory;
  },
]);
