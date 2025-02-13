let categorie = [];
let quote = [
    {
        "moltiplicatore": 1.4,
        "esito": "1",
        "categoria": "Principali",
        "evento": 1,
        "stato": "",
        "chiusa": false
    },
    {
        "moltiplicatore": 4.3,
        "esito": "X",
        "categoria": "Principali",
        "evento": 1,
        "stato": "",
        "chiusa": false
    },
    {
        "moltiplicatore": 5.4,
        "esito": "2",
        "categoria": "Principali",
        "evento": 1,
        "stato": "",
        "chiusa": false
    },
    {
        "moltiplicatore": 1.1,
        "esito": "1X",
        "categoria": "Principali",
        "evento": 1,
        "stato": "",
        "chiusa": false
    },
    {
        "moltiplicatore": 2.2,
        "esito": "X2",
        "categoria": "Principali",
        "evento": 1,
        "stato": "",
        "chiusa": false
    },
    {
        "moltiplicatore": 1.6,
        "esito": "12",
        "categoria": "Principali",
        "evento": 1,
        "stato": "",
        "chiusa": false
    },
    {
        "moltiplicatore": 2,
        "esito": "UN",
        "categoria": "Principali",
        "evento": 1,
        "stato": "",
        "chiusa": false
    },
    {
        "moltiplicatore": 3.5,
        "esito": "OV",
        "categoria": "Principali",
        "evento": 1,
        "stato": "",
        "chiusa": false
    },
    {
        "moltiplicatore": 1.7,
        "esito": "G",
        "categoria": "Principali",
        "evento": 1,
        "stato": "",
        "chiusa": false
    },
    {
        "moltiplicatore": 2,
        "esito": "GG",
        "categoria": "Principali",
        "evento": 1,
        "stato": "",
        "chiusa": false
    },
];
let listCategorie = document.getElementById("categoryList");
let listQuote = document.getElementById("quoteList");

setCategorie();
setQuote();



function findCategories(){
    let j = 0;
    for(let i = 0; i < quote.length; i++){
        if(!categorie.includes(quote[i].categoria)){
            categorie[j] = quote[i].categoria;
            j++;
        }
    }
}

function setCategorie(){
    findCategories();
    for(let i = 0; i < categorie.length; i++){
        let innerButton = document.createElement("button");
        innerButton.textContent = quote[i].categoria;
        innerButton.style.gridColumn = (i%4)+1;
        innerButton.setAttribute("class", "buttonCategory");
        listCategorie.appendChild(innerButton);
    }
}

function setQuote(){
    for(let i = 0; i < quote.length; i++){
        let innerContainer = document.createElement("div");
        let quotaName = document.createElement("h3");
        let innerButton = document.createElement("button");
        quotaName.textContent = quote[i].esito;
        innerButton.textContent = quote[i].moltiplicatore;
        innerButton.setAttribute("class", "buttonBet");
        innerContainer.style.gridColumn = (i%5)+1;
        innerContainer.style.margin = "3px";
        innerContainer.appendChild(quotaName);
        innerContainer.appendChild(innerButton);
        listQuote.appendChild(innerContainer);
    }
}