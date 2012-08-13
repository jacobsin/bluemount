[#ftl]
[#include  "*/layouts/base.ftl"]
[@base_layout]
<div class="container">

  <h1>${projects.size()} Projects</h1>
  <ul>
    [#foreach project in projects]
      <li>${project.title} (${project.owner})</li>
    [/#foreach]
  </ul>

</div>
[/@base_layout]