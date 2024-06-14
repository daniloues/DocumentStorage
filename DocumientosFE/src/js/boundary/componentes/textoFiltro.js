class TextoFiltro extends HTMLElement{

    constructor(){
        super();
        this.root = this.attachShadow({mode: 'open'});
    }

    connectedCallback(){
        console.log("Me conecté al texto-filtro componente ahorita");
        this.root.innerHTML = `
        <link rel='stylesheet' type='text/css' media='screen' href='./resources/estilos/estilo.css'>
        <div>
            <input type="text" id="searchInput" placeholder="Ingrese texto...">
        </div>
        `}

}

customElements.define('texto-filtro', TextoFiltro);