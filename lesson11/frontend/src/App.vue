<template>
    <div id="app">
        <Header :user="user"/>
        <Middle :users="users" :posts="posts" :comments="comments"/>
        <Footer/>
    </div>
</template>

<script>
import Header from "./components/Header";
import Middle from "./components/Middle";
import Footer from "./components/Footer";
import axios from "axios"

export default {
    name: 'App',
    components: {
        Footer,
        Middle,
        Header
    },
    data: function () {
        return {
            user: null,
            users: [],
            posts: [],
            comments: [],
        }
    },
    beforeMount() {
        if (localStorage.getItem("jwt") && !this.user) {
            this.$root.$emit("onJwt", localStorage.getItem("jwt"));
        }

        axios.get("/api/1/users").then(response => {
            this.users = response.data;
        });

        axios.get("/api/1/posts").then(response => {
            this.posts = response.data;
        });

        axios.get("/api/1/comments").then(response => {
            this.comments = response.data;
        });
    },
    beforeCreate() {
        this.$root.$on("onEnter", (login, password) => {
            if (password === "") {
                this.$root.$emit("onEnterValidationError", "Password is required");
                return;
            }

            axios.post("/api/1/jwt", {
                    login, password
            }).then(response => {
                localStorage.setItem("jwt", response.data);
                this.$root.$emit("onJwt", response.data);
            }).catch(error => {
                this.$root.$emit("onEnterValidationError", error.response.data);
            });
        });

        this.$root.$on("onRegister", (name, login, password) => {
            if (name === null || name.length < 1 || name.length > 32 || !/[a-z]{1,32}/.test(name)) {
                this.$root.$emit("onRegisterValidationError", "Name should contain only 1 to 32 latin letters");
                return;
            }

            if (login === null || login.length < 3 || login.length > 16 || !/[a-z]{3,16}/.test(login)) {
                this.$root.$emit("onRegisterValidationError", "Login should contain only 3 to 16 latin letters");
                return;

            }

            if (password === "") {
                this.$root.$emit("onRegisterValidationError", "Password is required");
                return;
            }

            axios.post("/api/1/register", {
                name, login, password
            }).then(response => {
                localStorage.setItem("jwt", response.data);
                axios.get("/api/1/users").then(response => {
                    this.users = response.data;
                });
                this.$root.$emit("onJwt", response.data);
            }).catch(error => {
                this.$root.$emit("onRegisterValidationError", error.response.data);
            });
        });

        this.$root.$on("onJwt", (jwt) => {
            localStorage.setItem("jwt", jwt);

            axios.get("/api/1/users/auth", {
                params: {
                    jwt
                }
            }).then(response => {
                this.user = response.data;
                this.$root.$emit("onChangePage", "Index");
            }).catch(() => this.$root.$emit("onLogout"))
        });

        this.$root.$on("onLogout", () => {
            localStorage.removeItem("jwt");
            this.user = null;
        });

        this.$root.$on("onWritePost", (title, text) => {
            const jwt = localStorage.getItem("jwt")

            if (jwt) {
                if (!title || title.trim().length < 5) {
                    this.$root.$emit("onWritePostValidationError", "Title is too short or contains only spaces");
                } else if (!text || text.trim().length < 10) {
                    this.$root.$emit("onWritePostValidationError", "Text is too short or contains only spaces");
                } else {
                    axios.post("/api/1/writePost", {
                        title, text, userJwt: jwt
                    }).then(() => {
                        axios.get("/api/1/posts").then(response => {
                            this.posts = response.data;
                        });

                        this.$root.$emit("onChangePage", "Index");
                    }).catch(error => {
                        this.$root.$emit("onWritePostValidationError", error.response.data);
                    });
                }
            } else {
                this.$root.$emit("onWritePostValidationError", "No access");
            }
        });

        this.$root.$on("onWriteComment", (postId, text) => {
            const jwt = localStorage.getItem("jwt")

            if (jwt) {
                if (!text || text.trim().length < 10) {
                    this.$root.$emit("onCommentValidationError", "Text is too short or contains only spaces");
                } else {
                    axios.post("/api/1/commentPost", {
                        postId, text, userJwt: jwt
                    }).then(() => {
                        axios.get("/api/1/comments").then(response => {
                            this.comments = response.data;
                        });
                    }).catch(error => {
                        this.$root.$emit("onCommentValidationError", error.response.data);
                    });
                }
            } else {
                this.$root.$emit("onCommentValidationError", "No access");
            }
        });
    }
}
</script>

<style>
#app {

}
</style>
