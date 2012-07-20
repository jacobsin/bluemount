define(['backbone', 'moment', 'underscore.string', 'backbone-super'], function(Backbone, moment) {

  window.Project = Backbone.Model.extend({
    defaults: {
      type: _.str.trim("OpenSource ")
    },

    initialize: function() {
      this.set({created : moment().utc().format()});
    },

    start: function() {
      this.set({started : true});
    }
  });

  window.OpenSourceProject = Project.extend({
    start: function() {
      this._super();
      this.set({incubating : true});
    }
  });

  return window.Project;
});