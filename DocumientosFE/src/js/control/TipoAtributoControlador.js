import { AccesoADatos } from './AccesoADatos.js';
import { TipoAtributo } from '../entidad/TipoAtributo.js';
export class TipoAtributoControlador extends AccesoADatos {

    constructor() {
        super('TipoAtributo');
        this.tipoDato = TipoAtributo; 
    }

    entityQuery() {
        return 'TipoAtributo';
    }

    getApiUrl() {
        return `http://localhost:9090/Documientos-1.0-SNAPSHOT/resources/tipoatributo`;
    }
}
