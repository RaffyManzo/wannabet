let categorie = ["Champions league", "Europa League", "Serie A", "Serie B", "Premier League"];
let listCategorie = document.getElementById("categorie");
let eventi = [];
let listEventi = document.getElementById("partiteRecenti");

setCategorie();

for ( let i  = 0; i < 6; i++){
    let d = new Date();
    d.setFullYear(2020);
    d.setMonth(11%i);
    d.setDate(i+1);
    eventi[i] = {
            "idEvento": i,
            "nome": "nome"+i,
            "data": d,
            "descrizione": "Parole per test "+i,
            "chiuso": false,
            "categoria": categorie[0]
        };
}

setEventi();

function ricercaEventi(){
    fetch("api/evento")
        .then(response => response)
        .then(data => {
            console.log(data);
        })
        .catch(error => console.error("Errore", error));
}

function setCategorie(){
    for (let i = 0; i < categorie.length; i++){
        let innerButton = document.createElement("button");
        innerButton.textContent = categorie[i];
        innerButton.style.gridColumn = (i%4)+1;
        innerButton.setAttribute("onclick", "ricercaEventi()");
        innerButton.setAttribute("class", "buttonCategory");
        listCategorie.appendChild(innerButton);
    }
}

function setEventi(){
    for(let i = 0; i < eventi.length; i++){
        let innerButton = document.createElement("button");
        console.log(eventi[i].name);
        innerButton.innerHTML = eventi[i].descrizione+": "+ eventi[i].nome;
        innerButton.style.gridColumn = (i%2)+1;
        innerButton.style.height = "100px";
        innerButton.setAttribute("class", "buttonCategory");
        listEventi.appendChild(innerButton);
    }
    eventi.sort((a, b) => {
        let x = new Date(a.data);
        let y = new Date(b.data);
        if(x.getTime() < y.getTime()){
            return -1;
        }else if(x.getTime() > y.getTime()){
            return 1;
        }
        return 0;
    });
}