import { TipoDocumentoControlador } from "../../control/TipoDocumentoControlador.js";

class TipoCE extends HTMLElement {
    constructor() {
        super();
        this.root = this.attachShadow({ mode: "open" });
    }

    connectedCallback() {
        const controlador = new TipoDocumentoControlador();
        controlador.findAll().then(attributes => {
            console.log(attributes);
            this.root.innerHTML = `
                <div>
                    <select id="filterSelect">
                        ${attributes.map(tdoc => `<option value="${tdoc.idTipoDocumento}">${tdoc.nombre}</option>`).join('')}
                    </select>
                </div>
            `;
            const selectElement = this.root.querySelector('#filterSelect');
            selectElement.addEventListener('change', (event) => {
                const selectedValue = event.target.value;
                this.dispatchEvent(new CustomEvent('tipo-doc-changed', {
                    detail: { idTipoDocumento: selectedValue },
                    bubbles: true,
                    composed: true
                }));

                // Show the documento-doc component and set its attribute
                const documentoMostrar = document.getElementById('documentoMostrar');
                const documentoDoc = documentoMostrar.querySelector('documento-doc');
                documentoDoc.setAttribute('id-tipo-documento-a', selectedValue);
                documentoMostrar.style.display = 'block';
            });

            
        }).catch(error => {
            console.error('Error fetching attributes:', error);
            this.root.innerHTML = `
                <div>
                    <p>Error loading attributes. Please try again later.</p>
                </div>
            `;
        });
    }
}

customElements.define("tipo-doc", TipoCE);
