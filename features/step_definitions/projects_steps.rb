Given /^project ([A-z]*) exists$/ do |title|
  Projects.create :title=>title
end

When /^I list projects$/ do
  @result = Projects.list
end

Then /^it should give me:$/ do |table|
  table.hashes.each do |h|
    @result.should include h
  end
end
