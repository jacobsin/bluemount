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

  def self.uri(path)
    File.join "http://localhost:#{self.instance.port}", path
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

  def rjb_load
    classes = ["target/test/java", "target/java"]
    classpath = (classes | jars('local') | jars('m2')).join(File::PATH_SEPARATOR)
    jvmargs = [
        '-Duser.timezone=UTC',
        '-Djava.util.logging.config.file=src/main/resources/logging.properties',
        '-Dlogback.access.config.file=src/main/resources/logback-access.xml'
    ]
    jvmargs |= ['-Xdebug', '-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005'] if debug?
    Rjb::load(classpath, jvmargs)
  end

end