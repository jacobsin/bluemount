namespace :ivy do
  ivy_install_version = '2.3.0-rc1'
  ivy_jar_dir = 'lib/reference'
  ivy_jar_file = "#{ivy_jar_dir}/ivy.jar"

  directory ivy_jar_dir

  def ivy_retrieve(lib_path, org, mod, rev, src=false)
    lib_path << "/src" if src
    conf = src ? "sources" : "default"
    pattern = src ? "lib/#{lib_path}/[artifact]-[conf].[ext]" : "lib/#{lib_path}/[artifact].[ext]"
    ant.retrieve :organisation => org,
                 :module => mod,
                 :revision => rev,
                 :pattern => pattern,
                 :inline => true,
                 :conf => conf
  end

  def ivy_retrieve_all(lib_path, artifacts, src=false)
    artifacts.each_slice(3) do |*artifact|
      artifact[0] << src
      ivy_retrieve(lib_path, *artifact[0])
    end
  end

  task :download => ivy_jar_dir do
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

  task :setup => :install do
    ant.configure :file => 'ivy/ivysettings.xml'
    require 'artifacts'
    ARTIFACTS.each_pair  do |k,v|
      ivy_retrieve_all k, v
    end
  end
end