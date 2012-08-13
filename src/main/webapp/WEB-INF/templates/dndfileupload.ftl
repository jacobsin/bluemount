[#ftl]
[#include  "*/layouts/base.ftl"]
[@base_layout]
<div class="container">

  <h1>Drag n Drop File Upload</h1>
  <br/>

  <form class="well form-horizontal">
    <fieldset>
      <div class="control-group">
        <label class="control-label" for="fileupload">File input</label>

        <div class="controls">
          <input class="input-file" id="fileupload" type="file" name="files[]" data-url="/guice/api/fileupload">
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
    require(['jquery.fileupload'], function () {
      $(function () {
        $('#fileupload').fileupload({
          dataType: 'json',
          done: function (e, data) {
            $.each(data.result, function (index, file) {
              $('<p/>').text(file.name).appendTo(document.body);
            });
          }
        });
      });
    });
  </script>
  [/@scripts]
[/@base_layout]
