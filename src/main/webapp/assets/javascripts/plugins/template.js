define({
  load: function (name, req, load, config) {
    req(['templates/templates', 'text!templates/'+name + '.underscore'], function (Templates, value) {
      Templates.add(name, value);
      load(Templates.get(name));
    });
  }
});