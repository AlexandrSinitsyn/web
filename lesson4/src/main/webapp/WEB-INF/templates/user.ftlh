<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="previous" type="ru.itmo.web.lesson4.model.User" -->
<#-- @ftlvariable name="next" type="ru.itmo.web.lesson4.model.User" -->
<#-- @ftlvariable name="user_id" type="java.lang.Integer" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if user_id??>
        <#if !user_id?is_number>
            <#assign user_id = -1/>
        </#if>
        <#assign u = c.findBy(users, "id", user_id)!/>
    <#else>
        <#assign u = c.findBy(users, "handle", handle)!/>
    </#if>

    <#if u?has_content>
        <#assign user = u/>
        <#assign previous = c.findByShift(users, user, -1)!/>
        <#assign next = c.findByShift(users, user, 1)!/>

        <div class="user-box">
            <div class="user-description">
                <div class="info">
                    <@c.userlink user false/>
                    <ul>
                        <li>id: ${u.id}</li>
                        <li>handle: ${u.handle}</li>
                        <li>name: ${u.name}</li>
                    </ul>
                </div>
                <div class="img">
                    <img src="/img/no-title.png" alt="No user image uploaded">
                </div>
            </div>
            <div class="user-posts">
                <a href="/posts?user_id=${u.id}">View all this user's posts</a>
            </div>
        </div>
        <div class="user-pointer">
            <#if previous?has_content>
                <@c.href "/user?handle=${previous.handle}"> ← </@c.href>
            <#else>
                <@c.text "previous"> ← </@c.text>
            </#if>
            <#if next?has_content>
                <@c.href "/user?handle=${next.handle}"> → </@c.href>
            <#else>
                <@c.text "next"> → </@c.text>
            </#if>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
