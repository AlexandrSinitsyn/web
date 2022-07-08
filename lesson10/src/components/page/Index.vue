<template>
    <div class="posts">
        <Post v-for="post in viewPosts" :post="post" :comments="[]" :users="users" :comments-length="commentsLength(post)" :key="post.id"/>
    </div>
</template>

<script>
import Post from "@/components/page/Post";
export default {
    name: "Index",
    props: ["users", "posts", "comments"],
    components: {Post},
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id)
        }
    },
    methods: {
        commentsLength: function (post) {
            return Object.values(this.comments).filter(c => c.postId === post.id).length
        }
    }
}
</script>

<style scoped>

</style>
