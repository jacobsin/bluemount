When /^I upload (.*)$/ do |filename|
  @result = FileUpload.upload TestFiles.file(filename)
end

When /^I upload:$/ do |table|
  @result = FileUpload.upload table.raw.flatten.collect { |f| TestFiles.file(f) }
end

Transform /^table:name,contentType,size,fieldName$/ do |table|
  table.map_column!(:size) { |v| TestFiles.size(v.match(/size of (.*)/)[1]) }
end