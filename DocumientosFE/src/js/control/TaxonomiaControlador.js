import { AccesoADatos } from './AccesoADatos.js';
import {Taxonomia} from '../entidad/Taxonomia.js';

export class TaxonomiaControlador extends AccesoADatos {
    constructor() {
        super(Taxonomia); 
        this.tipoDato = Taxonomia; 
    }

    entityQuery() {
        return 'Taxonomia';
    }

    getApiUrl() {   
        return `http://localhost:9090/Documientos-1.0-SNAPSHOT/resources/taxonomia/documento/`; 
    }
    
    getTipoDocumentoByDocumento(idDocumento){

        return new Promise((resolve, reject) => {
            fetch(this.getApiUrl() + idDocumento)
                .then(response => {
                    if (!response.ok) {
                        reject(new Error('Error al extraer datos'));
                    } else {
                        response.json()
                            .then(jsonData => {
                                resolve(jsonData);
                            })
                            .catch(error => reject(error));
                    }
                })
                .catch(error => reject(error));
        });
    }

}
