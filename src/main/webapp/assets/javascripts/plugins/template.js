define({
  load:function (name, req, load, config) {
    req(['templates/templates', 'text!templates/' + name + '.underscore'], function (Templates, value) {
      if (config.isBuild) {
        load();
        return;
      }
      Templates.add(name, value);
      load(Templates.get(name));
    });
  }
});