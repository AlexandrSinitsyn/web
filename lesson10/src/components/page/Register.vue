<template>
    <div class="form">
        <div class="header">Register</div>
        <div class="body">
            <form @submit.prevent="onRegister">
                <div class="field">
                    <div class="name">
                        <label for="name">Name:</label>
                    </div>
                    <div class="value">
                        <input id="name" name="name" v-model="name"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="login">Login:</label>
                    </div>
                    <div class="value">
                        <input id="login" name="login" v-model="login"/>
                    </div>
                </div>
                <div class="error">{{ error }}</div>
                <div class="button-field">
                    <input type="submit" value="Register">
                </div>
            </form>
        </div>
    </div>
</template>

<script>
export default {
    name: "Register",
    data: function () {
        return {
            name: "",
            login: "",
            error: ""
        }
    },
    methods: {
        onRegister: function () {
            this.error = "";
            this.$root.$emit("onRegister", this.name, this.login);
        }
    },
    beforeMount() {
        this.$root.$on("onRegisterValidationError", error => this.error = error);
    }
}
</script>

<style scoped>

</style>