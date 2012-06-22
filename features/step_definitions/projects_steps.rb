Given /^([A-z]*) project ([A-z]*) exists$/ do |project_type, title|
  step "I create #{project_type} project #{title}"
end

When /^I create ([A-z]*) project:$/ do |project_type, table|
  @result = Projects.create project_type, table.rows_hash
end

When /^I create ([A-z]*) project ([A-z]*)$/ do |project_type, title|
  @result = Projects.create project_type, 'title'=>title
end

When /^I list projects$/ do
  @result = Projects.list
end