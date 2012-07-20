//noinspection BadExpressionStatementJS
({
  appDir: "../webapp",
  baseUrl: "assets/javascripts/lib",
  dir: "../../../build/deploy/webapp",
  preserveLicenseComments: false,
  //Comment out the optimize line if you want
  //the code minified by UglifyJS
  //optimize: "none",

  paths: {
    "jquery": "empty:",
    "app": "../app"
  },

  modules: [
    //Optimize the application files. jQuery is not
    //included since it is already in require-jquery.js
    {
      name: "app"
    }
  ]
})
