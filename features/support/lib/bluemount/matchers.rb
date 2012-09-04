require 'rspec/matchers'

module Matchers
  RSpec::Matchers.define :be_valid_timestamp do
    match do |actual|
      begin
        Date.strptime(actual, '%F')
        true
      rescue :argument_error
        false
      end
    end
  end
end