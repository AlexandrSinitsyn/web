<template>
    <article>
        <div class="title"><a href="#" @click.prevent="gotoPost"> {{ post.title }}</a> : {{ post.id }}</div>
        <div class="information">By {{ post.userId }}, 17.12.2021</div>
        <div class="body">{{ post.text }}</div>
        <ul class="attachment">
            <li v-for="comment in comments" :key="comment.id">
                Comment from `{{ getUserLogin(comment.userId) }}`: {{ comment.text }}
            </li>
        </ul>
        <div class="footer">
            <div class="left">
                <img src="@/assets/img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="@/assets/img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="@/assets/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                17.12.2021
                <img src="@/assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">{{ commentsLength }}</a>
            </div>
        </div>
    </article>
</template>

<script>
export default {
    name: "Post",
    props: ["post", "comments", "users", "commentsLength"],
    methods: {
        gotoPost: function () {
            this.$root.$emit("setCurrentPost", this.post);
            this.$root.$emit("onChangePage", "Post");
        },
        getUserLogin: function (userId) {
            return Object.values(this.users).find(u => u.id === userId).login
        }
    }
}
</script>

<style scoped>

</style>