let logged = false;
let nav = document.getElementById('nav');
if(!logged){
    nav.innerHTML = '' +
        '    <ul class="sidebar">\n' +
        '        <li onclick=hideSidebar()><a href="#"><svg xmlns="http://www.w3.org/2000/svg" height="26" viewBox="0 96 960 960" width="26"><path d="m249 849-42-42 231-231-231-231 42-42 231 231 231-231 42 42-231 231 231 231-42 42-231-231-231 231Z"/></svg></a></li>\n' +
        '        <li><a href="#">Sports</a></li>\n' +
        '        <li><a href="#">Esports</a></li>\n' +
        '        <li><a href="#">Login</a></li>\n' +
        '        <li><a href="#">Register</a></li>\n' +
        '    </ul>\n' +
        '    <ul>\n' +
        '        <li><a href="#">Logo</a></li>\n' +
        '        <li><a href="#">Sports</a></li>\n' +
        '        <li><a href="#">Esports</a></li>\n' +
        '        <li><a href="login.html">Login</a></li>\n' +
        '        <li><a href="singup.html" class="nav__register">Register</a></li>\n' +
        '        <li class="menu-button" onclick=showSidebar()><a href="#"><svg xmlns="http://www.w3.org/2000/svg" height="26" viewBox="0 96 960 960" width="26"><path d="M120 816v-60h720v60H120Zm0-210v-60h720v60H120Zm0-210v-60h720v60H120Z"/></svg></a></li>\n' +
        '    </ul>\n';
}else{
    nav.innerHTML = '' +
        '    <ul class="sidebar">\n' +
        '        <li onclick=hideSidebar()><a href="#"><svg xmlns="http://www.w3.org/2000/svg" height="26" viewBox="0 96 960 960" width="26"><path d="m249 849-42-42 231-231-231-231 42-42 231 231 231-231 42 42-231 231 231 231-42 42-231-231-231 231Z"/></svg></a></li>\n' +
        '        <li><a href="#">Sports</a></li>\n' +
        '        <li><a href="#">Esports</a></li>\n' +
        '        <li><a href="#">Scontrino</a></li>\n' +
        '        <li><a href="#">AreaPersonale</a></li>\n' +
        '    </ul>\n' +
        '    <ul>\n' +
        '        <li><a href="#">Logo</a></li>\n' +
        '        <li><a href="#">Sports</a></li>\n' +
        '        <li><a href="#">Esports</a></li>\n' +
        '        <li><a href="login.html">Scontrino</a></li>\n' +
        '        <li><a href="singup.html" class="nav__register">Area Personale</a></li>\n' +
        '        <li class="menu-button" onclick=showSidebar()><a href="#"><svg xmlns="http://www.w3.org/2000/svg" height="26" viewBox="0 96 960 960" width="26"><path d="M120 816v-60h720v60H120Zm0-210v-60h720v60H120Zm0-210v-60h720v60H120Z"/></svg></a></li>\n' +
        '    </ul>\n'
}