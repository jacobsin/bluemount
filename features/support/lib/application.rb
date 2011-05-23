require 'singleton'
require 'rjb'

class Application
  include Singleton

  attr_reader :port

  def initialize
    rjb_load
    @port = 3333
    @jetty = Rjb::import('bluemount.web.RunJetty').new(@port)
    @jetty.start false
  end

  def self.api(path)
    File.join "http://localhost:#{self.instance.port}", path
  end

  def stop
    @jetty.stop
  end

  private

  def rjb_load
    separator = Config::CONFIG['target_os'] =~ /windows/ ? ';' : ':'
    jars = Dir.glob(File.join('m2', '**', '*.jar')).select{|f| f !~ /sources\.jar/ }
    classpath = "target/test/java:target/java:#{jars.join(separator)}"
    jvmargs = [
        '-Duser.timezone=UTC',
        '-Djava.util.logging.config.file=src/main/resources/logging.properties',
        '-Dlogback.access.config.file=src/main/resources/logback-access.xml'
    ]
    Rjb::load(classpath, jvmargs)
  end

end