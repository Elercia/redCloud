<template>
    <header>
        <nav>
            <div class="nav-wrapper">
                <router-link to="/" class="brand-logo right"><i class="material-icons">cloud</i>RedCloud</router-link>
                <a href="#" data-target="mobile-sidenav" class="sidenav-trigger"><i class="material-icons">menu</i></a>
                <ul class="hide-on-med-and-down">
                    <li>
                        <router-link to="/">Accueil</router-link>
                    </li>
                    <li>
                        <router-link to="/about">A propos</router-link>
                    </li>
                    <li>
                        <router-link to="/my-account">Mon Compte</router-link>
                    </li>
                </ul>
            </div>
        </nav>
        <ul class="sidenav" id="mobile-sidenav">
            <li>
                <router-link to="/">Accueil</router-link>
            </li>
            <li>
                <router-link to="/about">A propos</router-link>
            </li>
            <li>
                <router-link to="/my-account">Mon Compte</router-link>
            </li>
        </ul>
    </header>
</template>

<script>
    export default {
        name: "Header",
        mounted() {
            document.addEventListener('DOMContentLoaded', function() {
                const elems = document.querySelectorAll('.sidenav');
                // eslint-disable-next-line no-undef
                M.Sidenav.init(elems, {});
            });
        }
    }
</script>

<style scoped>

</style>
