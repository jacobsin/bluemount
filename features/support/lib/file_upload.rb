class FileUpload
  @@path = '/api/fileupload'

  class << self

    def upload(files)
      files = [files] if (files.is_a? File)
      response = RestClient.post Application.uri(@@path), to_file_fields(files)
      JSON.parse(response.to_str)
    end

    def get(id)
      response = RestClient.get Application.uri("#{@@path}/#{id}")
    end

    def to_file_fields(files)
      Hash[*files.to_enum(:each_with_index).collect{|f, i| ["file#{i+1}", f]}.flatten]
    end

  end

end