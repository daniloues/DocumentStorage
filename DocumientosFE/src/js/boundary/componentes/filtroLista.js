import { TipoAtributoControlador } from '../../control/TipoAtributoControlador.js'; // Adjust the path as needed

class FiltrarLista extends HTMLElement {
    constructor() {
        super();
        this.root = this.attachShadow({ mode: 'open' });
    }

    connectedCallback() {
        const controlador = new TipoAtributoControlador();

        controlador.findAll()
            .then(attributes => {
                this.root.innerHTML = `
                    <link rel='stylesheet' type='text/css' media='screen' href='./resources/estilos/estilo.css'>
                    <div>
                        <select id="filterSelect">
                            ${attributes.map(attr => `<option value="${attr.idTipoAtributo}">${attr.nombreScreen}</option>`).join('')}
                        </select>
                    </div>
                `;

                const selectElement = this.root.querySelector('#filterSelect');
                selectElement.addEventListener('change', (event) => {
                    const selectedValue = event.target.value;
                    console.log(selectedValue);
                    this.dispatchEvent(new CustomEvent('filter-changed', {
                        detail: { idAtributo: selectedValue },
                        bubbles: true,
                        composed: true
                    }));
                });
            })
            .catch(error => {
                console.error('Error fetching attributes:', error);
                this.root.innerHTML = `
                    <div>
                        <p>Error loading attributes. Please try again later.</p>
                    </div>
                `;
            });
    }
}

customElements.define('filtrar-lista', FiltrarLista);
