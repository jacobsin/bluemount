require.config({
  baseUrl : '../../../main/webapp/assets/javascripts/lib',
  paths : {
    test : '../../../../../test/javascripts/tests',
    sinon : '../../../../../test/javascripts/lib/sinon',
    'sinon-qunit' :'../../../../../test/javascripts/lib/sinon-qunit',
  },
  shim : {
    'sinon-qunit' : {
      deps : ['sinon']
    }
  }
});

