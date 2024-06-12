export class TipoAtributo {
    constructor(idTipoAtributo, nombre, observaciones, expresionRegular, nombreScreen, indicacionesScreen) {
        this._idTipoAtributo = idTipoAtributo;
        this._nombre = nombre;
        this._observaciones = observaciones;
        this._expresionRegular = expresionRegular;
        this._nombreScreen = nombreScreen;
        this._indicacionesScreen = indicacionesScreen;
    }

    get idTipoAtributo() {
        return this._idTipoAtributo;
    }

    set idTipoAtributo(value) {
        this._idTipoAtributo = value;
    }

    get nombre() {
        return this._nombre;
    }

    set nombre(value) {
        this._nombre = value;
    }

    get observaciones() {
        return this._observaciones;
    }

    set observaciones(value) {
        this._observaciones = value;
    }

    get expresionRegular() {
        return this._expresionRegular;
    }

    set expresionRegular(value) {
        this._expresionRegular = value;
    }

    get nombreScreen() {
        return this._nombreScreen;
    }

    set nombreScreen(value) {
        this._nombreScreen = value;
    }

    get indicacionesScreen() {
        return this._indicacionesScreen;
    }

    set indicacionesScreen(value) {
        this._indicacionesScreen = value;
    }

    toJSON() {
        const json = {
            idTipoAtributo: this.idTipoAtributo,
            nombre: this.nombre,
            observaciones: this.observaciones,
            expresionRegular: this.expresionRegular,
            nombreScreen: this.nombreScreen,
            indicacionesScreen: this.indicacionesScreen
        };

        return json;
    }

    static fromJSON(json) {
        return new TipoAtributo(
            json.idTipoAtributo,
            json.nombre,
            json.observaciones,
            json.expresionRegular,
            json.nombreScreen,
            json.indicacionesScreen
        );
    }
}
