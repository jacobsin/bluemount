require 'ant'

namespace :requirejs do
  jvm_args = %w{}
  #args = %w{-f r.js -o name=main out=main-built.js baseUrl=.}
  args = %w{-f r.js -o app.build.js}

  task :optimise do
    ant.java(:classname => 'org.mozilla.javascript.tools.shell.Main', :dir => 'src/main/webapp/assets/javascripts',
         :fork => true, :failonerror => true) do
      classpath do
        path :refid => 'test.class.path'
      end
      jvm_args.each { |v| jvmarg :value => v }
      args.each { |v| arg :value => v }
    end
  end
  #java org.mozilla.javascript.tools.shell.Main [options] script-filename-or-url [script-arguments]
end
