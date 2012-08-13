class TestFiles
  class << self
    def path(filename)
      "#{File.dirname(__FILE__)}/../files/#{filename}"
    end

    def file(filename)
      File.new(path(filename), 'rb')
    end

    def size(filename)
      File.stat(path(filename)).size
    end
  end
end