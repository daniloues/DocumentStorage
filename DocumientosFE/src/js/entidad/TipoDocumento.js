export class TipoDocumento {
    constructor(idTipoDocumento, nombre, activo, observaciones) {
        this._idTipoDocumento = idTipoDocumento;
        this._nombre = nombre;
        this._activo = activo;
        this._observaciones = observaciones;
    }

    get idTipoDocumento() {
        return this._idTipoDocumento;
    }

    set idTipoDocumento(value) {
        this._idTipoDocumento = value;
    }

    get nombre() {
        return this._nombre;
    }

    set nombre(value) {
        this._nombre = value;
    }

    get activo() {
        return this._activo;
    }

    set activo(value) {
        this._activo = value;
    }

    get observaciones() {
        return this._observaciones;
    }

    set observaciones(value) {
        this._observaciones = value;
    }

    toJSON() {
        const json = {
            idTipoDocumento: this.idTipoDocumento,
            nombre: this.nombre,
            activo: this.activo,
            observaciones: this.observaciones
        };

        return json;
    }

    static fromJSON(json) {
        return new TipoDocumento(
            json.idTipoDocumento,
            json.nombre,
            json.activo,
            json.observaciones
        );
    }
}
