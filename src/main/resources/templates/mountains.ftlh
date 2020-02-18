<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Cool Effects</title>

    <link rel="stylesheet" type="text/css" href="/css/mountains.css">
</head>
<body>

<section class="zoom">
    <img src="/css/mountain1.png" id="layer1">
    <img src="/css/mountain2.png" id="layer2">
    <img src="/css/text.png" id="text">
</section>
<section class="content">
    <h2>Simple Background Parralax</h2>
    <p>Some text sssssssssssssssssss
    ssssssssssssssssssssssssssssssssss
    sssssssssssssssssssssssssss
    sssssssssssssssssssssss</p>
</section>
    <script type="text/javascript">
        var layer1 = document.getElementById('layer1')
        scroll = window.pageYOffset;
        document.addEventListener('scroll', function (e) {
            var offset = window.pageYOffset;
            scroll = offset;
            layer1.style.width = (100 + scroll/5) + '%';
        });

        var layer2 = document.getElementById('layer2')
        scroll = window.pageYOffset;
        document.addEventListener('scroll', function (e) {
            var offset = window.pageYOffset;
            scroll = offset;
            layer2.style.width = (100 + scroll/5) + '%';
            layer2.style.left = scroll/50 + '%';
        });

        var text = document.getElementById('text')
        scroll = window.pageYOffset;
        document.addEventListener('scroll', function (e) {
            var offset = window.pageYOffset;
            scroll = offset;
            layer2.style.width = (100 + scroll/5) + '%';
            text.style.top = - scroll/20 + '%';
        });

    </script>
</body>
</html>