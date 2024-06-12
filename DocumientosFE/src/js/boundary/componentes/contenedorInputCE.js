class ContenedorInputComponente extends HTMLElement {
    constructor() {
        super();
        this.attachShadow({ mode: 'open' });
    }

    connectedCallback() {
        this.render();
    }

    render() {
        this.shadowRoot.innerHTML = '';

        const container = document.createElement('div');

        const labels = this.getAttribute('labels') ? JSON.parse(this.getAttribute('labels')) : [];

        labels.forEach(label => {
            const wrapper = document.createElement('div');
            const inputLabel = document.createElement('label');
            inputLabel.textContent = label;

            const inputBox = document.createElement('input');
            inputBox.type = 'text';
            inputBox.name = label.toLowerCase().replace(/\s+/g, '-');

            wrapper.appendChild(inputLabel);
            wrapper.appendChild(inputBox);
            container.appendChild(wrapper);
        });

        this.shadowRoot.appendChild(container);
    }

    static get observedAttributes() {
        return ['labels'];
    }

    attributeChangedCallback(name, oldValue, newValue) {
        if (name === 'labels' && oldValue !== newValue) {
            this.render();
        }
    }
}

customElements.define('contenedor-input', ContenedorInputComponente);