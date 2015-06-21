/* Directives */

angular.module('springChat.directives', [])
	.directive('printMessage', function () {
	//.directive('print-message', function () {
	    return {
	    	restrict: 'A',
	        template:
			'<div class="chat-message-container">' +
				'<div class="chat-message" ng-class="{ownMessage: appCtrl.username == message.from}">' +
					'<span ng-show="message.priv">[private] </span>' +
					'<strong>{{message.from}}<span ng-show="message.to"> -> {{message.to}}</span>:</strong> ' +
					'{{message.text}}<br/>' +
				'</div>' +
			'</div>'
	    };
});