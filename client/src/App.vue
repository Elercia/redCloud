<template>
  <div id="app">
    <nav class="orange accent-3">
      <div class="nav-wrapper">
        <a href="#!" class="brand-logo"><i class="material-icons">cloud</i>RedCloud</a>
        <ul class="right hide-on-med-and-down">
          <li><router-link to="/">Accueil</router-link></li>
          <li><router-link to="/about">A propos</router-link></li>
        </ul>
      </div>
    </nav>

    <router-view/>
  </div>
</template>

<style lang="scss">
</style>
