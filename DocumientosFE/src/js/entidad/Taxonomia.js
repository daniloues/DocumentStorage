export class Taxonomia {
    constructor(idTaxonomia, idDocumento, idTipoDocumento, fechaCreacion) {
        this._idTaxonomia = idTaxonomia;
        this._idDocumento = idDocumento;
        this._idTipoDocumento = idTipoDocumento;
        this._fechaCreacion = fechaCreacion;
    }

    get idTaxonomia() {
        return this._idTaxonomia;
    }

    set idTaxonomia(value) {
        this._idTaxonomia = value;
    }

    get idDocumento() {
        return this._idDocumento;
    }

    set idDocumento(value) {
        this._idDocumento = value;
    }

    get idTipoDocumento() {
        return this._idTipoDocumento;
    }

    set idTipoDocumento(value) {
        this._idTipoDocumento = value;
    }

    get fechaCreacion() {
        return this._fechaCreacion;
    }

    set fechaCreacion(value) {
        this._fechaCreacion = value;
    }

    toJSON() {
        const json = {
            idTaxonomia: this.idTaxonomia,
            idDocumento: this.idDocumento,
            idTipoDocumento: this.idTipoDocumento,
            fechaCreacion: this.fechaCreacion
        };

        return json;
    }

    static fromJSON(json) {
        return new Taxonomia(
            json.idTaxonomia,
            json.idDocumento,
            json.idTipoDocumento,
            json.fechaCreacion
        );
    }
}
