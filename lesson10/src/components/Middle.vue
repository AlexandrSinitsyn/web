<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'" :posts="posts" :comments="comments"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <Users v-if="page === 'Users'" :users="viewUsers"/>
            <Post v-if="page === 'Post'" :post="currentPost" :comments="postComments" :users="users" :comments-length="postComments.length"/>
            <WritePost v-if="page === 'WritePost'"/>
            <EditPost v-if="page === 'EditPost'"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./page/Index";
import Enter from "./page/Enter";
import WritePost from "./page/WritePost";
import EditPost from "./page/EditPost";
import Register from "@/components/page/Register";
import Users from "@/components/page/Users";
import Post from "@/components/page/Post";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index"
        }
    },
    components: {
        Post,
        Users,
        Register,
        WritePost,
        Enter,
        Index,
        Sidebar,
        EditPost
    },
    props: ["users", "posts", "postId", "comments"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        },
        viewUsers: function () {
            return Object.values(this.users).sort((a, b) => a.id - b.id);
        },
        currentPost: function () {
            return Object.values(this.posts).find(p => p.id === this.postId)
        },
        postComments: function () {
            return Object.values(this.comments).filter(c => c.postId === this.currentPost.id);
        }
    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page)
    }
}
</script>

<style scoped>

</style>
