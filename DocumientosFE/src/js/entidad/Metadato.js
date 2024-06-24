export class Metadato {
    constructor(idMetadato, idDocumento, idAtributo, fechaCreacion) {
        this._idMetadato = idMetadato;
        this._idDocumento = idDocumento;
        this._idAtributo = idAtributo;
        this._valor = valor;
        this._fechaCreacion = fechaCreacion;
        his._comentarios = comentarios;
    }

    get idMetadato() {
        return this._idMetadato;
    }

    set idMetadato(value) {
        this._idMetadato = value;
    }

    get idDocumento() {
        return this._idDocumento;
    }

    set idDocumento(value) {
        this._idDocumento = value;
    }

    get idAtributo() {
        return this._idAtributo;
    }

    set idAtributo(value) {
        this._idAtributo = value;
    }

    get valor() {
        return this._valor;
    }

    set valor(value) {
        this._valor = value;
    }

    get fechaCreacion() {
        return this._fechaCreacion;
    }

    set fechaCreacion(value) {
        this._fechaCreacion = value;
    }

    get comentarios() {
        return this._comentarios;
    }

    set comentarios(value) {
        this._comentarios = value;
    }


    toJSON() {
        const json = {
            idMetadato: this.idMetadato,
            idDocumento: this.idDocumento,
            idAtributo: this.idAtributo,
            valor: this.valor,
            fechaCreacion: this.fechaCreacion,
            comentarios: this.comentarios
        };

        return json;
    }

    static fromJSON(json) {
        return new Metadato(
            json.idMetadato,
            json.idDocumento,
            json.idAtributo,
            json.valor,
            json.fechaCreacion,
            json.comentarios
        );
    }
}
