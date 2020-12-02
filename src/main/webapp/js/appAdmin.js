let listClient = function () {
    let listClientRequest = new XMLHttpRequest();
    listClientRequest.open("GET", "/admin", true);
    listClientRequest.responseType = "json";

    listClientRequest.onload = function () {
        let clientsList = this.response;
        createClientsList(clientsList);
    };

    listClientRequest.send();
};

let deleteClient = function (clientId) {
    let deleteClientRequest = new XMLHttpRequest();
        deleteClientRequest.open("DELETE", "/"+ Client.getId() , true);

        deleteVoyageRequest.onload = function () {
           listClient();
        };

        deleteClientRequest.send();
}

