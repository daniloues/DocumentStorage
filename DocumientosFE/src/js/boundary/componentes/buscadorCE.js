import { DocumentoControlador } from "../../control/DocumentoControlador.js";
import { TaxonomiaControlador } from "../../control/TaxonomiaControlador.js";
import { toggleView } from '../../modelo/mostrarVista.js'; // Adjust the path as needed

class DocSearch extends HTMLElement {
    constructor() {
        super();
        this.root = this.attachShadow({ mode: "open" });
        this.searchValue = "";
        this.selectedAtributo = null;
        this.matchTipoDocumento = null;
    }

    connectedCallback() {
        let scriptTextoFiltro = document.createElement("script");
        let scriptFiltroLista = document.createElement("script");
        scriptFiltroLista.type = "module";
        scriptFiltroLista.src = "./js/boundary/componentes/filtroLista.js";
        scriptTextoFiltro.type = "module";
        scriptTextoFiltro.src = "./js/boundary/componentes/textoFiltro.js";

        scriptFiltroLista.addEventListener("load", () => {
            this.root.innerHTML = `
            <link rel="stylesheet" href="./resources/estilos/estilo.css">
            <div class="search-container">
                <div class="search-bar">
                    <filtrar-lista></filtrar-lista>
                    <texto-filtro></texto-filtro>
                    <button id="searchButton">Buscar</button>
                </div>
                <div id="resultsContainer" class="results-container"></div>
            </div>
            `;

            this.root.querySelector('texto-filtro').addEventListener('input-changed', (event) => {
                this.searchValue = event.detail.value;
            });

            this.root.querySelector('filtrar-lista').addEventListener('filter-changed', (event) => {
                this.selectedAtributo = event.detail.idAtributo;
            });

            this.root.querySelector('#searchButton').addEventListener('click', () => {
                this.mostrarResultados();
            });
        });

        this.root.appendChild(scriptTextoFiltro);
        this.root.appendChild(scriptFiltroLista);
    }

    mostrarResultados() {
        const controlador = new DocumentoControlador();
        controlador.getAllDocumentoByAtributoValor(this.selectedAtributo, this.searchValue).then(attributes => {
            const resultsContainer = this.root.querySelector('#resultsContainer');
            resultsContainer.innerHTML = ''; // Clear previous results
            attributes.forEach(entidad => {
                const entidadElement = document.createElement('div');
                entidadElement.className = 'entidadDocumento';
                entidadElement.textContent = entidad.nombre; // Assuming each entity has a 'name' property
                entidadElement.dataset.idDocumento = entidad.idDocumento; // Save the idDocumento in a data attribute
                entidadElement.addEventListener('click', () => {
                    this.llenarAtributosDelDocumento(entidad.idDocumento);
                });
                resultsContainer.appendChild(entidadElement);
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

    llenarAtributosDelDocumento(idDocumentoPasar) {
        const controladorTax = new TaxonomiaControlador();
        controladorTax.getTipoDocumentoByDocumento(idDocumentoPasar).then(documentoAttr => {
            this.matchTipoDocumento = documentoAttr;
            const documentoMostrar = document.getElementById('documentoMostrar');
            const documentoDoc = documentoMostrar.querySelector('documento-doc');

            documentoDoc.setAttribute('id-tipo-documento-a', this.matchTipoDocumento);
            documentoDoc.setAttribute('id-documento', idDocumentoPasar);

            toggleView(true);
            documentoMostrar.style.display = 'block';
        }).catch(error => {
            console.error('Error obteniendo tipo documento con el id de documento');
            this.root.innerHTML = `
                <div>
                    <p>Error al intentar llenar la lista de atributos con el documento.</p>
                </div>
            `;
        });
    }
}

customElements.define('buscar-doc', DocSearch);
