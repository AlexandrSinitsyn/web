<template>
    <div>
        <article>
            <div class="title"><a href="#" @click.prevent="gotoPost"> {{ post.title }}</a> : {{ post.id }}</div>
            <div class="information">By {{ getUserLogin(post.user.id) }}, 17.12.2021</div>
            <div class="body">{{ post.text }}</div>
            <ul class="attachment">
                <li v-for="comment in comments" :key="comment.id">
                    Comment from `{{ getUserLogin(comment.user.id) }}`: {{ comment.text }}
                </li>
            </ul>
            <div class="footer">
                <div class="left">
                    <img src="../../assets/img/voteup.png" title="Vote Up" alt="Vote Up"/>
                    <span class="positive-score">+173</span>
                    <img src="../../assets/img/votedown.png" title="Vote Down" alt="Vote Down"/>
                </div>
                <div class="right">
                    <img src="../../assets/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                    17.12.2021
                    <img src="../../assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
                    <a href="#">{{ commentsLength }}</a>
                </div>
            </div>
        </article>
        <div v-if="onPage">
            <div style="margin-bottom: 2rem"></div>
            <div class="form">
                <div class="header">Write a comment</div>
                <div class="body">
                    <form @submit.prevent="onComment">
                        <div class="field">
                            <div class="name">
                                <label for="text">Text:</label>
                            </div>
                            <div class="value">
                                <textarea id="text" name="text" v-model="text"/>
                            </div>
                        </div>
                        <div class="error">{{ error }}</div>
                        <div class="button-field">
                            <input type="submit" value="Comment">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "Post",
    props: ["post", "comments", "users", "commentsLength", "onPage"],
    data: function () {
        return {
            text: "",
            error: ""
        }
    },
    methods: {
        gotoPost: function () {
            localStorage.setItem("currentPost", this.post.id);

            this.$root.$emit("onChangePage", "Post");
        },
        getUserLogin: function (userId) {
            return Object.values(this.users).find(u => u.id === userId).login
        },
        onComment: function () {
            this.error = "";
            this.$root.$emit("onWriteComment", this.post.id, this.text);
            this.text = ""
        }
    },
    beforeMount() {
        this.$root.$on("onCommentValidationError", error => this.error = error);
    }
}
</script>

<style scoped>

</style>