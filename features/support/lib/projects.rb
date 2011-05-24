class Projects
  @@path = '/rest/v1/projects'

  def self.create(project)
    RestClient.post Application.uri(@@path), project.to_json, :content_type => :json, :accept => :json
  end

  def self.list
    response = RestClient.get Application.uri(@@path)
    JSON.parse(response.to_str)
  end
end