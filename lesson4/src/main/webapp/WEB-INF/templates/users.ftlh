<#import "./common.ftlh" as c>

<@c.page>
    <@c.table "Users", ["User id", "User name", "Handle"]>
        <#list users as u>
            <tr>
                <td>${u.id}</td>
                <td>
                    <#if user?? && user.id == u.id>*</#if>
                    <@c.userlink u false/>
                </td>
                <td>${u.handle}</td>
            </tr>
        </#list>
    </@c.table>
</@c.page>
