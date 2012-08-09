class FileUpload
  @@path = '/api/fileupload'

  class << self

    def upload(file)
      response = RestClient.post Application.uri(@@path), :myfile => file
      JSON.parse(response.to_str)
    end

    def get(id)
      response = RestClient.get Application.uri("#{@@path}/#{id}")
    end

  end

end