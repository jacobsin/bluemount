class Projects < Api
  @path_template = '/api/projects/:project_type'

  class << self

    def create(project_type, project)
      response = TestClient.post path(:project_type=>project_type.downcase), sample.merge(project).to_json
      JSON.parse(response.to_str)
    end

    def list
      response = TestClient.get path
      JSON.parse(response.to_str)
    end

    def sample
      {
        'title'=>'some-title',
      }
    end
  end

end