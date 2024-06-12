
export class AccesoADatos {

    constructor(tipoDato) {
        this.tipoDato = tipoDato;
    }

    // PARA HACER OVERRIDE
    entityQuery() {
        return '';
    }

    getApiUrl() {
        throw new Error('No se ha podido encontrar el API URL');
    }

    findById(id) {
        if (!id) {
            throw new Error('IllegalArgumentException');
        }

        // TODO: LOGICA DE HACER GET REQUEST CON ARGUMENTO ID
    }

    findAll() {
        return new Promise((resolve, reject) => {
            fetch(this.getApiUrl())
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

    create(registro) {

        return new Promise((resolve, reject) => {
            fetch(this.getApiUrl())
                .then(response => {
                    if (!response.ok) {
                        reject(new Error('Error en crear registro'));
                    } else {
                        resolve(new String('Registro creado exitosamente'));
                    }
                })
                .catch(error => reject(error));
        });

    }

    delete(registro) {

        if (!registro) {

            //TODO: LOGICA PARA ELIMINAR ENTIDAD

        }

    }

    modify(registro) {

        if (!registro) {

            //TODO: LOGICA PARA MODIFICAR ENTIDAD

        }

    }
} 
