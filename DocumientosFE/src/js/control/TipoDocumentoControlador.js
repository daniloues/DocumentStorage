import { AccesoADatos } from './AccesoADatos.js';
import { TipoDocumento } from '../entidad/TipoDocumento.js';

export class TipoDocumentoControlador extends AccesoADatos {

    constructor() {
        super('TipoDocumento');
        this.tipoDato = TipoDocumento; 
    }

    entityQuery() {
        return 'TipoDocumento';
    }


    getApiUrl() {
        return `http://localhost:9090/Documientos-QS17002/resources/tipodocumento`;
    }
}
