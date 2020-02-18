<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false"
   aria-controls="collapseExample">
    Message Editor
</a>

<div class="collapse" id="collapseExample">
    <div class="form-group mt-3">
        <form action="/messenger" method="post" enctype="multipart/form-data">

            <div class="form-group">
                <input type="text" name="text" class="form-control" placeholder="Введите сообщение">
                <#if textError??>
                    <div>
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input type="text" name="tag" class="form-control" placeholder="Тэг">
            </div>
            <div class="custom-file">
                <input type="file" name="file" id="customFile">
                <label class="custom-file-label" for="customFile">Choose file</label>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="<#if message??>${message.id}</#if>"/>
            <div class="form-group mt-2">
                <button type="submit" class="btn btn-primary">Save message</button>
            </div>
        </form>
    </div>
</div>
