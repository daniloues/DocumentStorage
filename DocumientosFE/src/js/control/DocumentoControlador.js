import { AccesoADatos } from './AccesoADatos.js';
import { Documento } from '../entidad/Documento.js';

export class DocumentoControlador extends AccesoADatos {

    constructor() {
        super('Documento');
        this.tipoDato = Documento;

    }

    entityQuery() {
        return 'Documento';
    }

    getApiUrl() {
        return `http://localhost:9090/Documientos-1.0-SNAPSHOT/resources/documento`;
    }

    getAllDocumentoByAtributoValor(idAtributoB, valorBusqueda){

        return new Promise((resolve, reject) => {
            fetch(this.getApiUrl() + "/atributo/" + idAtributoB + "/" + valorBusqueda)
                .then(response => {
                    if (!response.ok) {
                        reject(new Error('Error al extraer datos'));
                    } else {
                        response.json()
                            .then(jsonData => {
                                resolve(jsonData.map(data => this.tipoDato.fromJSON(data)));
                            })
                            .catch(error => reject(error));
                    }
                })
                .catch(error => reject(error));
        });
    }
}
