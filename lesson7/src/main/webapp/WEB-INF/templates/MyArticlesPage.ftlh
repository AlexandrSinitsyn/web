<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles datatable">
        <div class="caption">My articles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Hidden</th>
            </tr>
            </thead>
            <tbody>
            <#list articles as article>
                <tr>
                    <td class="article-id">${article.id}</td>
                    <td class="article-title">${article.title}</td>
                    <td class="article-is-hidden">
                        <div class="button-field">
                            <input type="button" value="${article.hidden?then("Show", "Hide")}"
                                   data-article-id="${article.id}"
                                   data-article-hidden="${article.hidden?then("false", "true")}">
                        </div>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
    <script>
        $(function () {
            $(".articles tr .button-field input").click(function() {
                ajax("setHidden", { articleId: $(this).attr("data-article-id"), hidden: $(this).attr("data-article-hidden") },
                    response => {
                        if (!response["redirect"]) {
                            const hidden = $(this).attr("data-article-hidden");
                            $(this).attr("value", hidden === "true" ? "Show" : "Hide");
                            $(this).attr("data-article-hidden", hidden === "true" ? "false" : "true");

                            notify("Hidden was changed");
                            return false;
                        }

                        return true;
                    });
            });
        });
    </script>
</@c.page>
