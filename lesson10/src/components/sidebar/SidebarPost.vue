<template>
    <section>
        <div class="header">
            {{ post.title }}
        </div>
        <div class="body">
            {{ post.text }}
        </div>
        <div class="footer">
            <a href="#" @click.prevent="gotoPost">View all</a>
        </div>
    </section>
</template>

<script>
export default {
    name: "SidebarPost",
    props: ["post"],
    methods: {
        gotoPost: function () {
            this.$root.$emit("setCurrentPost", this.post);
            this.$root.$emit("onChangePage", "Post");
        }
    }
}
</script>

<style scoped>
section {
    margin-bottom: 1rem;
}
</style>