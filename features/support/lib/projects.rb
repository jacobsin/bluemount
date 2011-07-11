class Projects
  @@path = '/api/projects'

  class << self

    def create(project)
      response = RestClient.post Application.uri(@@path), sample.merge(project).to_json, :content_type => :json
      JSON.parse(response.to_str)
    end

    def list
      response = RestClient.get Application.uri(@@path)
      JSON.parse(response.to_str)
    end

    def sample
      {
        'title'=>'some-title',
        '@class'=>'bluemount.core.model.OpenSourceProject'
      }
    end
  end

end