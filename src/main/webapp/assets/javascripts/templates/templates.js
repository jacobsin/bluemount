define(['underscore', 'super'], function () {
  window.Templates = Class.extend({

    init:function () {
      this.templates ={};
    },

    add:function (name, templateSrc) {
      this.templates[name] = _.template(templateSrc);
    },

    apply:function (name, context) {
      return this.templates[name](context);
    }
  });

  return window.Templates;
});