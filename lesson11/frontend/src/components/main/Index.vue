<template>
    <div class="posts">
        <Post v-for="post in viewPosts" :post="post" :comments="[]" :users="users"
              :comments-length="commentsCount(post)" :on-page="false" :key="post.id"/>
    </div>
</template>

<script>
import Post from "./Post";

export default {
    name: "Index",
    props: ["users", "posts", "comments"],
    components: {Post},
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id);
        },
        commentsMap: function () {
            const map = new Array(this.posts[0].id + 1).fill(0);

            Object.values(this.comments).forEach(c => map[c.post.id]++);

            return map;
        }
    },
    methods: {
        commentsCount: function (post) {
            return this.commentsMap[post.id]
        }
    }
}
</script>

<style scoped>

</style>
