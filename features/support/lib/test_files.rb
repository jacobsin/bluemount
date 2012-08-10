class TestFiles
  class << self
    def path(filename)
      "#{File.dirname(__FILE__)}/../files/#{filename}"
    end

    def size(filename)
      File.stat(path(filename)).size
    end

    def file(filename, mode)
      File.new(path(filename), mode)
    end
  end
end