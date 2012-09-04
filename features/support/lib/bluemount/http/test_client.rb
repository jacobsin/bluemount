class TestClient
  class << self
    attr_accessor :session_id

    def post(url, payload='', headers={}, &block)
      RestClient.post url, payload, merge_headers(headers), &(block.nil? ? Response.handler: block)
    end

    def get(url, headers={}, &block)
      RestClient.get url, merge_headers(headers), &(block.nil? ? Response.handler: block)
    end

    def delete(url, headers={}, &block)
      RestClient.delete url, merge_headers(headers), &(block.nil? ? Response.handler: block)
    end

    def put(url, payload, headers={}, &block)
      RestClient.put url, payload, merge_headers(headers), &(block.nil? ? Response.handler: block)
    end

    def merge_headers(overrides)
      defaults = {:content_type => :json}
      defaults[:cookies] = {'JSESSIONID' => @session_id} unless @session_id.nil?
      defaults.merge(overrides)
    end

    def store_session()
      proc { |response, request, result|
        @session_id=response.cookies['JSESSIONID']
      }
    end
  end
end