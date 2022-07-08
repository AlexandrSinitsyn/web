<template>
    <footer>
        <div>Total count of users: {{ usersCount }}, posts: {{ postsCount }}</div>
        <a href="/">Codeforces</a> &copy; 2021 by Mike Mirzayanov
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["usersCount", "postsCount"]
}
</script>

<style scoped>

</style>