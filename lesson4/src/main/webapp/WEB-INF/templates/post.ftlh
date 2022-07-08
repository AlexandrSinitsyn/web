<#-- @ftlvariable name="post_id" type="java.lang.Integer" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#assign p = c.findBy(posts, "id", post_id)!/>

    <#if p?has_content>
        <@c.full_post p false/>
    <#else>
        No such user
    </#if>
</@c.page>
