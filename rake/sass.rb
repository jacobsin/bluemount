require 'compass'
require 'compass/commands'

namespace :sass do
  working_dir = Rake.application.original_dir

  desc "Compile sass"
  task :compile do
    Compass::Commands::UpdateProject.new(working_dir, :quiet=>false, :force=>true).perform
  end

  desc "Watch sass"
  task :watch do
    Compass::Commands::WatchProject.new(working_dir, :quiet=>false).perform
  end
end