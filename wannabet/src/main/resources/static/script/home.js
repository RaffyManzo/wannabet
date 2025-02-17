let logged = false;
let header = document.getElementById('header');
if(logged){
    header.innerHTML = '' +
        '<a href="#" class ="logo">Wannabet</a>' +
        '<nav>' +
        '<ul class ="menu">' +
        '<li><a href = "#">Sports</a>' +
        '<ul class ="SubSports">' +
        '</ul>' +
        '</li>' +
        '<li><a href="#">Esports</a>' +
        '<ul class="SubEsports">' +
        '</li>' +
        '</ul>' +
        '</ul>' +
        '</nav>' +
        '<a href="login.html"><button>Login</button></a>' +
        '<a href = "singup.html"><button>Registrati</button></ahref>'

}else{
    header.innerHTML = '' +
        '<a href="#" class ="logo">Wannabet</a>' +
        '<nav>' +
        '<ul class ="menu">' +
        '<li><a href = "#">Sports</a>' +
        '<ul class ="SubSports">' +
        '</ul>' +
        '</li>' +
        '<li><a href="#">Esports</a>' +
        '<ul class="SubEsports">' +
        '</li>' +
        '</ul>' +
        '</ul>' +
        '</nav>' +
        '<a href = "#"><button>Area Personale</button></a>'

}