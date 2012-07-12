requirejs.config({
  shim: {
    'bootstrap-popover': ['bootstrap-tooltip']
  }
});

define(['bootstrap-transition', 'bootstrap-alert', 'bootstrap-modal', 'bootstrap-dropdown',
  'bootstrap-scrollspy', 'bootstrap-tab', 'bootstrap-tooltip', 'bootstrap-popover',
  'bootstrap-button', 'bootstrap-collapse', 'bootstrap-carousel', 'bootstrap-typeahead'], function () {
});