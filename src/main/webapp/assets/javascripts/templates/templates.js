define(['underscore', 'super'], function () {
  var Templates = Class.extend({

    init:function () {
      this.templates ={};
    },

    add:function (name, templateSrc) {
      this.templates[name] = _.template(templateSrc);
    },

    get:function (name) {
      return this.templates[name];
    },

    apply:function (name, context) {
      return this.templates[name](context);
    }
  });

  window.Templates = new Templates();

  return window.Templates;
});