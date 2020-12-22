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

let listClient = function () {
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
let deleteClient = function (client) {
        let deleteRequest = new XMLHttpRequest();
        let url=URLBASE+"api/clients/"+client.id;
        deleteRequest.open("DELETE", url, true);

        deleteRequest.onload = function () {
            if (this.status === 200) {
                listClient();
            } else if (this.status === 404) {
                alert("The bet code does not exist!");
            }else if (this.status === 400){
                alert("Your request is incorrect");
            }else{
                "There an unknown error, NOT modified"
            }
        };

        deleteRequest.send();
    }
//

//Creation d'un client
let createClient = function (client) {
    let saveRequest = new XMLHttpRequest();
    let url=URLBASE+"api/clients/add";
    saveRequest.open("POST", url, true);

    saveRequest.onload = function () {
        if (this.status === 201) {
            listClient();
            document.getElementById("form").hidden = true;
            document.getElementById("list").hidden = false;
        } else if (this.status === 409) {
            alert("The bet code already exists!");
        }else if (this.status === 400){
            alert("Your request is incorrect");
        }else{
            "There an unknown error, NOT modified"
        }
    };
    saveRequest.setRequestHeader("content-type", "application/json");
    saveRequest.send(JSON.stringify(client));
};


//Update un client
let updateClient = function (client) {
    let saveRequest = new XMLHttpRequest();
    let url=URLBASE+"api/clients/edit";
    saveRequest.open("PATCH", url, true);

    saveRequest.onload = function () {
        if (this.status === 200) {
            listClient();
            document.getElementById("form").hidden = true;
            document.getElementById("list").hidden = false;
        } else if (this.status === 404) {
            alert("The Bet to edit doesn't exist");
        } else if (this.status === 400){
            alert("Your request is incorrect");
        } else{
            "There an unknown error, NOT modified"
        }
    };
    saveRequest.setRequestHeader("content-type", "application/json");
    saveRequest.send(JSON.stringify(client));};

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

    let actionCell = document.createElement("td");
    let buttonGroupElement = document.createElement("div");
    buttonGroupElement.classList.add("btn-group");
    //creation bouton modifier (ou show) dans chaque ligne
    let showButton = document.createElement("button");  // création element bouton
    showButton.classList.add("btn", "btn-primary", "btn-sm"); // ajout d'une classe pour le Bootstraps
    showButton.innerHTML = "<i class=\"fas fa-eye\"></i>"; // FontAwsome d'un oeil
    showButton.title = "Modify";//titre du bouton
    showButton.onclick = function ()
    {
        showClient1(client); // on appel la fonction showClient pour modifier la ligne
    };
    buttonGroupElement.appendChild(showButton);
    // Appendchild du bouton dans Tr


    //creation bouton delete dans chaque ligne
    let deleteButton = document.createElement("button");
    deleteButton.classList.add("btn", "btn-danger", "btn-sm");
    deleteButton.innerHTML = "<i class=\"fas fa-trash-alt\"></i>";
    deleteButton.title = "Delete";
    deleteButton.onclick = function ()
    {
        deleteClient(client);
    };
    lineElement.appendChild(deleteButton);
    actionCell.appendChild(buttonGroupElement);
    lineElement.appendChild(actionCell);
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

var BOOL;
let showClient1 = function (client) {
    BOOL=1;
    document.getElementById("list").hidden = true;
    document.getElementById("form").hidden = false;
    document.getElementById("code-input").value = client.id;
    document.getElementById("code-input").disabled = !!client.id;
    document.getElementById("nom-input").value = client.nom;
    document.getElementById("prenom-input").value = client.prenom;
    document.getElementById("pseudo-input").value =client.pseudo;
    document.getElementById("email-input").value = client.email;
    document.getElementById("solde-input").value = client.solde;
    document.getElementById("role-input").value = client.idRole;
    document.getElementById("modif-input").value = "1";


};

let showClient2 = function () {
    BOOL=0;
    document.getElementById("list").hidden = true;
    document.getElementById("form").hidden = false;
    document.getElementById("code-input").value = 1;
    document.getElementById("code-input").disabled = !!1;
    document.getElementById("nom-input").value = null;
    document.getElementById("prenom-input").value = null;
    document.getElementById("pseudo-input").value =null;
    document.getElementById("email-input").value = null;
    document.getElementById("solde-input").value = null;
    document.getElementById("role-input").value = "1";
    document.getElementById("modif-input").value = "1";
    document.getElementById("modif-input").disabled = !!"0";


};


let saveForm = function () {
    let client= {
        id: document.getElementById("code-input").value,
        nom: document.getElementById("nom-input").value,
        prenom: document.getElementById("prenom-input").value,
        email: document.getElementById("email-input").value,
        pseudo: document.getElementById("pseudo-input").value,
        solde: document.getElementById("solde-input").value,
        idRole: document.getElementById("role-input").value,
        modif:document.getElementById("modif-input").value
    };
    if (BOOL==0) {
        createClient(client);
    } else {
        updateClient(client);
    }
    return false;
};


// 3 - Window.Onload

window.onload = function () {
    document.getElementById("form").hidden = true;
    listAll();

    document.getElementById("apply-parameters").onclick = function () {
        listClient();
    }

    document.getElementById("add-button").onclick = function () {
        showClient2();
    };

    document.getElementById("go-back").onclick = function () {
        document.getElementById("form").hidden = true;
        document.getElementById("list").hidden = false;
    };

    document.getElementById("valid-form").onclick = function(){
        saveForm();
    }
};

