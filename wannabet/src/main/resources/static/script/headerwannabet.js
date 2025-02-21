let head = document.getElementById('headerwannabet');
head.innerHTML = '' +
    '<a href="home.html" class ="logo">Wannabet</a>\n' +
    '\n' +
    '    <nav>\n' +
    '        <ul class ="menu">\n' +
    '            <li><a href = "#">Sports</a>\n' +
    '                <ul class ="SubSports"></ul>\n' +
    '            </li>\n' +
    '            <li><a href="#">Esports</a>\n' +
    '                <ul class="SubEsports"></ul>\n' +
    '            </li>\n' +
    '        </ul>\n' +
    '    </nav>\n' +
    '    <div sec:authorized="isAnonymous()">\n' +
    '        <a href="login.html"><button class="headbutton">Login</button></a>\n' +
    '        <a href = "singup.html"><button class="headbutton">Registrati</button></a>\n' +
    '    </div>\n' +
    '    <div sec:authorized="isAuthenticated()">\n' +
    '        <a href="area_utente.html"><button class="headbutton">Area Utente</button></a>\n' +
    '    </div>'