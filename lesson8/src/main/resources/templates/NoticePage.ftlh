<#import "commons.ftlh" as c>

<@c.page>
    <div class="registration form-box">
        <div class="header">Add notice</div>
        <div class="body">
            <form method="post" action="">
                <div class="field">
                    <div class="name">
                        <label for="content">Notice content</label>
                    </div>
                    <div class="value">
                        <input id="content" name="content" value="${newNotice.content!}"/>
                    </div>
                    <@c.error "newNotice.content"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Add notice">
                </div>
            </form>
        </div>
    </div>
</@c.page>
