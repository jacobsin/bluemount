require 'ant'

namespace :rjs do
  jvm_args = %w{-Xmx256m}
  args = %w{r.js -o app.build.js}

  task :optimize do
    ant.java(:classname => 'org.mozilla.javascript.tools.shell.Main', :dir => 'src/main/rjs',
         :fork => true, :failonerror => true) do
      classpath do
        path :refid => 'test.class.path'
      end
      jvm_args.each { |v| jvmarg :value => v }
      args.each { |v| arg :value => v }
    end
  end
end
