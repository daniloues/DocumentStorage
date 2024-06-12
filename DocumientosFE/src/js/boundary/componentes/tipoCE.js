import { TipoDocumentoControlador } from "../../control/TipoDocumentoControlador.js";

class TipoCE extends HTMLElement {
    constructor() {
        super();
        this.root = this.attachShadow({ mode: "open" });
    }

    async connectedCallback() {

        const tipoDocElement = document.getElementById('documentoMostrar');
        const tiposDocumento = await this.fetchTiposDocumento();
        this.root.innerHTML = `
            <div>
                <select id="filterSelect">
                    <option value="all">Seleccione tipo de documento</option>
                    ${tiposDocumento.map(tdoc => `<option value="${tdoc.idTipoDocumento}">${tdoc.nombre}</option>`).join('')}
                </select>
            </div>
        `;
        if (tiposDocumento !== null) {
            if (tipoDocElement.style.display === 'none' || tipoDocElement.style.display === '') {
                tipoDocElement.style.display = 'block';
            } else {
                tipoDocElement.style.display = 'none';
            }
        }

    }

    async fetchTiposDocumento() {
        const controlador = new TipoDocumentoControlador();
        try {
            return await controlador.findAll();
        } catch (error) {
            console.error('Error fetching attributes:', error);
            return [];
        }
    }

}

customElements.define("tipo-doc", TipoCE);
