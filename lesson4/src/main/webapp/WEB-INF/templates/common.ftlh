<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "Users"},
            {"href": "/posts", "name": "Posts"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <#if item.href == uri>
                            <div class="current-page-name">
                                <a class="ref" href="${item.href}">${item.name}</a>
                            </div>
                        <#else>
                            <a href="${item.href}">${item.name}</a>
                        </#if>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro table caption list_of_table_cols>
    <div class="datatable">
        <div class="caption">${caption}</div>
        <table>
            <thead>
            <tr>
                <#list list_of_table_cols as col>
                    <#if col??>
                        <th>${col}</th>
                    </#if>
                </#list>
            </tr>
            </thead>
            <tbody>
                <#nested/>
            </tbody>
        </table>
    </div>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts as p>
            <@post post=p shorten=true/>
        </#list>
    </aside>
</#macro>

<#macro post post shorten>
<#-- @ftlvariable name="post" type="ru.itmo.web.lesson4.model.Post" -->
    <section>
        <div class="header">Post #${post.id}</div>
        <div class="body">
            <#if shorten && (post.text?length > 250)>
                ${post.text?substring(0, 250)}...
            <#else>
                ${post.text}
            </#if>
        </div>
        <div class="footer">
            <a href="/post?post_id=${post.id}">View all</a>
        </div>
    </section>
</#macro>

<#macro full_post post shorten>
    <article>
        <div class="title">${post.title}</div>
        <div class="information">By <@userlink findBy(users, "id", post.userId) false/></div>
        <div class="body">
            <#if shorten && (post.text?length > 250)>
                ${post.text?substring(0, 250)}&hellip;
            <#else>
                ${post.text}
            </#if>
        </div>
    </article>
</#macro>

<#macro href link>
    <a href="${link}">
        <#nested/>
    </a>
</#macro>

<#macro text class>
    <div class="${class}">
        <#nested/>
    </div>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly>
<#-- @ftlvariable name="user" type="ru.itmo.web.lesson4.model.User" -->
    <#if nameOnly>
        <a class="user-link-name-only" href="/user?handle=${user.handle}">${user.name}</a>
    <#else>
        <a class="user-link user-color-${user.color?lower_case}" href="/user?handle=${user.handle}">${user.name}</a>
    </#if>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function findByShift items value shift>
    <#list items as item>
        <#if item == value>
            <#return items[item_index + shift]/>
        </#if>
    </#list>
</#function>
