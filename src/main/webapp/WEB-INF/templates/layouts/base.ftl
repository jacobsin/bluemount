[#ftl]
[#include '*/partials/scripts.ftl']
[#macro base_layout]
<!DOCTYPE html>
<html lang="en">
  [#include "*/partials/head.ftl"]
<body>

  [#include "*/partials/navbar.ftl"]

  [#nested]

</body>
</html>
[/#macro]