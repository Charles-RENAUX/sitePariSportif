// fonction pour lister les paris
let listParams = {
    filter: undefined,
    double: undefined,
    sort: undefined
};

let listBet = function () {
    let betsRequest = new XMLHttpRequest();
     let url = "/bet" ;
     
     // utilisation d'un sort/flitre ou double
    if (listParams.sort) {
        url = "/sort=" + listParams.sort;
    }
    if (listParams.filter){
    	url="/filter="+bet.id+"&name="+bet.nom;
    }
    if (listParams.double){
    	url="/double";
    }
    
    betsRequest.open("GET", url, true);
    betsRequest.responseType = "json";

    betsRequest.onload = function () {
        let bets = this.response;
        refreshTable(bets);
    };

  betsRequest.send();
};
        
    let refreshTable = function (bets) {
    let tableElement = document.querySelector("#bet-list tbody");
    var newTableElement = tableElement.cloneNode(false);
    for (const bet of bets) {
        newTableElement.appendChild(buildTableLine(bet));
    }
    tableElement.parentNode.replaceChild(newTableElement, tableElement);
};

let buildTableLine = function (bet) {
    let lineElement = document.createElement("tr");
    lineElement.appendChild(createTableCell(bet.id));
    lineElement.appendChild(createTableCell(bet.market,true));
    lineElement.appendChild(createTableCell(bet.marketB, true));
    lineElement.appendChild(createTableCell(bet.ligue));
    lineElement.appendChild(createTableCell(bet.date_h));
	lineElement.appendChild(createTableCell(bet.teamH));
	lineElement.appendChild(createTableCell(bet.teamA));
	lineElement.appendChild(createTableCell(bet.odd1));
	lineElement.appendChild(createTableCell(bet.odd2));
	lineElement.appendChild(createTableCell(bet.odd3));
	lineElement.appendChild(createTableCell(bet.dateOdd));
}

let createTableCell = function (text, header = false) {
    let cellElement;
    if (header) {
        cellElement = document.createElement("th");
        cellElement.setAttribute("scope", "row");
    } else {
        cellElement = document.createElement("td");
    }
    cellElement.innerText = text;
    return cellElement;
};


// suppression d'un bet
let deleteBet = function (bet) {
        let deleteRequest = new XMLHttpRequest();
        deleteRequest.open("DELETE", "/bet/" + bet.id, true);

        deleteRequest.onload = function () {
            listBet();
        };

        deleteRequest.send();
    }
//

//modification&creation bet

let showBet = function (bet) {
    formMode = bet.id ? UPDATE : CREATION;

    document.getElementById("id-input").value = bet.id;
    document.getElementById("id-input").disabled = !!bet.id;
    document.getElementById("market-input").value = bet.market;
    document.getElementById("marketB-input").value = bet.marketB;
    document.getElementById("league-input").value = bet.league;
    document.getElementById("teamH-input").value = bet.teamH;
    document.getElementById("teamA-input").value = bet.teamA;
    document.getElementById("odd1-input").value = bet.odd1;
    document.getElementById("odd2-input").value = bet.odd2;
    document.getElementById("odd3-input").value = bet.odd3;
	document.getElementById("dateOdd-input").value = bet.dateOdd 
	
    document.getElementById("list").hidden = true;
    document.getElementById("form").hidden = false;
};

let saveForm = function () {
    let bet= {
        id: document.getElementById("id-input").value,
        market: document.getElementById("market-input").value,
        marketB: document.getElementById("marketB-input").value,
        league: document.getElementById("league-input").value,
        teamH: document.getElementById("teamH-input").value,
        teamA: document.getElementById("teamA-input").value,
        odd1: document.getElementById("odd1-input").value,
        odd2: document.getElementById("odd2-input").value,
        odd3: document.getElementById("odd3-input").value,
        dateOdd: document.getElementById("dateOdd-input").value
    };

    if (formMode === CREATION) {
        createBet(bet);
    } else {
        updateBet(bet);
    }


    return false;
};

let createBet = function (bet) {
    let saveRequest = new XMLHttpRequest();
    saveRequest.open("POST", "/bet", true); // a modif

    saveRequest.onload = function () {
        if (this.status === 201) {
            listBet();
            document.getElementById("form").hidden = true;
            document.getElementById("list").hidden = false;
        } else if (this.status === 204) {
            alert("The bet creation should return a CREATED response status!");
        } else if (this.status === 409) {
            alert("The bet code already exists!");
        }
    };
    saveRequest.setRequestHeader("content-type", "application/json");
    saveRequest.send(JSON.stringify(bet));
};

let updateBet = function (bet) {
    let saveRequest = new XMLHttpRequest();
    saveRequest.open("PATCH", "/bet/" + bet.id, true);

    saveRequest.onload = function () {
        if (this.status === 204) {
            listBet();
            document.getElementById("form").hidden = true;
            document.getElementById("list").hidden = false;
        } else if (this.status === 404) {
            alert("The updated bet does not exist!");
        }
    };
    saveRequest.setRequestHeader("content-type", "application/x-www-form-urlencoded");
    saveRequest.send("id=" + bet.id + "&market=" + bet.market + "&marketB=" + bet.marketB+ "&league=" + bet.league + "&odd1=" + bet.odd1 + "&odd2=" + bet.odd2 + "&odd3=" + bet.odd3);
};

window.onload = function () {
    document.getElementById("form").hidden = true;

    listBet();

    document.getElementById("list-parameters").onsubmit = applyParameters;

    document.getElementById("add-button").onclick = function () {
        showBet({id: "", nom: {}, prenom: "", pseudo: "",email:""});
    };

    document.getElementById("go-back").onclick = function () {
        document.getElementById("form").hidden = true;
        document.getElementById("list").hidden = false;
    };

    document.getElementById("save-form").onclick = saveForm;
};

