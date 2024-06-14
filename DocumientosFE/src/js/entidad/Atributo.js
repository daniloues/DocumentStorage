export class Atributo {
    constructor(idTipoAtributo, nombre, idTipoDocumento, idAtributo, nombrePantalla, indicacionesPantalla, obligatorio) {
        this._idAtributo = idAtributo;
        this._idTipoAtributo = idTipoAtributo;
        this._nombre = nombre;                 
        this._idTipoDocumento = idTipoDocumento;
        this._nombrePantalla = nombrePantalla;
        this._obligatorio = obligatorio;
        this._indicacionesPantalla = indicacionesPantalla;
        
    }

    get idAtributo() {
        return this._idAtributo;
    }
    
    set idAtributo(value) {
        this._idAtributo = value;
    }    

    get idTipoAtributo() {
        return this._idTipoAtributo;
    }

    set idTipoAtributo(value) {
        this._idTipoAtributo = value;
    }

    get nombre() {
        return this._nombre;
    }

    set nombre(value) {
        this._nombre = value;
    }

    get idTipoDocumento() {
        return this._idTipoDocumento;
    }

    set idTipoDocumento(value) {
        this._idTipoDocumento = value;
    }

    get indicacionesPantalla() {
        return this._indicacionesPantalla;
    }

    set indicacionesPantalla(value) {
        this._indicacionesPantalla = value;
    }

    get nombrePantalla() {
        return this._nombrePantalla;
    }

    set nombrePantalla(value) {
        this._nombrePantalla = value;
    }

    get obligatorio() {
        return this._obligatorio;
    }

    set obligatorio(value) {
        this._obligatorio = value;
    }

    toJSON() {
        const json = {
            idTipoAtributo: this.idTipoAtributo,
            idAtributo: this.idAtributo,
            nombre: this.nombre,
            idTipoDocumento: this.idTipoDocumento,
            obligatorio: this.obligatorio,
            nombrePantalla: this.nombrePantalla,
            indicacionesPantalla: this.indicacionesPantalla
        };        
            
        return json;
    }

    static fromJSON(json) {
        return new Atributo(
            json.idTipoAtributo,
            json.nombre,
            json.idTipoDocumento,
            json.idAtributo,
            json.nombrePantalla,
            json.indicacionesPantalla,
            json.obligatorio
        );
    }
    
}
