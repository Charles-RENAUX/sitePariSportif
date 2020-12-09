let currentDestinationId;

let listerDestinations = function () {
    let listerDestinationsRequest = new XMLHttpRequest();
    listerDestinationsRequest.open("GET", "api/destinations", true);
    listerDestinationsRequest.responseType = "json";
    listerDestinationsRequest.onload = function () {
        let destinationsList = this.response;
        createDestinationList(destinationsList);
    }
    listerDestinationsRequest.send();
}

let ajouterDestination = function() {
    let destinationName=document.getElementById("new-destination-input").value;
    if (destinationName!="") {
        let ajouterDestinationRequest = new XMLHttpRequest();
        ajouterDestinationRequest.open("POST", "api/destinations", true);

        ajouterDestinationRequest.onload = function () {
            listerDestinations();
        }
        ajouterDestinationRequest.setRequestHeader("content-type", "application/x-www-form-urlencoded");
        ajouterDestinationRequest.send("nom=" + destinationName);
    }
}

let listerVoyages = function(destinationId) {
    currentDestinationId = destinationId;
    let listerVoyagesRequest = new XMLHttpRequest();
    listerVoyagesRequest.open("GET", "api/destinations/"+destinationId+"/voyages", true);
    listerVoyagesRequest.responseType = "json";
    listerVoyagesRequest.onload = function () {
        enableVoyageCreation();
        let listVoyages=this.response;
        if (listVoyages.length!==0) {
            createVoyagesList(listVoyages);
            document.getElementById("voyages").hidden=false;
            document.getElementById("no-destination").hidden=true;
        }else{
            document.getElementById("no-destination").hidden=false;
            document.getElementById("voyages").hidden=true;
        }
        document.getElementById("voyage-form").hidden=true;
        document.getElementById("detail-voyage").hidden=true;
    }
    listerVoyagesRequest.send();



}

let deleteVoyage = function (voyageId) {
    if (confirm("Are you sure you want to delete " + voyageId + " ?")){
        let deleteRequest = new XMLHttpRequest();
        deleteRequest.open("DELETE", "api/destinations/0/voyages/" + voyageId, true);
        deleteRequest.onload = function () {
            listerVoyages(currentDestinationId)
        };

        deleteRequest.send();
    }
}

let saveVoyage = function (imageData) {
    let voyage = {
        lieu:document.getElementById("lieu-input").value,
        nbPersonnes:document.getElementById("nbPersonnes-input").value,
        imageData:imageData,
        activites:activites,
        description:document.getElementById("description-input").value,
        note:document.getElementById("note-input").value,
        destinationId:currentDestinationId
    }
    let saveRequest = new XMLHttpRequest();
    saveRequest.open("POST", "api/destinations/"+voyage.destinationId+"/voyages", true);
    saveRequest.onload = function () {
        listerVoyages(currentDestinationId)
    };
    saveRequest.setRequestHeader("content-type", "application/json");
    saveRequest.send(JSON.stringify(voyage));
}

let openDetail = function (voyageId) {
    let voyageDetailRequest = new XMLHttpRequest();
    voyageDetailRequest.open("GET", "api/destinations/0/voyages/"+voyageId, true);
    voyageDetailRequest.responseType = "json";
    voyageDetailRequest.onload = function () {
        let voyageDetail = this.response;
        updateAndShowDetails(voyageDetail)
    }
    voyageDetailRequest.send();
}


window.onload = function () {
    listerDestinations();
    disableVoyageCreation();

    document.getElementById("add-destination-button").onclick = function () {
        ajouterDestination();
    }
    document.getElementById("voyage-add-button").onclick = function () {
        showAddVoyageForm();
    }
    document.getElementById("cancel").onclick = function () {
        listerVoyages(currentDestinationId);
        return false;
    }
    document.getElementById("retour-button").onclick = function () {
        listerVoyages(currentDestinationId);
    }
    document.getElementById("add-activite-button").onclick = function () {
        addActivite();
    }

    document.getElementById("save-voyage").onclick = function() {
        creerVoyage();
        return false;
    }
}

let showAddVoyageForm = function () {
    document.getElementById("voyage-form").hidden = false;
    document.getElementById("detail-voyage").hidden = true;
    document.getElementById("voyages").hidden = true;
    document.getElementById("no-destination").hidden = true;
}

let updateAndShowDetails = function (voyageDetail) {
    document.getElementById("detail-voyage-titre").innerText = voyageDetail.lieu;
    document.getElementById("detail-voyage-image").src = "images/" + voyageDetail.imagePath;
    document.getElementById("nombre-personnes").innerText = voyageDetail.nbPersonnes;
    let activites = document.getElementById("activites");
    activites.innerText = "";
    for(const activite of voyageDetail.activites) {
        let li = document.createElement("li");
        li.innerHTML = activite;
        activites.appendChild(li);
    }
    document.getElementById("detail-voyage").hidden = false;
    document.getElementById("voyages").hidden = true;
    document.getElementById("no-destination").hidden = true;
}

////////////////////////////////////////////////
//    Fonctions pour génération du contenu     //
////////////////////////////////////////////////

let enableVoyageCreation = function() {
    switchVoyageCreation(false);
}

let disableVoyageCreation = function() {
    switchVoyageCreation(true);
}

let switchVoyageCreation= function(enable) {
    document.getElementById("voyage-add-button").disabled = enable;
}

let getImageFileAsBase64 = function (callback) {
    let uploadedFileList = document.getElementById("image-input").files;
    if (uploadedFileList.length === 0) {
        callback();
        return;
    }
    let uploadedImage = uploadedFileList[0];

    let reader = new FileReader();
    reader.onload = function () {
        callback(reader.result);
    };
    reader.readAsDataURL(uploadedImage);
};

let creerVoyage = function () {
    getImageFileAsBase64(function(b64Image) {
        saveVoyage(b64Image);
    });
}

let createVoyagesList = function (voyageList) {
    let voyagesSection = document.getElementById("voyages");
    let newVoyagesList = voyagesSection.cloneNode(false);
    for(const voyage of voyageList) {
        newVoyagesList.appendChild(createVoyageElement(voyage));
    }
    voyagesSection.parentNode.replaceChild(newVoyagesList, voyagesSection);
}

let createVoyageElement = function(voyage) {
    let voyageElement = document.createElement("article");
    voyageElement.classList.add("voyage");

    voyageElement.appendChild(generateVoyageTitle(voyage));
    voyageElement.appendChild(generateImage(voyage));
    voyageElement.appendChild(generateNoteElement(voyage));
    voyageElement.appendChild(generateDetailLink(voyage));
    voyageElement.appendChild(generateDeleteButton(voyage));
    return voyageElement;
}

let generateVoyageTitle = function(voyage) {
    let titre = document.createElement("h4");
    titre.innerHTML = voyage.lieu;
    return titre;
}

let generateImage = function (voyage) {
    let figure = document.createElement("figure");
    let img = document.createElement("img");
    img.src = "images/" + voyage.imagePath;
    let figCaption = document.createElement("figcaption");
    figCaption.innerHTML = voyage.description;
    figure.appendChild(img);
    figure.appendChild(figCaption);
    return figure;
}

let generateNoteElement = function (voyage) {
    let span = document.createElement("span");
    span.innerHTML = "Note : " + voyage.note;
    return span;
}

let generateDetailLink = function(voyage) {
    let link = document.createElement("a");
    link.href="#";
    link.id="voyage"+voyage.id;
    link.innerText = " detail";
    link.onclick = function () {
        openDetail(voyage.id);
    }
    return link;
}

let generateDeleteButton = function(voyage) {
    let deleteButton = document.createElement("button");
    deleteButton.type = "button";
    deleteButton.classList.add("close", "suppr");

    let deleteLink = document.createElement("a");
    deleteLink.href="#";
    deleteLink.title = "supprimer";
    deleteLink.innerText ="x";
    deleteLink.onclick = function() {
        deleteVoyage(voyage.id);
    }

    deleteButton.appendChild(deleteLink);
    return deleteButton;
}


let createDestinationList = function(destinationList) {
    let destinationElement = document.getElementById("liste-destination");
    let newDestinationList = destinationElement.cloneNode(false);
    for(const destination of destinationList) {
        newDestinationList.appendChild(createDestinationElement(destination));
    }
    destinationElement.parentNode.replaceChild(newDestinationList, destinationElement);
}

let createDestinationElement = function (destination) {
    let link = document.createElement("a");
    link.id = "destination" + destination.id;
    link.classList.add("list-group-item", "list-group-item-action");
    link.href = "#";
    link.onclick = function() {
        listerVoyages(destination.id);
    }
    link.innerHTML = destination.nom;
    return link;
}

let activites = [];
let addActivite = function () {
    let activite = document.getElementById("activite-input").value;
    activites.push(activite);
    let li = document.createElement("li");
    li.innerText = activite;
    document.getElementById("activite-form").appendChild(li);
    document.getElementById("activite-input").value = "";
}

let resetForm = function () {
    document.getElementById("activite-form").innerHTML = "";
    activites = [];
}