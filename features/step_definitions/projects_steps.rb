Given /^project ([A-z]*) exists$/ do |title|
  RestClient.post Application.api('/rest/v1/projects'), {:title=>title}.to_json, :content_type => :json, :accept => :json
end

When /^I list projects$/ do
  response = RestClient.get Application.api('/rest/v1/projects')
  @result = JSON.parse(response.to_str)
end

Then /^it should give me:$/ do |table|
  table.hashes.each do |h|
    @result.should include h
  end
end
