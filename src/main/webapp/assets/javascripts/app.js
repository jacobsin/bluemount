requirejs.config({
  baseUrl: '/assets/javascripts/lib',
  paths: {
    app: '../app'
  },
  shim: {
    backbone: {
      deps: ['underscore', 'jquery'],
      exports: 'Backbone'
    }
  }
});

require(['jquery', 'underscore', 'backbone', 'bootstrap'],
function($, _, Backbone, bootstrap) {

});