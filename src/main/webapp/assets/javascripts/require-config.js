var require = require || {};
require.baseUrl = '/assets/javascripts/lib';
require.paths = {
  app:'../app'
};
require.shim = {
  backbone:{
    deps:['underscore', 'jquery'],
    exports:'Backbone'
  },
  'underscore.string':{
    deps:['underscore']
  }
};