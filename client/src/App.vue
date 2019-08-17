<template>
    <div id="app">
        <Header></Header>
        <main>
            <router-view/>
        </main>
        <Error-modal v-bind:error="error" v-if="error !== undefined && error !== null"></Error-modal>
        <Footer></Footer>
    </div>
</template>

<script>
    import Footer from "./views/static/Footer";
    import Header from "./views/static/Header";
    import {mapActions, mapGetters} from "vuex";
    import {GET_ERROR} from "./store/getter-type";
    import ErrorModal from "./components/ErrorModal";

    export default {
        components: {ErrorModal, Footer, Header},
        mounted() {
            this.initMaterialize()
            this.getConnectedUser()
        },
        updated() {
            this.initMaterialize()
        },
        methods: {
            ...mapActions([
                'getConnectedUser',
            ]),
            initMaterialize() {
                // eslint-disable-next-line no-undef
                M.AutoInit();
                const elem = document.querySelector('.collapsible.expandable');
                // eslint-disable-next-line no-undef
                M.Collapsible.init(elem, {
                    accordion: false
                });
            }
        },
        computed: {
            ...mapGetters({
                error: GET_ERROR,
            })
        }
    }
</script>

<style lang="scss">

    @import "../../node_modules/materialize-css/sass/components/color-variables";

    $primary-color: color("orange", "lighten-2") !default;
    $secondary-color: color("orange", "lighten-1") !default;
    @import '../../node_modules/materialize-css/sass/materialize';

</style>
