/* Directives */

angular.module('springChat.directives', [])
	.directive('printMessage', function () {
	    return {
	    	restrict: 'A',
	        template: '<span ng-show="message.priv">[private] </span><strong>{{message.from}}<span ng-show="message.to"> -> {{message.to}}</span>:</strong> {{message.text}}<br/>'
	       
	    };
});