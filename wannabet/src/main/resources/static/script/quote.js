// Funzione di debounce (facoltativa, utile per evitare troppi aggiornamenti durante la digitazione)
function debounce(func, wait) {
    let timeout;
    return function(...args) {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), wait);
    };
}

function filtraQuote() {
    const searchInput = document.getElementById('searchTeam');
    const minInput = document.getElementById('minQuota');
    const maxInput = document.getElementById('maxQuota');

    // Recupera e normalizza i valori dei filtri
    const searchText = (searchInput.value || "").toLowerCase().trim();
    const minQuota = parseFloat(minInput.value) || 0;
    const maxQuota = parseFloat(maxInput.value) || Infinity;

    // Itera su ogni evento (event-card)
    document.querySelectorAll('.event-card').forEach(card => {
        let eventVisible = false;

        // Controlla se il nome dell'evento contiene il testo di ricerca
        const eventNameElem = card.querySelector('.quote-event');
        const eventName = eventNameElem ? eventNameElem.textContent.toLowerCase() : "";
        const searchMatchesEvent = eventName.includes(searchText);

        // Itera su ciascuna cella di quota all'interno della card
        card.querySelectorAll('.quote-cell').forEach(cell => {
            // Legge il valore della quota (assicurandosi che sia un numero)
            const quotaVal = parseFloat(cell.getAttribute('data-quota')) || 0;
            // Verifica se il testo della cella contiene il testo di ricerca
            const cellText = cell.textContent.toLowerCase();

            if ((cellText.includes(searchText) || searchMatchesEvent) && quotaVal >= minQuota && quotaVal <= maxQuota) {
                cell.style.display = 'inline-block';
                eventVisible = true;
            } else {
                cell.style.display = 'none';
            }
        });

        // Mostra la card se almeno una quota soddisfa i criteri, altrimenti nascondila
        card.style.display = eventVisible ? 'flex' : 'none';
    });
}

// Funzione per gestire il cambio di categoria tramite la nav bar
function cambiaCategoria(element) {
    // Rimuove la classe 'active' da tutti gli elementi della navbar
    document.querySelectorAll('.quote-categories-nav li').forEach(li => li.classList.remove('active'));
    // Aggiunge la classe 'active' all'elemento cliccato
    element.classList.add('active');
    // Aggiorna l'URL impostando il parametro "categoria" in modo da ricaricare la pagina filtrata
    const categoria = element.innerText;
    const urlParams = new URLSearchParams(window.location.search);
    urlParams.set('categoria', categoria);
    window.location.search = urlParams.toString();
}

// Utilizza debounce per evitare aggiornamenti troppo frequenti (attiva se necessario)
const debouncedFiltraQuote = debounce(filtraQuote, 300);

document.addEventListener('DOMContentLoaded', function () {
    // Inizializza il filtro alla partenza
    // filtraQuote();

    // Aggiunge gli event listener agli input di filtro
    document.getElementById('searchTeam').addEventListener('input', debouncedFiltraQuote);
    document.getElementById('minQuota').addEventListener('input', debouncedFiltraQuote);
    document.getElementById('maxQuota').addEventListener('input', debouncedFiltraQuote);
});
