import { AccesoADatos } from './AccesoADatos.js';
import { Atributo } from '../entidad/Atributo.js';

export class AtributoControlador extends AccesoADatos {
    constructor() {
        super(Atributo); 
        this.tipoDato = Atributo; 
        this._idTipoDocumentoA = -1;
    }

    entityQuery() {
        return 'Atributo';
    }

    get idTipoDocumentoA() {
        return this._idTipoDocumentoA;
    }
    
    set idTipoDocumentoA(value) {
        this._idTipoDocumentoA = value;
    } 

    getApiUrl() {
        
        return `http://localhost:9090/Documientos-QS17002/resources/tipodocumento/${this._idTipoDocumentoA}/atributo`; 
    }    
}
