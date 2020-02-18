<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
   aria-controls="collapseExample">
    Message Editor
</a>

<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">

            <div class="form-group">
                <input type="text" name="text" class="form-control ${(textError??)?string('is-invalid', '')}"
                       value="<#if message??>${message.text}</#if>" placeholder="Введите сообщение">
                <#if text??>
                    <div class="invalid-feedback">
                        ${text}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <input type="text" name="tag" value="<#if message??>${message.tag}</#if>"
                       class="form-control" placeholder="Тэг">
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="<#if message??>${message.id}</#if>"/>
            <div class="form-group mt-2">
                <button type="submit" class="btn btn-primary">Save message</button>
            </div>
        </form>
        <form method="get" action="/deleteMessage/<#if message??>${message.id}</#if>">
            <div class="form-group mt-2">
                <button type="submit" class="btn btn-danger">Delete Message</button>
            </div>
        </form>
    </div>
</div>
