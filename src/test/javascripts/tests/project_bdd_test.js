require(['app/project', 'sinon-qunit'], function (Project) {

  pavlov.specify("Projects", function() {
    describe("initialize", function() {

      before(function() {
        clock = sinon.useFakeTimers();
        now = moment();
        project = new Project();
      });

      after(function() {
        clock.restore();
      });

      it("should have OpenSource type", function() {
        assert(project.get("type")).equals("OpenSource");
      });

      it("should have Created time", function(){
        assert(project.get("created")).equals(now.utc().format());
      });

      describe("start", function() {
        before(function() {
          project.start();
        });

        it("should be started", function () {
          assert(project.get("started")).isTrue();
        });
      });

      describe("OpenSourceProject start", function() {
        before(function() {
          project = new OpenSourceProject();
          project.start();
        });

        it("should be started", function () {
          assert(project.get("started")).isTrue();
        });

        it("should be incubating", function () {
          assert(project.get("incubating")).isTrue();
        });
      });

    });

  });

});