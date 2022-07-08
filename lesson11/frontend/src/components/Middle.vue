<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'" :posts="posts" :users="users" :comments="viewComments"/>
            <Enter v-if="page === 'Enter'"/>
            <Users v-if="page === 'Users'" :users="users"/>
            <Post v-if="page === 'Post'" :post="this.currentPost" :comments="viewComments" :users="users" :comments-length="viewComments.length" :on-page="true"/>
            <WritePost v-if="page === 'WritePost'"/>
            <Register v-if="page === 'Register'"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./main/Index";
import Enter from "./main/Enter";
import Register from "./main/Register";
import Users from "./main/Users";
import Post from "./main/Post";
import WritePost from "./main/WritePost";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index",
        }
    },
    components: {
        WritePost,
        Post,
        Users,
        Register,
        Enter,
        Index,
        Sidebar
    },
    props: ["users", "posts", "comments"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        },
        currentPost: function () {
            const currentPostId = Number(localStorage.getItem("currentPost"));
            return Object.values(this.posts).find(p => p.id === currentPostId);
        },
        viewComments: function () {
            return Object.values(this.comments).filter(c => c.post.id === this.currentPost.id)
        },
    },
    beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page);
    },
}
</script>

<style scoped>

</style>
