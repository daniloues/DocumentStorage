import { AtributoControlador } from "../../control/AtributoControlador.js";
import './contenedorInputCE.js';

class DocumentoComponente extends HTMLElement {
    constructor() {
        super();
        this.root = this.attachShadow({ mode: "open" });
        this.idTipoDocumentoA = -1; // Default value
        this.idDocumento = -1;
    }

    static get observedAttributes() {
        return ['id-tipo-documento-a', 'id-documento'];
    }

    attributeChangedCallback(name, oldValue, newValue) {
        if (name === 'id-tipo-documento-a' && oldValue !== newValue) {
            this.idTipoDocumentoA = newValue;
            this.loadAttributes();
        }
        if (name === 'id-documento' && oldValue !== newValue) {
            this.idDocumento = newValue;
            this.loadAttributes();
        }
    }

    connectedCallback() {
        this.loadAttributes();
        document.addEventListener('tipo-doc-changed', this.handleTipoDocChanged.bind(this));
    }

    disconnectedCallback() {
        document.removeEventListener('tipo-doc-changed', this.handleTipoDocChanged.bind(this));
    }

    handleTipoDocChanged(event) {
        const { idTipoDocumento } = event.detail;
        this.setAttribute('id-tipo-documento-a', idTipoDocumento);
        this.idDocumento = -1;
    }

    loadAttributes() {
        if (this.idTipoDocumentoA === -1) return;
        const controlador = new AtributoControlador();
        controlador.idTipoDocumentoA = this.idTipoDocumentoA;
        controlador.findAll().then(attributes => {
            const labels = attributes.map(atributo => atributo.nombrePantalla);

            // Testing webhooks 3
            
                this.root.innerHTML = `
                <contenedor-input labels='${JSON.stringify(labels)}' ${this.idDocumento !== -1 ? `documento='${this.idDocumento}'` : ''}></contenedor-input>
                    <div id="input-container"></div>
                        <div class="button-container">
                        <button id="new-button">Nuevo</button>
                        <button id="save-button">${this.idDocumento !== -1 ? 'Actualizar' : 'Crear'}</button>
                    </div>
                `;
            
            this.root.querySelector('#new-button').addEventListener('click', this.handleNewClick.bind(this));
            this.root.querySelector('#save-button').addEventListener('click', this.handleSaveClick.bind(this));
        }).catch(error => {
            console.error('Error fetching attributes:', error);
            this.root.innerHTML = `
                <div>
                    <p>Error loading attributes. Please try again later.</p>
                </div>
            `;
        });
    }



    handleNewClick() {
        const inputContainer = this.root.querySelector('contenedor-input');
        const inputs = inputContainer.shadowRoot.querySelectorAll('input');
        inputs.forEach(input => {
            input.value = '';
        });
        this.idDocumento = -1;
    }

    handleSaveClick() {
        const data = this.collectInputData();
        const url = this.idDocumento !== -1 ? 'your-update-endpoint' : 'your-create-endpoint';
        const method = this.idDocumento !== -1 ? 'PUT' : 'POST';

        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
    }

    collectInputData() {
        const inputContainer = this.root.querySelector('contenedor-input');
        const inputs = inputContainer.shadowRoot.querySelectorAll('input');
        const data = {};
        inputs.forEach(input => {
            data[input.name] = input.value;
        });
        return data;
    }


}

customElements.define('documento-doc', DocumentoComponente);
