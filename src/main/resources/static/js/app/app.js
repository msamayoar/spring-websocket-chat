'use strict';

/* App Module */

var springChat = angular.module('springChat', ['springChat.controllers',
                                               'springChat.services',
                                               'springChat.directives']);

var servicesModule = angular.module('springChat.services', []);
var controllersModule = angular.module('springChat.controllers', ['toaster']);

/*Inject "chatConst" and use it to access the constants*/
//springChat.constant("chatConst", {
//    serverUrl: "http://localhost:8080"  //Example! Unused.
//});