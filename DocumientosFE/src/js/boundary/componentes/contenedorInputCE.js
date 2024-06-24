import { MetadatoControlador } from "../../control/MetadatoControlador.js";
if (!customElements.get('contenedor-input')) {
    class ContenedorInputComponente extends HTMLElement {
        constructor() {
            super();
            this.attachShadow({ mode: 'open' });
        }

        connectedCallback() {
        }

        // CUANDO SOLO QUIEREN LOS ATRIBUTOS VACIOS DEL TIPO DOCUMENTO
        renderVacio() {
            this.shadowRoot.innerHTML = '';

            const container = document.createElement('div');

            const labels = this.getAttribute('labels') ? JSON.parse(this.getAttribute('labels')) : [];

            labels.forEach(label => {
                const wrapper = document.createElement('div');
                const inputLabel = document.createElement('label');
                inputLabel.textContent = label;

                const inputBox = document.createElement('input');
                inputBox.type = 'text';
                inputBox.name = label.toLowerCase().replace(/\s+/g, '-');
                inputBox.textContent = "";
                wrapper.appendChild(inputLabel);
                wrapper.appendChild(inputBox);
                container.appendChild(wrapper);
            });

            this.shadowRoot.appendChild(container);
        }

        // CUANDO SE TIENE UN DOCUMENTO PARA RELLENAR LOS VALORES
        renderCompleto() {
            this.shadowRoot.innerHTML = '';

            const container = document.createElement('div');

            const labels = this.getAttribute('labels') ? JSON.parse(this.getAttribute('labels')) : [];
            const documento = this.getAttribute('documento');
            const controladorMet = new MetadatoControlador();
            // TRAE LOS METADATO RELACIONADOS AL DOCUMENTO
            controladorMet.getMetadatoByDocumento(documento).then(attributes => {
                // SE RECORRE LA LISTA DE ENTIDADES
                console.log(attributes);
                attributes.forEach(entity => {
                    console.log(entity);
                    // SE RECORRE LOS ATRIBUTOS DISPONIBLES EN EL TIPO DOCUMENTO ACTUAL
                    labels.forEach(label => {

                        if (label === entity.idAtributo.nombre) {

                            const wrapper = document.createElement('div');
                            const inputLabel = document.createElement('label');
                            inputLabel.textContent = label;

                            const inputBox = document.createElement('input');
                            inputBox.type = 'text';
                            inputBox.name = label.toLowerCase().replace(/\s+/g, '-');
                            inputBox.value = entity.valor;
                            wrapper.appendChild(inputLabel);
                            wrapper.appendChild(inputBox);
                            container.appendChild(wrapper);


                        }
                    });
                    this.shadowRoot.appendChild(container);
                });
            });

        }
        static get observedAttributes() {
            return ['labels',
                'documento'];
        }

        attributeChangedCallback(name, oldValue, newValue) {
            if (name === 'labels' && oldValue !== newValue) {
                this.renderVacio();
            }
            if (name === 'documento' && oldValue !== newValue) {
                this.renderCompleto();
            }
        }
    }

    customElements.define('contenedor-input', ContenedorInputComponente);
}