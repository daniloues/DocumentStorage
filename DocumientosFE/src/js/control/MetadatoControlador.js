import { AccesoADatos } from './AccesoADatos.js';
import {Metadato} from '../entidad/Metadato.js';

export class MetadatoControlador extends AccesoADatos {
    constructor() {
        super(Metadato); 
        this.tipoDato = Metadato; 
    }

    entityQuery() {
        return 'Metadato';
    }

    getApiUrl() {   
        return `http://localhost:9090/Documientos-1.0-SNAPSHOT/resources/documento/`; 
    }
    
    getMetadatoByDocumento(idDocumento){

        return new Promise((resolve, reject) => {
            fetch(this.getApiUrl() + idDocumento + '/metadato')
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
