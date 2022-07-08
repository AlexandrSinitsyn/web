<#-- @ftlvariable name="events" type="ru.itmo.wp.model.domain.Event[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">Events</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>UserId</th>
                <th>Type</th>
                <th>Time</th>
            </tr>
            </thead>
            <tbody>
            <#list events?reverse as event>
            <tr>
                <td>${event.id}</td>
                <td>${event.userId}</td>
                <td>${event.type}</td>
                <td>${event.creationTime}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
