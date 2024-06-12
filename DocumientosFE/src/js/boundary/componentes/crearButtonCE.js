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

        this.root.getElementById('crearButton').addEventListener('click', () => {
            const articleContent = document.getElementById('articleContent');
            const body = document.body;
            if (articleContent.style.display === 'none' || articleContent.style.display === '') {
                articleContent.style.display = 'block';
                body.classList.remove('full-header');
            } else {
                articleContent.style.display = 'none';
                body.classList.add('full-header');
            }
        });
    }
}

customElements.define('crear-button', CrearButton);
