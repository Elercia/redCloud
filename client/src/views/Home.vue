<template>
    <div class="">
        <div class="row">
            <div class="col s2 orange accent-2 hide-on-small-only">
                <h4>Dossier(s)</h4>
                <ul class="collapsible expandable">
                    <li v-for="index in 10" :key="index">
                        <div class="collapsible-header"><i class="material-icons">insert_drive_file</i>First</div>
                        <div class="collapsible-body">
                            <ul class="collapsible expandable">
                                <li v-for="index in 10" :key="index">
                                    <div class="collapsible-header"><i class="material-icons">insert_drive_file</i>First
                                    </div>
                                    <div class="collapsible-body"><span>Lorem ipsum dolor sit amet.</span></div>
                                </li>
                            </ul>
                        </div>
                    </li>
                </ul>
            </div>

            <div class="col s12 m10">
                <!-- page content -->
                <div class="row">
                    <nav class="col s12 orange accent-2">
                        <div class="nav-wrapper">
                            <div>
                                <a href="#!" class="breadcrumb">First</a>
                                <a href="#!" class="breadcrumb">Second</a>
                                <a href="#!" class="breadcrumb">Third</a>
                            </div>
                        </div>
                    </nav>

                    <ul class="collection">
                        <li v-for="index in 1" :key="index" class="collection-item avatar">
                            <i class="material-icons circle">folder</i>
                            <span class="title">Title</span>
                            <p>First Line <br>
                                Second Line
                            </p>
                            <a href="#!" class="secondary-content"><i
                                    class="material-icons circle">cloud_download</i></a>
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
                <li><a class="btn-floating red"><i class="material-icons">cloud_upload</i></a></li>
                <li><a class="btn-floating yellow darken-1"><i class="material-icons">cloud_upload</i></a></li>
                <li><a class="btn-floating green"><i class="material-icons">cloud_upload</i></a></li>
                <li><a class="btn-floating blue"><i class="material-icons">cloud_upload</i></a></li>
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
