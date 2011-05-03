require "artifacts"

VERSION_NUMBER = "1.0.0"
GROUP = "bluemount"

#options.proxy.http = 'http://myproxy:8080'

desc "The Bluemount project"
define "bluemount" do

  project.version = VERSION_NUMBER
  project.group = GROUP

  compile.with SHIPPED
  resources
  test.compile.with SHIPPED, REFERENCE

  package(:jar)

end
