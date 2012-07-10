[#ftl]
<!DOCTYPE html>
<html lang="en">
[#include "../partials/head.ftl"]
<body>

[#include "../partials/navbar.ftl"]

<div class="container">

  <h1>${projects.size()} Projects</h1>
  <ul>
  [#foreach project in projects]
    <li>${project.title} (${project.owner})</li>
  [/#foreach]
  </ul>

</div> <!-- /container -->

[#include '../partials/scripts.ftl']

</body>
</html>

