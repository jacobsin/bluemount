class Api
  @spring = false

  class << self

    def uri(path)
      uri_fragments = %W(http://localhost:#{Application.instance.port})
      uri_fragments << (@spring ? 'spring' : 'guice')
      uri_fragments << path
      File.join uri_fragments
    end

    def path(opts={})
      path = opts.each_pair.reduce(@path_template) do |memo, pair|
        memo.gsub(':'+"#{pair[0]}", pair[1])
      end
      Api.uri path.gsub(/:[^\/]*/, '')
    end

    def sample
      {}
    end

    def spring
      @spring = true
    end

    def reset
      @spring = false
    end
  end
end