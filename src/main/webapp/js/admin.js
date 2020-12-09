// 1 - AJAX

var URLBASE="http://localhost:8080/";

//Lister tous les clients
let listAll = function () {
    let clientRequest = new XMLHttpRequest();
    let url = URLBASE+"api/clients" ;
    clientRequest.open("GET", url, true);
    clientRequest.responseType = "json";
    console.log("url");
    clientRequest.onload = function () {
        let clients = this.response;
        refreshTable(clients);

    };
    clientRequest.send();
};

let listUser = function () {
    let idRole=document.getElementById("role").value;
    let sort=document.getElementById("sort").value;
    if (sort.length==0){
        listAll();
    }else {
        let url = URLBASE+ "api/clients";
        if (idRole.length == 0) {
            url += "/sort?sort=" + sort;
        } else {
            url += "/byrole?role=" + idRole + "&sort=" + sort;
        }
        let clientRequest = new XMLHttpRequest();
        clientRequest.open("GET", url, true);
        clientRequest.responseType = "json";
        console.log("coucou");
        clientRequest.onload = function () {
            let clients = this.response;
            refreshTable(clients);

        };
        clientRequest.send();
    }
};

// Suppression d'un client
let deleteBet = function (client) {
        let deleteRequest = new XMLHttpRequest();
        deleteRequest.open("DELETE", "api/client/" + client.id, true);

        deleteRequest.onload = function () {
            listBet();
        };

        deleteRequest.send();
    }
//

//Creation d'un client
let createClient = function (client) {
    let saveRequest = new XMLHttpRequest();
    saveRequest.open("POST", "api/client/add", true); 

    saveRequest.onload = function () {
        if (this.status === 201) {
            listClient();
            document.getElementById("form").hidden = true;
            document.getElementById("list").hidden = false;
        } else if (this.status === 204) {
            alert("The client creation should return a CREATED response status!");
        } else if (this.status === 409) {
            alert("The client code already exists!");
        }
    };
    saveRequest.setRequestHeader("content-type", "application/json");
    saveRequest.send(JSON.stringify(client));
};


//Update un client
let updateClient = function (client) {
    let saveRequest = new XMLHttpRequest();
    saveRequest.open("PATCH", "api/client/edit", true);

    saveRequest.onload = function () {
        if (this.status === 204) {
            listClient();
            document.getElementById("form").hidden = true;
            document.getElementById("list").hidden = false;
        } else if (this.status === 404) {
            alert("The updated client does not exist!");
        }
    };
    saveRequest.setRequestHeader("content-type", "application/x-www-form-urlencoded");
    saveRequest.send("id=" + client.id + "&nom=" + client.nom + "&prenom=" + client.prenom+ "&email=" + client.email + "&pseudo=" + client.pseudo + "&solde=" + client.solde+ "&role=" + client.role);
};

// 2 - HTML JS Functions

let refreshTable = function (clients) {
    let tableElement = document.querySelector("#clients-list tbody");
    var newTableElement = tableElement.cloneNode(false);
    for (const client of clients) {
        console.log(client);
        newTableElement.appendChild(buildTableLine(client));
    }
    tableElement.parentNode.replaceChild(newTableElement, tableElement);
};

let buildTableLine = function (client) {
    let lineElement = document.createElement("tr");
    lineElement.appendChild(createTableCell(client.id));
    lineElement.appendChild(createTableCell(client.nom));
    lineElement.appendChild(createTableCell(client.prenom));
    lineElement.appendChild(createTableCell(client.email));
    lineElement.appendChild(createTableCell(client.pseudo));
    lineElement.appendChild(createTableCell(client.solde));
    lineElement.appendChild(createCellIdRole(client.idRole));
    return lineElement;
}

let createTableCell = function (text) {
    let cellElement = document.createElement("td");
    cellElement.innerText = text;
    return cellElement;
};


let createCellIdRole = function (text) {
    let cellElement = document.createElement("td");
    if (text==1000){
        cellElement.innerText = "Admin";
    }else{
        cellElement.innerText = "User";
    }
    return cellElement;
};


let showClient = function (client) {
    formMode = client.id ? UPDATE : CREATION;

    document.getElementById("id-input").value = client.id;
    document.getElementById("id-input").disabled = !!client.id;
    document.getElementById("nom-input").value = client.nom;
    document.getElementById("prenon-input").value = client.prenom;
    document.getElementById("pseudo-input").value =client.pseudo;
    document.getElementById("email-input").value = client.email;
    document.getElementById("solde-input").value = client.solde;
    document.getElementById("role-input").value = client.role;

    document.getElementById("list").hidden = true;
    document.getElementById("form").hidden = false;
};


let saveForm = function () {
    let client= {
        id: document.getElementById("id-input").value,
        nom: document.getElementById("nom-input").value,
        prenom: document.getElementById("prenom-input").value,
        email: document.getElementById("email-input").value,
        pseudo: document.getElementById("pseudo-input").value,
        solde: document.getElementById("solde-input").value,
        role: document.getElementById("role-input").value,
    };
    if (formMode === CREATION) {
        createClient(client);
    } else {
        updateBet(client);
    }
    return false;
};


// 3 - Window.Onload

window.onload = function () {
    document.getElementById("form").hidden = true;
    listAll();

    document.getElementById("apply-parameters").onclick = function () {
        listUser();
    }

    /*
    document.getElementById("add-button").onclick = function () {
        showClient({id: "", nom:"", prenom: "", pseudo: "",email:"",solde:"",role:""});
    };

    document.getElementById("go-back").onclick = function () {
        document.getElementById("form").hidden = true;
        document.getElementById("list").hidden = false;
    };

    document.getElementById("save-form").onclick = saveForm;
	*/
};

