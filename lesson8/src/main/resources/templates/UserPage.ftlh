<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
            </tr>
            </thead>
            <tbody>
            <#if currentUser??>
                <tr>
                    <td class="id">${currentUser.id}</td>
                    <td class="login">${currentUser.login}</td>
                    <td class="creationTime">${currentUser.creationTime}</td>
                </tr>
            <#else>
                <tr class="noSuchUser">
                    <td colspan="3">
                        No such user
                    </td>
                </tr>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
