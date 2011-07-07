$LOAD_PATH.unshift(File.dirname(__FILE__) + '/lib')
require 'rspec/expectations'
require 'restclient'
require 'application'

at_exit { Application.instance.stop }