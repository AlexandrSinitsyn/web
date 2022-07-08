<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
<#--        <form method="post" action="">-->
            <table>
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Status</th>
                    <th>Login</th>
                    <th>Created</th>
                </tr>
                </thead>
                <tbody>
                <#if users?size==0>
                    <tr class="noData">
                        <td colspan="3">
                            No data
                        </td>
                    </tr>
                <#else>
                    <#list users as user>
                        <tr>
                            <td class="id">${user.id}</td>
                            <td class="status">
                                <form method="post" action="">
                                    <input hidden name="login" value="${user.login}"/>
                                    <input hidden name="disabled" value="${user.disabled?then("false", "true")}"/>
                                    <input type="submit" value="${user.disabled?then("Enable", "Disable")}"/>
                                </form>
                            </td>
                            <td class="login">${user.login}</td>
                            <td class="creationTime">${user.creationTime}</td>
                        </tr>
                    </#list>
                </#if>
                </tbody>
            </table>
<#--        </form>-->
    </div>
</@c.page>
