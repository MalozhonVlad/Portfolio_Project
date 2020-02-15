<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="css/registration.css">

    <link href="https://fonts.googleapis.com/css?family=Permanent+Marker" rel="stylesheet">

</head>
<body>


<div class="signup-form">
    <form action="/registration" method="post">
        <h1>Sign Up</h1>
        <#if message??>
            <h2>${message}</h2>
        </#if>
        <input type="text" name="username" placeholder="Username" class="txtb">
        <input type="password" name="password" placeholder="Password" class="txtb">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <input type="email" name="email" placeholder="Email" class="txtb">

        <input type="submit" value="Create Account" class="signup-btn">

    </form>
</div>



</body>
</html>