<template>
    <div class="modal" id="errorModal">
        <div class="modal-content">
            <h4>{{title}}</h4>
            <p>{{message}}</p>
        </div>
        <div class="modal-footer">
            <a class="modal-close waves-effect waves-green btn-flat" href="#!">Ok</a>
        </div>
        <button class="btn modal-trigger" data-target="errorModal" id="hiddenModalButton" style="display: none;">Modal
        </button>
    </div>
</template>

<script>
    import {is4XX} from '../repository/http-constants'

    export default {
        name: "ErrorModal",
        props: {
            'error': {
                type: Object,
                required: true
            },
        },
        data: function () {
            const title = is4XX(this.error.errorStatus) ? 'Erreur client' : 'Erreur du serveur'
            return {
                'title': title,
                'message': this.error.errorMessage,
            }
        },
        mounted() {
            setTimeout(() => {
                const elem = document.getElementById('errorModal');
                // eslint-disable-next-line no-undef
                const instance = M.Modal.init(elem, {dismissible: false});
                instance.open();
            }, 0);
        },
    }
</script>

<style scoped>

</style>
