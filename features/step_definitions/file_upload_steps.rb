When /^I upload (.*)$/ do | filename |
  @result = FileUpload.upload File.new("#{File.dirname(__FILE__)}/../support/files/#{filename}", 'rb')
end

Transform /^table:name,contentType,size,fieldName$/ do | table |
  table.map_column!(:size) {|v| v.to_i }
end