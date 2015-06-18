'use strict';

/* App Module */

var springChat = angular.module('springChat', ['springChat.controllers',
                                               'springChat.services',
                                               'springChat.directives']);

/*Inject "chatConst" and use it to access the constants*/
//springChat.constant("chatConst", {
//    serverUrl: "http://localhost:8080"  //Example! Unused.
//});