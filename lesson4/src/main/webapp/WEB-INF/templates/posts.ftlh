<#-- @ftlvariable name="user_id" type="java.lang.Integer" -->

<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?reverse as p>
        <#if user_id??>
            <#if user_id?is_string>
            <#else>
                <#if p.userId == user_id>
                    <@c.full_post p false/>
                </#if>
            </#if>
        <#else>
            <@c.full_post p false/>
        </#if>
    </#list>
</@c.page>
