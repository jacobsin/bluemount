var require = require || {};
require.baseUrl = '/assets/javascripts/lib';
require.paths = {
  app:'../app',
  template:'../plugins/template',
  templates:'../templates'
};
require.shim = {
  backbone:{
    deps:['underscore', 'jquery'],
    exports:'Backbone'
  },
  'backbone-super': {
    deps:['backbone']
  },
  'underscore.string':{
    deps:['underscore']
  }
};