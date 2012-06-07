require 'artifacts'
require 'cucumber/rake/task'

VERSION_NUMBER = "1.0.0"
GROUP = "bluemount"

TARGET_JAVA = 'target/java'
TARGET_TEST_JAVA = 'target/test/java'

#options.proxy.http = 'http://myproxy:8080'

define 'bluemount' do
  project.version = VERSION_NUMBER
  project.group = GROUP
  project.no_ipr

  iml.local_repository_env_override = nil

  local_shipped = Dir[_("lib/shipped/*.jar")]
  compile.into(TARGET_JAVA).with local_shipped, SHIPPED
  resources.filter.into(TARGET_JAVA)

  local_reference = Dir[_("lib/reference/*.jar")]
  test.compile.into(TARGET_TEST_JAVA).with local_shipped, SHIPPED, local_reference, REFERENCE
  test.resources.filter.into(TARGET_TEST_JAVA)
#  test.enhance { Rake::Task['features'].execute }

  package(:jar)
end

Cucumber::Rake::Task.new(:features => task('compile')) do |t|
  t.cucumber_opts = %w{--format junit    --out reports/cucumber
                       --format progress --out reports/cucumber/features.log
                       --format html     --out reports/cucumber/features.html}
end