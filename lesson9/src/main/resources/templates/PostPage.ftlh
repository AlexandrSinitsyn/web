<#-- @ftlvariable name="comment" type="ru.itmo.wp.domain.Comment" -->
<#-- @ftlvariable name="currentPost" type="ru.itmo.wp.domain.Post" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if currentPost??>
        1
    </#if>
    <@c.post currentPost=currentPost showComments=true/>
    <#if user??>
        <div style="margin: 2rem"></div>
        <div class="form">
            <div class="header">Comment this post</div>
            <div class="body">
                <form method="post" action="">
                    <div class="field">
                        <div class="name">
                            <label for="text"></label>
                        </div>
                        <div class="value">
                            <textarea id="text" name="text">${comment.text!}</textarea>
                        </div>
                        <@c.error "comment.text"/>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Comment">
                    </div>
                </form>
            </div>
        </div>
    </#if>
</@c.page>
