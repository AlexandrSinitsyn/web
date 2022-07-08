<#-- @ftlvariable name="posts" type="ru.itmo.wp.domain.Post[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#list posts as post>
        <@c.post currentPost=post showComments=false/>
    </#list>
</@c.page>
