require.config({
  baseUrl : '/assets/javascripts/lib',
  paths : {
    app:'../app',
    text:'../plugins/text',
    template:'../plugins/template',
    templates:'../templates'
  },
  shim: {
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
  }
});