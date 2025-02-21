function isAuthenticated() {
// Questa è una simulazione. Sostituisci con la tua logica di autenticazione.
    return !!sessionStorage.getItem('authenticated');
}

// Aggiorna i link di autenticazione in base allo stato di autenticazione
function updateAuthLinks() {
    const authLinks = document.getElementById('functions');
    authLinks.innerHTML = ''; // Svuota i link esistenti

    if (isAuthenticated()) {
// Se l'utente è autenticato, mostra i link "Logout" e "Area Utente"
        authLinks.innerHTML ='' +
            '<a href="/api/auth/logout">Logout</a>' +
        '<a href="/user/profile">Area Utente</a>'
        ;
    } else {
// Se l'utente non è autenticato, mostra i link "Login" e "Sign Up"
        authLinks.innerHTML ='' +
            '<a href="/login">Login</a>' +
        '<a href="/sign-up">Sign Up</a>'
        ;
    }
}

// Esegui l'aggiornamento dei link di autenticazione quando la pagina viene caricata
window.onload = updateAuthLinks;

// Simulazione per test: fai il login al caricamento della pagina (rimuovi per la versione reale)
sessionStorage.setItem('authenticated', 'true');