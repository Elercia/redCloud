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
        // eslint-disable-next-line no-undef
        M.AutoInit();
    }
  }
</script>

<style lang="scss">
</style>
