<template>
    <div id="app">
        <Header></Header>
        <main>
            <router-view/>
        </main>
        <Footer></Footer>
    </div>
</template>

<script>
    import Footer from "./views/static/Footer";
    import Header from "./views/static/Header";

    export default {
        components: {Footer, Header},
        mounted() {
            this.initMaterialize()
        },
        updated() {
            this.initMaterialize()
        },
        methods: {
            initMaterialize() {
                // eslint-disable-next-line no-undef
                M.AutoInit();
                const elem = document.querySelector('.collapsible.expandable');
                // eslint-disable-next-line no-undef
                M.Collapsible.init(elem, {
                    accordion: false
                });
            }
        }
    }
</script>

<style lang="scss">
</style>
