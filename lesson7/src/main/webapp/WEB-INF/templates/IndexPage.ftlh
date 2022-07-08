<#import "commons.ftlh" as c>

<@c.page>
    Welcome on Codeforces!
    <div style="margin: 2rem"></div>
    <div class="article">
        <div class="noData">
            No data
        </div>
        <template>
            <@c.article "Default title" "default info">
                Default text
            </@c.article>
        </template>
    </div>
    <script>
        $(function () {
            const $noData = $(".article .noData");
            $noData.text("Loading...");

            ajax("findAll", {}, response => {
                const result = response["articles"].filter(article => !article.hidden);

                if (result.length === 0) {
                    $noData.text("No data");
                    return false;
                }

                $noData.hide();

                result.forEach(function(article) {
                    const $article = $($(".article template").prop("content")).clone();

                    $article.find(".title").text(article.title);
                    $article.find(".information").text("Article id: " + article.id + " @ " + "Author id: " + article.userId);
                    $article.find(".body").text(article.text);
                    $article.find("article").attr("data-userId", article.id);

                    $(".article").append($article);
                });

                // $(".article tr").click(function() {
                //     ajax("findUser", {userId: $(this).attr("data-userId")},
                //         response => notify(response["user"].creationTime));
                // });
            });
        })
    </script>
<#--    <#list articles as article>-->
<#--        <@c.article article.title "">-->
<#--            ${article.text}-->
<#--        </@c.article>-->
<#--    </#list>-->
</@c.page>
