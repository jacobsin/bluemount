[#ftl]
<!DOCTYPE HTML>
<html lang="en-US">
<head>
  <meta charset="UTF-8">
  <title></title>
  <link href="/stylesheets/screen.css" media="screen, projection" rel="stylesheet" type="text/css"/>
  <link href="/stylesheets/print.css" media="print" rel="stylesheet" type="text/css"/>
  <!--[if IE]>
  <link href="/stylesheets/ie.css" media="screen, projection" rel="stylesheet" type="text/css"/>
  <![endif]-->
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
