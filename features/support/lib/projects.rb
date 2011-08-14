class Projects
  @@path = '/api/projects'

  class << self

    def create(project_type, project)
      response = RestClient.post Application.uri("#{@@path}/#{project_type.downcase}"), sample.merge(project).to_json, :content_type => :json
      JSON.parse(response.to_str)
    end

    def list
      response = RestClient.get Application.uri(@@path), :content_type => :json
      JSON.parse(response.to_str)
    end

    def sample
      {
        'title'=>'some-title',
      }
    end
  end

end