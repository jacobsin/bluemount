[#ftl]
[#include  "*/layouts/base.ftl"]
[@base_layout]
<div class="container">

  <h1>File Upload</h1>
  <br/>

  <form class="well form-horizontal" action="/guice/api/fileupload" method="post" enctype="multipart/form-data" accept-charset="utf-8">
    <fieldset>
      <div class="control-group">
        <label class="control-label" for="fileInput">File input</label>

        <div class="controls">
          <input class="input-file" id="fileInput" type="file" name="file">
        </div>
        <div class="form-actions">
          <button type="submit" class="btn btn-primary">Submit</button>
        </div>
      </div>

    </fieldset>
  </form>

</div>

  [@scripts]
  <script type="text/javascript">
    require(['bootstrap'], function () {
      $(function () {

      });
    });
  </script>
  [/@scripts]
[/@base_layout]
