import { DocumentoControlador } from "../../control/DocumentoControlador.js";

class TextoFiltro extends HTMLElement{

    constructor(){
        super();
        this.root = this.attachShadow({mode: 'open'});
    }

    connectedCallback(){
        this.root.innerHTML = `
        <link rel='stylesheet' type='text/css' media='screen' href='./resources/estilos/estilo.css'>
        <div>
            <input type="text" id="searchInput" placeholder="Ingrese texto...">
        </div>
        `
        const inputElement = this.shadowRoot.querySelector('#searchInput');
        inputElement.addEventListener('input', (event) => {
            const inputValue = event.target.value;
            this.dispatchEvent(new CustomEvent('input-changed', {
                detail: { value: inputValue },
                bubbles: true, // Allow the event to bubble up the DOM
                composed: true // Allow the event to cross the shadow DOM boundary
            }));
        });
    }
        
}

customElements.define('texto-filtro', TextoFiltro);