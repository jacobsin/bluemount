$LOAD_PATH.unshift(File.dirname(__FILE__) + '/lib')
require 'rspec/expectations'
require 'restclient'
require 'application'
require 'term/ansicolor'

at_exit { Application.instance.stop }