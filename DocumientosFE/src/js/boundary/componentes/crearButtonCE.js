import { toggleView } from '../../modelo/mostrarVista.js'; // Adjust the path as needed

class CrearButton extends HTMLElement {
    constructor() {
        super();
        this.root = this.attachShadow({ mode: 'open' });
    }

    connectedCallback() {
        this.root.innerHTML = `
        <link rel="stylesheet" href="./resources/estilos/estilo.css">
            <button id="crearButton">Crear</button>
        `;

        const crearButton = this.root.getElementById('crearButton');
        crearButton.addEventListener('click', () => {
            const articleContent = document.getElementById('articleContent');
            toggleView(articleContent.style.display === 'none' || articleContent.style.display === '');
        });

        // Initially hide the article if needed
        const articleContent = document.getElementById('articleContent');
        if (articleContent.style.display === 'none' || articleContent.style.display === '') {
            crearButton.textContent = 'Crear documento';
        } else {
            crearButton.textContent = 'Buscar documento';
        }
    }
}

customElements.define('crear-button', CrearButton);
