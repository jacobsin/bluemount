require 'singleton'
require 'rjb'

class Application
  include Singleton

  attr_reader :port

  def initialize
    @port = 3333
    jars = Dir.glob(File.join('m2', '**', '*.jar')).select{|f| f !~ /sources\.jar/ }
    classpath = "target/resources:target/java:#{jars.join(':')}"
    jvmargs = [
        '-Duser.timezone=UTC',
        '-Djava.util.logging.config.file=src/main/resources/logging.properties',
        '-Dlogback.access.config.file=src/main/resources/logback-access.xml'
    ]
    Rjb::load(classpath, jvmargs)
    run_jetty = Rjb::import('bluemount.web.RunJetty')
    @jetty = run_jetty.new(@port)
    @jetty.start false
  end

  def self.api(path)
    File.join "http://localhost:#{self.instance.port}", path
  end

  def stop
    @jetty.stop
  end
end