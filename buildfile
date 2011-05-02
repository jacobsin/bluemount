VERSION_NUMBER = "1.0.0"
GROUP = "bluemount"

repositories.remote << "http://repo2.maven.org/maven2/"
repositories.remote << "http://www.ibiblio.org/maven2/"
repositories.local = "./.m2"

#options.proxy.http = 'http://myproxy:8080'

desc "The Bluemount project"
define "bluemount" do

  project.version = VERSION_NUMBER
  project.group = GROUP
  compile.with
  resources
  test.compile.with 
  package(:jar)

end
