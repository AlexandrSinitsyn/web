<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th class="admin-field">Admin</th>
                <th>Login</th>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user-id"></td>
                    <td class="user-admin admin-field">
                        <div class="button-field">
                            <input type="button" value="Enable">
                        </div>
                    </td>
                    <td class="user_login"></td>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            ajax("findAll", {}, response => {
                response["users"].forEach(function(user) {
                    $noData.hide();
                    const $tr = $($(".users template").prop("content")).clone();
                    $tr.find(".user-id").text(user["id"]);
                    let admBtn = $tr.find(".user-admin .button-field input");
                    admBtn.attr("value", (user["admin"] ? "Disable" : "Enable"));
                    admBtn.attr("data-flag", user["admin"]);
                    admBtn.click(function () {
                        ajax("setAdmin", { onClickUserId: user["id"] }, _ => {})
                    });
                    $tr.find(".user_login").text(user["login"]);
                    $tr.find("tr").attr("data-userId", user["id"]);
                    $(".users tbody").append($tr);
                });

                $(".users tr").click(function() {
                    ajax("findUser", {userId: $(this).attr("data-userId")},
                        response => notify(response["user"].creationTime));
                });

                const currentUser = response["user"];
                if (!currentUser["admin"]) {
                    $(".admin-field").hide();
                }
            });
        });
    </script>
</@c.page>
