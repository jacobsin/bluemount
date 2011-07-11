Then /^it should give:$/ do |table|
  table.rows_hash.each do |k,v|
    @result[k].should == v
  end
end

Then /^it should give at least these:$/ do |expected|
  actual = Cucumber::Ast::Table.new @result
  expected.diff!(actual, {:surplus_row => false})
end