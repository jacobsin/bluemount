require 'singleton'
require 'java'

java_import 'bluemount.web.RunJetty'

class Application
  include Singleton

  attr_reader :port

  def initialize
    @port = 3333
    @jetty = RunJetty.new(@port)
    @jetty.start false
  end

  def self.uri(path)
    Api.uri path
  end

  def stop
    @jetty.stop
  end

  private

  def jars(dir)
    Dir.glob(File.join(dir, '**', '*.jar')).select { |f| f !~ /sources\.jar/ }
  end

  def debug?
    ENV['RUBYLIB'] =~ /ruby-debug-ide/
  end

end