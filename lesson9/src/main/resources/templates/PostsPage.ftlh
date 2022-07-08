<#import "commons.ftlh" as c>

<@c.page>
<#list user.posts as post>
    <@c.post currentPost=post showComments=false/>
</#list>
</@c.page>
