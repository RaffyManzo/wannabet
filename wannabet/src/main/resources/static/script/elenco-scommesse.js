let scommesse = [];
let listaScommesse = document.getElementById("betList");

getScommesse();
setScommesse();

function getScommesse() {
    for(let i = 1; i < 7; i++){
        let d = new Date();
        d.setFullYear(2020);
        d.setMonth((11%i)+1);
        d.setDate(i+1);
        scommesse[i-1] = {
            "idScommesse": i,
            "account": "account1",
            "importo": 50.35,
            "vincita": 356.57,
            "data": d.getDate()+ "/"+ d.getMonth()+ "/"+ d.getFullYear(),
            "stato": "Stato prova"
        }
    }
}

function setScommesse() {
    for(let i = 0; i < scommesse.length; i++){
        let innerButton = document.createElement("button");
        innerButton.innerHTML = "" +
            "<div class='eventInnerText2'>" + scommesse[i].data + " - " + scommesse[i].vincita + "</div>";
        innerButton.setAttribute("class", "eventButton");
        listaScommesse.appendChild(innerButton);
    }
}