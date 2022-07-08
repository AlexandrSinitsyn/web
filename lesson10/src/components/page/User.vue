<template>
    <tr>
        <td class="id">{{ user.id }}</td>
        <td class="name">{{ user.name }}</td>
        <td class="login">{{ user.login }}</td>
        <td class="admin">{{ user.admin }}</td>
    </tr>
</template>

<script>
export default {
    name: "User",
    props: ["user"]
}
</script>

<style scoped>

</style>