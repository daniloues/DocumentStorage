class DocSearch extends HTMLElement {
    constructor() {
        super();
        this.root = this.attachShadow({ mode: "open" });
    }

    connectedCallback() {

        // CUSTOM ELEMENT QUE ALMACENARA EL TEXTO POR EL CUAL FILTRAR
        
        let scriptTextoFiltro = document.createElement("script");
        let scriptFiltroLista = document.createElement("script");
        scriptFiltroLista.type = "module";
        scriptFiltroLista.src = "./js/boundary/componentes/filtroLista.js";    
        scriptTextoFiltro.type = "module";
        scriptTextoFiltro.src = "./js/boundary/componentes/textoFiltro.js"

        scriptFiltroLista.addEventListener("load", () => {
            this.root.innerHTML = `
            <link rel="stylesheet" href="./resources/estilos/estilo.css">
            <div class="search-container">
                <div class="search-bar">
                    <filtrar-lista></filtrar-lista>
                    <texto-filtro></texto-filtro>
                    <button id="searchButton">Search</button>
                </div>
            </div>
        `;
        });

        this.root.appendChild(scriptTextoFiltro);
        this.root.appendChild(scriptFiltroLista);
        
    }
}

customElements.define('buscar-doc', DocSearch);
