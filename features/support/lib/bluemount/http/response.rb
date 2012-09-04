class Response
  class << self
    attr_accessor :failed, :handler, :code

    def reset
      @handler = nil
      @code = nil
    end

    def expect_error
      @handler = proc { |response, request, result|
        @failed = true unless response.code == 200
        @code = response.code
        response
      }
    end
  end
end