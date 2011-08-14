Given /^([A-z]*) project ([A-z]*) exists$/ do |project_type, title|
  When "I create #{project_type} project #{title}"
end

When /^I create ([A-z]*) project:$/ do |project_type, table|
  @result = Projects.create table.rows_hash.merge('@type'=>"#{project_type}Project")
end

When /^I create ([A-z]*) project ([A-z]*)$/ do |project_type, title|
  @result = Projects.create 'title'=>title, '@type'=>"#{project_type}Project"
end

When /^I list projects$/ do
  @result = Projects.list
end