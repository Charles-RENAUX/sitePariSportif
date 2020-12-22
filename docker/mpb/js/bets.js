// Objet pour filtrer la liste
var BASEURL="http://localhost:8080/";

// 1 - Fonctions AJAX


//Lister tous les bets
let listAll = function() {
    let betsRequest = new XMLHttpRequest();
    let url = BASEURL+"api/bets" ;
    betsRequest.open("GET", url, true);
    betsRequest.responseType = "json";

    betsRequest.onload = function () {
        let bets = this.response;
        refreshTable(bets);
    };

    betsRequest.send();
};


//Lister avec ordre et/ou filtre
let listBet = function () {
    let sort=document.getElementById("sort").value;
    let filter=document.getElementById("filter").value;
    let filterName=document.getElementById("filter-name").value;
    if ((filterName.length==0||filter.length==0)&&sort.length==0) {
        listAll();
    } else if(sort.length>0 && filter.length>0&&filterName.length==0) {
        listAll()
    }else{
        let betsRequest = new XMLHttpRequest();
        let url = BASEURL+"api/bets/";
        if (sort .length==0 && filter.length>0) {
            url += "filter?name=" + filterName + "&filter=" + filter;
        } else if (sort.length>0 && filter.length==0) {
            url += "sort?sort=" + sort;
        } else {
            url += "pair?sort=" + sort + "&name=" + filterName + "&filter=" + filter;
        }
        console.log(url);
        betsRequest.open("GET", url, true);
        betsRequest.responseType = "json";
        betsRequest.onload = function () {
            let bets = this.response;
            refreshTable(bets);
        };
        betsRequest.send();
    }
};


// Suppression d'un bet
let deleteBet = function (bet) {
        let deleteRequest = new XMLHttpRequest();
        let url= BASEURL+"api/bets/"+bet.id;
        deleteRequest.open("DELETE", url, true);
        deleteRequest.onload = function () {
            if (this.status === 200) {
                listBet();
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

//Creation d'un bet


let createBet = function (bet) {
    let saveRequest = new XMLHttpRequest();
    let url=BASEURL+"api/bets/add";
    saveRequest.open("POST", url, true); // a modif

    saveRequest.onload = function () {
        if (this.status === 201) {
            listBet();
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
    saveRequest.send(JSON.stringify(bet));
};

//Update d'un bet

let updateBet = function (bet) {
    let saveRequest = new XMLHttpRequest();
    let url=BASEURL+"api/bets/edit";
    saveRequest.open("PATCH", url, true);

    saveRequest.onload = function () {
        if (this.status === 200) {
            listBet();
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
    saveRequest.send(JSON.stringify(bet));
};

// 2 - Functions JS


let refreshTable = function (bets) {
    let tableElement = document.querySelector("#bets-list tbody");
    var newTableElement = tableElement.cloneNode(false);
    for (const bet of bets) {
        newTableElement.appendChild(buildTableLine(bet));
    }
    tableElement.parentNode.replaceChild(newTableElement, tableElement);
};

let buildTableLine = function (bet) {
    let lineElement = document.createElement("tr");
    lineElement.appendChild(createTableCell(bet.id));
    lineElement.appendChild(createTableCell(bet.idLeague));
    lineElement.appendChild(createTableCell(bet.league));
    lineElement.appendChild(createTableCell(bet.dateMatch));
    lineElement.appendChild(createTableCell(bet.teamH));
    lineElement.appendChild(createTableCell(bet.teamA));
    lineElement.appendChild(createTableCell(bet.dateOdd));
    lineElement.appendChild(createTableCell(bet.market));
    lineElement.appendChild(createTableCell(bet.marketB));
    lineElement.appendChild(createTableCell(bet.odd1));
    lineElement.appendChild(createTableCell(bet.odd2));
    lineElement.appendChild(createTableCell(bet.odd3));

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
            showBet1(bet); // on appel la fonction showClient pour modifier la ligne
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
            deleteBet(bet);
        };
    lineElement.appendChild(deleteButton);
    actionCell.appendChild(buttonGroupElement);
    lineElement.appendChild(actionCell);

    return lineElement;

}

let createTableCell = function (text, header = false) {
    let cellElement = document.createElement("td");
    cellElement.innerText = text;
    return cellElement;
};

var BOOL;

let showBet1 = function (bet) {
    BOOL=1;
    document.getElementById("list").hidden = true;
    document.getElementById("form").hidden = false;
    document.getElementById("id-input").value = bet.id;
    document.getElementById("id-input").disabled = !!bet.id;
    document.getElementById("idLeague-input").value = bet.idLeague;
    document.getElementById("dateMatch-input").value = bet.dateMatch;
    document.getElementById("league-input").value = bet.league;
    document.getElementById("teamH-input").value = bet.teamH;
    document.getElementById("teamA-input").value = bet.teamA;
    document.getElementById("market-input").value = bet.market;
    document.getElementById("marketB-input").value = bet.marketB;
    document.getElementById("odd1-input").value = bet.odd1;
    document.getElementById("odd2-input").value = bet.odd2;
    document.getElementById("odd3-input").value = bet.odd3;
    document.getElementById("dateOdd-input").value = bet.dateOdd

};

let showBet2 = function (bet) {
    BOOL=0;
    document.getElementById("list").hidden = true;
    document.getElementById("form").hidden = false;
    document.getElementById("id-input").value = 1;
    document.getElementById("id-input").disabled = !!1;
    document.getElementById("idLeague-input").value = null;
    document.getElementById("dateMatch-input").value = null;
    document.getElementById("league-input").value = null;
    document.getElementById("teamH-input").value = null;
    document.getElementById("teamA-input").value = null;
    document.getElementById("market-input").value = null;
    document.getElementById("marketB-input").value = null;
    document.getElementById("odd1-input").value =null;
    document.getElementById("odd2-input").value = null;
    document.getElementById("odd3-input").value = null;
    document.getElementById("dateOdd-input").value =null;
};

let saveForm = function () {
    let bet= {
        id: document.getElementById("id-input").value,
        idLeague: document.getElementById("idLeague-input").value,
        league: document.getElementById("league-input").value,
        dateMatch: document.getElementById("dateMatch-input").value,
        teamH: document.getElementById("teamH-input").value,
        teamA: document.getElementById("teamA-input").value,
        market: document.getElementById("market-input").value,
        marketB: document.getElementById("marketB-input").value,
        odd1: document.getElementById("odd1-input").value,
        odd2: document.getElementById("odd2-input").value,
        odd3: document.getElementById("odd3-input").value,
        dateOdd: document.getElementById("dateOdd-input").value
    };

    if (BOOL === 0) {
        createBet(bet);
    } else {
        updateBet(bet);
    }


    return false;
};

// 3 - Window.onload

window.onload = function () {
    document.getElementById("form").hidden = true;

    listBet();

    document.getElementById("apply-parameters").onclick = function () {
        listBet();
    }

    document.getElementById("add-button").onclick = function () {
        showBet2({id: "", idLeague: "", League: "", DateMatch: "",Home:"",Away:"",DateOdd:"",Market:"",MarketB:"",Odd1:"",Odd2:"",Odd3:""},0);
    };

    document.getElementById("go-back").onclick = function () {
        document.getElementById("form").hidden = true;
        document.getElementById("list").hidden = false;
    };

    document.getElementById("save-form").onclick = function(){
    saveForm();
    }
};
