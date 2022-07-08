<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">Send Message</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="sendMessage"/>
                <div class="field">
                    <div class="name">
                        <label for="targetUser">Select an user</label>
                    </div>
                    <label>
                        <select size="1" name="selectedUser">
                            <#list users?reverse as user>
                                <option value="user_id_${user.id}">${user.login}</option>
                            </#list>
                        </select>
                    </label>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Text: </label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text"></textarea>
                    </div>
                </div>
                <div class="button-field">
                    <input type="submit" value="Send message">
                </div>
            </form>
        </div>
    </div>
    <div style="margin: 2rem"></div>
    <div class="datatable">
        <div class="caption">Messages</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>SourceUserId</th>
                <th>TargetUserId</th>
                <th>Text</th>
                <th>Time</th>
            </tr>
            </thead>
            <tbody>
            <#list talks?reverse as talk>
            <tr>
                <td>${talk.id}</td>
                <td>${talk.sourceUserLogin}</td>
                <td>${talk.targetUserLogin}</td>
                <td>${talk.text}</td>
                <td>${talk.creationTime}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
