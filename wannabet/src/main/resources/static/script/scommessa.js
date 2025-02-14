let eventi = [];
let listaEventi = document.getElementById("eventList");
let quote = [];

let statoScommessa = "VINTA";
document.getElementById("statoScommessa").innerText = document.getElementById("statoScommessa").innerText+ " " +statoScommessa;
let dataScommessa = new Date().getDate()+ "/"+ (new Date().getMonth()+1)+ "/"+ new Date().getFullYear();
document.getElementById("dataScommessa").innerText = document.getElementById("dataScommessa").innerText+ " " +dataScommessa;


loadEventAndBets();

for(let i = 0; i < eventi.length; i++){
    let innerButton = document.createElement("button");
    innerButton.innerText = eventi[i].nome;
    innerButton.innerHTML = "" +
        "<div class='eventInnerText1'>"+eventi[i].data+ " - "+ eventi[i].categoria+" - "+ eventi[i].descrizione+"</div>"+
        "<div class='eventInnerText2'>"+ eventi[i].nome+ "</div>"+
        "<div class='eventInnerText3'><button class='quoteButton'>"+quote[i].esito+"|"+quote[i].moltiplicatore+ "</div>";
    innerButton.setAttribute("class", "eventButton");
    listaEventi.appendChild(innerButton);
}

function loadEventAndBets(){
    let d = new Date();
    for ( let i  = 1; i < 7; i++){
        d.setFullYear(2020);
        d.setMonth((11%i)+1);
        d.setDate(i);
        console.log(d.getDate());
        eventi[i-1] = {
            "idEvento": i,
            "nome": "nome"+i,
            "data": d.getDate()+ "/"+ d.getMonth()+ "/"+ d.getFullYear(),
            "descrizione": "Parole per test "+i,
            "chiuso": false,
            "categoria": "Champions league"
        };
        quote[i-1] = {
            "esito": "esito"+i,
            "moltiplicatore": 2.6
        }

    }
}