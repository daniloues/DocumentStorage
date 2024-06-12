import { AtributoControlador } from "../../control/AtributoControlador.js";

class DocumentoComponente extends HTMLElement {
    constructor() {
        super();
        this.root = this.attachShadow({ mode: "open" });
    }

    async connectedCallback() {

        // SE SETTEA QUE TIPODOCUMENTO SE TRAERA EL ATRIBUTO
        const idTipoDocumentoA = this.getAttribute('id-tipo-documento-a') || -1; // Default to -1 if not provided
        // SE MANDA A TRAER LOS ATRIBUTOS
        const atributosTD = await this.fetchAtributosTD(idTipoDocumentoA);
        // SE SEPARAN LAS ETIQUETAS CON LAS QUE SE CREARAN LOS CAMPOS DE INGRESO DE DATOS
        const labels = atributosTD.map(atributo => atributo.nombrePantalla);
        // LOGICA PARA MOSTRAR LOS CAMPOS DE INGRESO DE DATOS
        let scriptCI = document.createElement("script");
        scriptCI.id = "scriptContenedorInput";
        scriptCI.src = "./js/boundary/componentes/contenedorInputCE.js";
        scriptCI.addEventListener("load", () => {
            this.root.innerHTML = `
            <contenedor-input labels='${JSON.stringify(labels)}'></contenedor-input>
            `;
        });
        this.root.appendChild(scriptCI);
    }

    async fetchAtributosTD(idAtributoTD) {
        const controlador = new AtributoControlador();
        controlador.idTipoDocumentoA = idAtributoTD; // Ensure this sets the value correctly
        try {
            return await controlador.findAll();
        } catch (error) {
            console.error('Error fetching attributes:', error);
            return [];
        }
    }
}

customElements.define('documento-doc', DocumentoComponente);
