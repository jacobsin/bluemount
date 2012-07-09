[#ftl]
<!DOCTYPE HTML>
<html lang="en-US">
<head>
  <meta charset="UTF-8">
  <title></title>
</head>
<body>
<h1>${projects.size()} Projects</h1>
<ul>
[#foreach project in projects]
  <li>${project.title} (${project.owner})</li>
[/#foreach]
</ul>
</body>
</html>
