//noinspection BadExpressionStatementJS
({
  appDir: '../webapp',
  mainConfigFile: '../webapp/assets/javascripts/require-config.js',
  baseUrl: 'assets/javascripts/lib',
  dir: '../../../build/deploy/webapp',
  preserveLicenseComments: false,
  //Comment out the optimize line if you want
  //the code minified by UglifyJS
  //optimize: 'none',

  paths: {
    jquery: 'empty:'
  },

  inlineText: true,

  modules: [
    //Optimize the application files. jQuery is not
    //included since it is already in require-jquery.js
//    {
//      name: 'app'
//    },
    {
      name: 'app/package/document_diff_view'
    }
  ]
})
