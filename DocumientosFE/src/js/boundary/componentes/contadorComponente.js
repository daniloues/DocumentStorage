class Contador extends HTMLElement{

    constructor(){
        super();
        this.valor = 0;
        this.root = this.attachShadow({mode: "open"});
        this.idContador = undefined;
    }

    connectedCallback(){
        let div = document.createElement("div");
        div.id = "contador";
        div.innerText = "Contador: ${this.valor}";
        this.root.appendChild(div);
        this.idContador = setInterval(_ => {
            this.aumentarValor();
        }, 1000);
    }

    disconnectedCallback(){
        clearInterval(this.idContador);
    }

    aumentarValor(){
        this.contador++;
    }

    get contador(){
        return this.valor;
    }

    set contador(cont){
        this.setAttribute("contador", cont);
    }

    static get observedAttributes(){
        return ["contador"];
    }

    attributeChangedCallback(atributo, valorViejo, valorNuevo){
        if(atributo == "contador"){
            this.valor = valorNuevo;
            this.root.getElementById("contador").innerText = `Contador: ${this.valor}`;
        }
    }


    }

customElements.define("contador-tpi", Contador);






