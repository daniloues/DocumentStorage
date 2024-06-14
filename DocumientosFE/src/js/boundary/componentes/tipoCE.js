import { TipoDocumentoControlador } from "../../control/TipoDocumentoControlador.js";

class TipoCE extends HTMLElement {
    constructor() {
        super();
        this.root = this.attachShadow({ mode: "open" });
    }

    connectedCallback() {
        const tiposDocumento = null;
        const controlador = new TipoDocumentoControlador();
        const tipoDocElement = document.getElementById('documentoMostrar');
        controlador.findAll().then(attributes => {
            console.log(attributes);
            this.root.innerHTML = `
            <div>
                <select id="filterSelect">
                    <option value="all">Seleccione tipo de documento</option>
                    ${tiposDocumento.map(tdoc => `<option value="${tdoc.idTipoDocumento}">${tdoc.nombre}</option>`).join('')}
                </select>
            </div>
            `;
        })
            .catch(error => {
                console.error('Error fetching attributes:', error);
                this.root.innerHTML = `
                <div>
                    <p>Error loading attributes. Please try again later.</p>
                </div>
            `;
            });

        if (tiposDocumento !== null) {
            if (tipoDocElement.style.display === 'none' || tipoDocElement.style.display === '') {
                tipoDocElement.style.display = 'block';
            } else {
                tipoDocElement.style.display = 'none';
            }
        }

    }

}

customElements.define("tipo-doc", TipoCE);
