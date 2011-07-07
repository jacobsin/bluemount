class Projects
  @@path = '/api/projects'

  class << self

    def create(project)
      RestClient.post Application.uri(@@path), project.to_json, :content_type => :json
    end

    def list
      response = RestClient.get Application.uri(@@path)
      JSON.parse(response.to_str)
    end
  end

end