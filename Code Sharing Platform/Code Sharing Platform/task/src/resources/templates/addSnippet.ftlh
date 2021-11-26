<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create</title>
    <link rel="stylesheet"
           target="_blank" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<textarea id="code_snippet"></textarea>
<input id="time_restriction" type="text"/>
<input id="views_restriction" type="text"/>
<button id="send_snippet" onclick="send()" type="submit">Submit</button>
<script>
  function send() {
    let object = {
      "code": document.getElementById("code_snippet").value,
      "time": document.getElementById("time_restriction").value,
      "views": document.getElementById("views_restriction").value,
    };

    let json = JSON.stringify(object);

    let xhr = new XMLHttpRequest();
    xhr.open("POST", '/api/code/new', false);
    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    xhr.send(json);
    if (xhr.status == 200) {
     alert("Success!");
    }
  }
</script>
</body>
</html>
