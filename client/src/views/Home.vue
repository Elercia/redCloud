<template>
    <div class="">
        <div class="row" style="margin-bottom: 0;">
            <div class="col s2 hide-on-small-only" style="height: 100vh">
                <div class="section">
                    <a class="btn-floating btn-large waves-effect waves-light red dropdown-trigger" href='#'
                       data-target='dropdown-add'><i class="material-icons">add</i></a>

                    <ul id='dropdown-add' class='dropdown-content'>
                        <li><a href="#!"><i class="material-icons">cloud_upload</i> Importer un fichier</a></li>
                        <li><a href="#!"><i class="material-icons">cloud_upload</i> Creer un dossier</a></li>
                    </ul>
                </div>

                <div class="divider"></div>

                <ul class="section collection" style="border:0;">
                    <li class="collection-item">
                        <div>Alvin<a href="#!" class="secondary-content"><i class="material-icons">send</i></a></div>
                    </li>
                    <li class="collection-item">
                        <div>Alvin<a href="#!" class="secondary-content"><i class="material-icons">send</i></a></div>
                    </li>
                    <li class="collection-item">
                        <div>Alvin<a href="#!" class="secondary-content"><i class="material-icons">send</i></a></div>
                    </li>
                    <li class="collection-item">
                        <div>Alvin<a href="#!" class="secondary-content"><i class="material-icons">send</i></a></div>
                    </li>
                </ul>
            </div>

            <div class="col s12 m10">
                <div class="row">
                    <nav>
                        <div class="nav-wrapper">
                            <div class="col s12">
                                <a href="#!" class="breadcrumb">First</a>
                                <a href="#!" class="breadcrumb">Second</a>
                                <a href="#!" class="breadcrumb">Third</a>
                            </div>
                        </div>
                    </nav>
                    <ul class=" collection">
                        <li class="collection-item avatar" v-for="index in 10" :key="index">
                            <i class="material-icons circle">folder</i>
                            <span class="title">Title</span>
                            <p>First Line <br>
                                Second Line
                            </p>
                            <a href="#!" class="secondary-content"><i class="material-icons">{{index % 2 ? "star_border"
                                : "grade"}}</i></a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="fixed-action-btn hide-on-large-only">
            <a class="btn-floating btn-large red">
                <i class="large material-icons">mode_edit</i>
            </a>
            <ul>
                <li>
                    <a href="#" class="btn-floating mobile-fab-tip red">Importer un fichier</a> <!--tooltip-->
                    <a class="btn-floating red"><i class="material-icons">cloud_upload</i></a>
                </li>

                <li>
                    <a href="#" class="btn-floating mobile-fab-tip yellow darken-1 ">Ajouter un dossiers</a> <!--tooltip-->
                    <a class="btn-floating yellow darken-1 waves-light"><i class="material-icons">cloud_upload</i></a>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'home',
        components: {},
    }
</script>

<style>
    .mobile-fab-tip {
        position: fixed;
        right: 85px;
        padding: 0px 0.5rem;
        text-align: right;
        border-radius: 2px;
        color: #FFF;
        width: auto;
    }
</style>
