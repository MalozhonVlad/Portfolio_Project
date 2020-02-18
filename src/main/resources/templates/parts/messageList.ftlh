
<div class="card-columns">
    <#list messages as message>
        <div class="card my-3">
            <#if message.filename??>
                <img src="${message.filename}" class="card-img-top">
            </#if>
            <div class="m-2">
                <span>${message.text}</span><br>
                <i>#${message.tag}</i>
            </div>
            <div class="card-footer text-muted">
                ${message.author.username}
            </div>
        </div>
    <#else>
        No message
    </#list>
</div>