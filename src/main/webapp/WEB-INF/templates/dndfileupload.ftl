[#ftl]
[#include  "*/layouts/base.ftl"]
[@base_layout]
<div class="container">

  <h1>Drag n Drop File Upload</h1>
  <br/>

  <form class="well form-horizontal" accept-charset="utf-8">
    <fieldset>
      <div class="control-group">
        <label class="control-label" for="fileupload">File Uploads</label>

        <div class="controls">
          <input style="display:none;" class="input-file" id="fileupload" type="file" name="files[]" data-url="/guice/api/fileupload">
          <table class="row fileupload table table-condensed" style="margin-left: 0;">
            <tbody>
            </tbody>
          </table>
        </div>
      </div>

      <div class="form-actions">
        <button type="submit" class="btn btn-primary">Submit</button>
      </div>

    </fieldset>
  </form>

</div>

  [@scripts]
  <script type="text/javascript">
    require(['jquery.fileupload', 'underscore'], function () {
      $(function () {
        function bytesToSize(bytes, precision) {
          var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
          var posttxt = 0;
          if (bytes == 0) return 'n/a';
          while( bytes >= 1024 ) {
            posttxt++;
            bytes = bytes / 1024;
          }
          return Number(bytes).toFixed(precision) + " " + sizes[posttxt];
        }

        $('#fileupload').fileupload({
          dataType:'json',
          add:function (e, data) {
            _.each(data.files, function (file, index) {
              data.context = $('<tr> <td class="span2 name">' + file.name + '</td> <td class="span2 size">' + bytesToSize(file.size, 2) + '</td> <td class="span2"><div class="progress progress-striped progress-success active" style="margin-bottom: 0;"><div class="bar" style="width: 0%">0%</div></div></td> </tr>').appendTo($('table.fileupload tbody'));
              data.submit();
            });
          },
          progress:function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            console.log(data, progress);
            data.context.find('div.progress').find('div.bar').css('width', progress + '%').text(progress + '%');
          },
          done:function (e, data) {
            _.each(data.result, function (file, index) {
              data.context.find('div.progress').removeClass('active').find('div.bar').css('width', '100%').text('Complete');
            });
          }
        });
      });
    });
  </script>
  [/@scripts]
[/@base_layout]
