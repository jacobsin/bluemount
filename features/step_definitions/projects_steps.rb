require 'rspec/expectations'

Given /^project ([A-z]*) exists$/ do |title|
  @projects ||= []
  @projects << {'title'=>title}
end

When /^I list projects$/ do
  @result = @projects
end

Then /^it should give me:$/ do |table|
  table.hashes.should == @projects
end
