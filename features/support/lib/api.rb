class Api
  @@spring = false

  class << self

    def uri(path)
      uri_fragments = ["http://localhost:#{Application.instance.port}"]
      uri_fragments << (@@spring ? 'spring' : 'guice')
      uri_fragments << path
      File.join uri_fragments
    end

    def spring
      @@spring = true
    end

    def reset
      @@spring = false
    end
  end
end