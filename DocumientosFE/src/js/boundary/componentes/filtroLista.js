import { TipoAtributoControlador } from '../../control/TipoAtributoControlador.js'; // Adjust the path as needed

class FiltrarLista extends HTMLElement {
    constructor() {
        super();
        this.root = this.attachShadow({ mode: 'open' });
    }

    connectedCallback() {
        console.log("Me conecté al filtrar-lista componente ahorita");
        const controlador = new TipoAtributoControlador();

        controlador.findAll()
            .then(attributes => {
                console.log(attributes);
                this.root.innerHTML = `
                    <link rel='stylesheet' type='text/css' media='screen' href='./resources/estilos/estilo.css'>
                    <div>
                        <select id="filterSelect">
                            <option value="Selecciona atributo">All</option>
                            ${attributes.map(attr => `<option value="${attr.idTipoAtributo}">${attr.nombreScreen}</option>`).join('')}
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
    }

}

customElements.define('filtrar-lista', FiltrarLista);