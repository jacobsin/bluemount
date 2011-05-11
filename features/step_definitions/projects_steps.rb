Given /^project ([A-z]*) exists$/ do |title|
  RestClient.post 'http://localhost:3000/rest/v1/projects', {:title=>title}.to_json, :content_type => :json, :accept => :json
end

When /^I list projects$/ do
  response = RestClient.get 'http://localhost:3000/rest/v1/projects'
  @result = JSON.parse(response.to_str)
end

Then /^it should give me:$/ do |table|
  @result.should == table.hashes
end
