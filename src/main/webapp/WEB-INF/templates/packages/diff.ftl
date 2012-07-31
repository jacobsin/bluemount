[#ftl]
[#include  "*/layouts/base.ftl"]
[@base_layout]
<div id="main" class="container-fluid">
</div> <!-- /container -->
  [@scripts]
  <script type="text/javascript">
    require(['app/package/document_diff_view'],
    function () {
      new DocumentDiffView({el:$('#main'), page:7}).render();
    });
  </script>
  [/@scripts]
[/@base_layout]