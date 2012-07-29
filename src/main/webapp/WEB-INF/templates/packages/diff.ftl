[#ftl]
[#include  "*/layouts/base.ftl"]
[@base_layout data_main="app/packages/document_diff_main"]
<div id="main" class="container-fluid">

  <h2>LA - New Warrant ETF</h2>
  <br>
  <div class="row-fluid">
    <div class="span6">
      <p>Before</p>
      <div id="before">
        <canvas class="viewer"></canvas>
      </div>
    </div>
    <div class="span6">
      <p>After</p>
      <div id="after">
        <canvas class="viewer"></canvas>
      </div>
    </div>
  </div>
  <div class="row-fluid">
    <div class="span6">
      <label>Comment</label>
      <textarea class="span12"></textarea>
    </div>
    <div class="span6">
      <button class="pull-right btn"><i class="icon-remove"></i> Close</button>
    </div>
  </div>
</div> <!-- /container -->
[/@base_layout]