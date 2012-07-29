[#ftl]
[#macro base_layout data_main="app"]
<!DOCTYPE html>
<html lang="en">
  [#include "*/partials/head.ftl"]
<body>

  [#include "*/partials/navbar.ftl"]

  [#nested]

  [#include '*/partials/scripts.ftl']

</body>
</html>
[/#macro]