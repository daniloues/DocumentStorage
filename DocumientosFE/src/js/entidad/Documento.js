export class Documento {
    constructor(idDocumento, nombre, referenciaExterna, ubicacionFisica, url, fechaCreacion, creadoPor, comentarios) {
        this._idDocumento = idDocumento;
        this._nombre = nombre;
        this._referenciaExterna = referenciaExterna;                 
        this._ubicacionFisica = ubicacionFisica;
        this._url = url;
        this._fechaCreacion = fechaCreacion;
        this._creadoPor = creadoPor;
        this._comentarios = comentarios;
    }

    get idDocumento() {
        return this._idDocumento;
    }
    
    set idDocumento(value) {
        this._idDocumento = value;
    }    

    get nombre() {
        return this._nombre;
    }

    set nombre(value) {
        this._nombre = value;
    }

    get referenciaExterna() {
        return this._referenciaExterna;
    }

    set referenciaExterna(value) {
        this._referenciaExterna = value;
    }

    get ubicacionFisica() {
        return this._ubicacionFisica;;
    }

    set ubicacionFisica(value) {
        this._ubicacionFisica = value;
    }

    get url() {
        return this._url;
    }

    set url(value) {
        this._url = value;
    }

    get fechaCreacion() {
        return this._fechaCreacion;
    }

    set fechaCreacion(value) {
        this._fechaCreacion = value;
    }

    get creadoPor() {
        return this._creadoPor;
    }

    set creadoPor(value) {
        this._creadoPor = value;
    }

    get comentarios() {
        return this._comentarios;
    }

    set comentarios(value) {
        this._comentarios = value;
    }

    toJSON() {
        const json = {
            idDocumento: this.idDocumento,
            nombre: this.nombre,
            referenciaExterna: this.referenciaExterna,
            ubicacionFisica: this.ubicacionFisica,
            url: this.url,
            fechaCreacion: this.fechaCreacion,
            creadoPor: this.creadoPor,
            comentarios: this.comentarios
        };        
            
        return json;
    }

    static fromJSON(json) {
        return new Documento(
            json.idDocumento,
            json.nombre,
            json.referenciaExterna,
            json.ubicacionFisica,
            json.url,
            json.fechaCreacion,
            json.creadoPor,
            json.comentarios
        );
    }
}
