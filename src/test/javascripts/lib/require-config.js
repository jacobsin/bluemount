var require = require || {};
require.baseUrl = '../../../main/webapp/assets/javascripts/lib';
require.paths = require.paths || {};
require.paths.tests = '../../../../../test/javascripts/tests';
require.paths.sinon = '../../../../../test/javascripts/lib/sinon';
require.paths['sinon-qunit'] = '../../../../../test/javascripts/lib/sinon-qunit';
require.shim = require.shim || {};
require.shim['sinon-qunit'] = {
  deps : ['sinon']
};