<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Messenger</title>
</head>
<body>

<form action="/messenger" method="post" enctype="multipart/form-data">
    <input type="text" name="text" placeholder="Введите сообщение">
    <input type="text" name="tag" placeholder="Тэг">
    <input type="file" name="file">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit">Добавить</button>
</form>

<#--<#if messages??>-->
<#--    <#list messages as message>-->
<#--        <div>-->
<#--            <#if message.filename??>-->
<#--                <img src="${message.filename}" class="card-img-top">-->
<#--            </#if>-->
<#--            <span>${message.text}</span>-->
<#--            <i>${message.tag}</i>-->
<#--            <strong>${message.author.username}</strong>-->
<#--        </div>-->
<#--    </#list>-->
<#--</#if>-->

<#if messages??>
    <div class="card-columns">
        <#list messages as message>
            <div class="card my-3">
                <#if message.filename??>
                    <img src="${message.filename}" width="250" height="250">
                </#if>
                <div class="m-2">
                    <span>${message.text}</span>
                </div>
                <#if textError??>
                    <div>
                        ${textError}
                    </div>
                </#if>
                <div>
                    <i>${message.tag}</i>
                </div>
                <#if textError??>
                    <div>
                        ${textError}
                    </div>
                </#if>
                <div class="card-footer text-muted">
                    ${message.author.username}
                </div>
            </div>
        <#else>
            No message
        </#list>
    </div>
</#if>


</body>
</html>