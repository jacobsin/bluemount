require 'ant'

namespace :ivy do
  ivy_install_version = '2.3.0-rc1'
  ivy_jar_dir = 'lib/reference'
  ivy_jar_file = "#{ivy_jar_dir}/ivy.jar"
  ant_proxy ={
    #:proxyhost=>'hk-proxy.ap.hedani.net', :proxyport=>'8080'
  }

  directory ivy_jar_dir

  def ivy_retrieve(opts)
    conf = opts[:src] ? "sources" : "default"
    pattern = opts[:src] ? "lib/#{opts[:lib_path]}/src/[artifact]-[conf].[ext]" : "lib/#{opts[:lib_path]}/[artifact].[ext]"
    puts opts
    ant.retrieve :organisation => opts[:org],
                 :module => opts[:mod],
                 :revision => opts[:rev],
                 :pattern => pattern,
                 :inline => true,
                 :conf => conf,
                 :transitive => opts[:transitive]
  end

  def ivy_retrieve_all(lib_path, artifacts, src=false)
    artifacts.each do |artifact|
      args = artifact.split(':')
      ivy_retrieve(:lib_path => lib_path, :org=>args[0], :mod=>args[1], :rev=>args[2], :src=>src, :transitive=>(args[3]!='false'))
    end
  end

  task :download => ivy_jar_dir do
    ant.setproxy ant_proxy
    ant.get :src => "http://repo1.maven.org/maven2/org/apache/ivy/ivy/#{ivy_install_version}/ivy-#{ivy_install_version}.jar",
            :dest => ivy_jar_file,
            :usetimestamp => true
  end

  task :install => :download do
    ant.path :id => 'ivy.lib.path' do
      fileset :dir => ivy_jar_dir, :includes => '*.jar'
    end

    ant.taskdef :resource => "org/apache/ivy/ant/antlib.xml",
                :classpathref => "ivy.lib.path"
  end

  task :configure do
    ant.configure :file => 'ivy/ivysettings.xml'
  end

  desc "Download all artifacts"
  task :artifacts => [:install, :configure] do
    require 'rake/artifacts'
    ARTIFACTS.each_pair  do |k,v|
      ivy_retrieve_all k, v
    end
  end

  desc "Download all artifacts' sources"
  task :sources => [:install, :configure] do
    require 'rake/artifacts'
    ARTIFACTS.each_pair  do |k,v|
      ivy_retrieve_all k, v, src=true
    end
  end
end