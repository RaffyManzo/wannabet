document.addEventListener('DOMContentLoaded', () => {
    // Toggle del popup dello scontrino: aggiunge o rimuove la classe 'active'
    const toggleBtn = document.getElementById('toggleScontrino');
    const popup = document.getElementById('scontrinoPopup');
    toggleBtn.addEventListener('click', () => {
        popup.classList.toggle('active');
    });

    // Al caricamento, aggiorna lo scontrino via AJAX
    updateScontrinoDOM();

    // Aggiorna il riepilogo quando l'importo viene modificato
    document.getElementById('importo').addEventListener('input', updateRiepilogo);

    // Listener per i pulsanti Prenota e Scommetti con chiamate AJAX ai rispettivi endpoint
    document.getElementById('btnPrenota').addEventListener('click', () => {
        const importo = document.getElementById('importo').value;
        fetch('/api/scontrino/prenota?importo=' + encodeURIComponent(importo), {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include'
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(`Errore HTTP: ${response.status} - ${text}`);
                    });
                }
                return response.text();
            })
            .then(codice => {
                alert("Prenotazione effettuata! Codice: " + codice);
                updateScontrinoDOM();
            })
            .catch(error => console.error('Errore nella prenotazione:', error));
    });


// Listener per i pulsanti "Carica" delle scommesse prenotate
    document.querySelectorAll('.btn-carica').forEach(btn => {
        btn.addEventListener('click', () => {
            const codicePrenotazione = btn.getAttribute('data-prenotazione-code');

            loadPrenotazione(codicePrenotazione)
        });
    });

    document.getElementById('btnScommetti').addEventListener('click', () => {
        const importo = document.getElementById('importo').value;
        fetch('/api/scontrino/conferma?importo=' + encodeURIComponent(importo), {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            credentials: 'include'
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(`Errore HTTP: ${response.status} - ${text}`);
                    });
                }
                return response.json();
            })
            .then(scommessa => {
                // Redirect alla pagina dei dettagli della scommessa
                window.location.href = '/scommessa/' + scommessa.id_scommessa;
            })
            .catch(error => {
                // Visualizza l'alert in caso di errore
                window.alert("Errore nella conferma scommessa: " + error.message);
                console.error('Errore nella conferma:', error);
            });
    });

});

document.addEventListener("DOMContentLoaded", function() {
    // Associa l'evento click a tutte le quote-cell presenti in pagina
    document.querySelectorAll('.quote-cell').forEach(function(cell) {
        cell.addEventListener('click', function() {
            const action = cell.getAttribute('data-action');
            const id = cell.getAttribute('data-id');
            if (action === 'remove') {
                rimuoviQuota(id);
            } else {
                aggiungiQuota(id);
            }
        });
    });
});

// Funzione per aggiornare lo stato (data-action e classe active) delle quote-cell
function updateQuoteCells(scontrino) {
    // Estrae gli id delle quote presenti nello scontrino; ricordiamo che nel JSON il campo è 'id_quota'
    const quoteIds = scontrino.quote.map(q => String(q.id_quota));

    // Itera su tutte le quote-cell generate da Thymeleaf
    document.querySelectorAll('.quote-cell').forEach(cell => {
        const id = cell.getAttribute('data-id');
        if (quoteIds.includes(id)) {
            cell.setAttribute('data-action', 'remove');
            cell.classList.add('active');
        } else {
            cell.setAttribute('data-action', 'add');
            cell.classList.remove('active');
        }
    });
}

// Calcola il totale dei moltiplicatori e la vincita potenziale
function updateRiepilogo() {
    const multiplierElements = document.querySelectorAll('.quota-moltiplicatore');
    let product = multiplierElements.length > 0 ? 1 : 0;
    multiplierElements.forEach(el => {
        const value = parseFloat(el.getAttribute('data-moltiplicatore'));
        if (!isNaN(value)) {
            product *= value;
        }
    });
    if (multiplierElements.length === 0) {
        product = 0;
    }
    document.getElementById('totaleQuota').innerText = product.toFixed(2);
    const importo = parseFloat(document.getElementById('importo').value) || 0;
    document.getElementById('vincitaPotenziale').innerText = (product * importo).toFixed(2);
}

// Funzione per aggiornare il DOM recuperando lo scontrino via AJAX GET
function updateScontrinoDOM() {
    fetch('/api/scontrino', {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Errore HTTP: ${response.status}`);
            }
            return response.json();
        })
        .then(scontrino => {
            // Aggiorna il badge con il numero di quote
            document.getElementById('scontrinoBadge').innerText = scontrino.quote.length;

            const container = document.getElementById('scontrino-content');
            container.innerHTML = ''; // svuota il contenitore

            // Sezioni classiche dello scontrino
            const importoSection = document.querySelector('.importo-scommessa');
            const riepilogoSection = document.querySelector('.riepilogo');
            const scontrinoButtons = document.querySelector('.scontrino-buttons');

            if (scontrino.quote.length === 0) {
                // Nascondi le sezioni classiche
                importoSection.style.display = 'none';
                riepilogoSection.style.display = 'none';
                scontrinoButtons.style.display = 'none';

                // Mostra la parte di inserimento codice
                container.innerHTML = `
                <div class="scontrino-empty">
                    <p>Scontrino vuoto</p>
                    <div class="code-input-container">
                        <label for="prenotazioneCode">Hai un codice?</label>
                        <div class="code-input-wrapper">
                            <input type="text" id="prenotazioneCode" placeholder="ABCDE" maxlength="5" />
                            <button id="btnCodicePrenotazione">
                                <i class="fa fa-arrow-right"></i>
                            </button>
                        </div>
                    </div>
                </div>
            `;

                document.getElementById("btnCodicePrenotazione").addEventListener("click", function(event) {
                    // Verifica se è stato cliccato il pulsante con la freccia
                    if (event.target && event.target.id === "btnCodicePrenotazione") {
                        const code = document.getElementById("prenotazioneCode").value.trim();
                        // Controllo minimo: 5 caratteri
                        if (code.length === 5) {
                            loadPrenotazione(code)
                        } else {
                            alert("Inserisci un codice di 5 caratteri.");
                        }
                    }
                });
            } else {
                // Mostra le sezioni classiche
                importoSection.style.display = 'block';
                riepilogoSection.style.display = 'block';
                scontrinoButtons.style.display = 'block';

                // Crea dinamicamente l'elenco delle quote
                scontrino.quote.forEach(quota => {
                    const divQuota = document.createElement('div');
                    divQuota.classList.add('evento-block');

                    // Pulsante per rimuovere la quota
                    const btnRemove = document.createElement('i');
                    btnRemove.classList.add('btn-rimuovi', 'fa', 'fa-trash');
                    btnRemove.onclick = () => rimuoviQuota(quota.id_quota);
                    divQuota.appendChild(btnRemove);

                    // Dati dell'evento
                    const divCategoria = document.createElement('div');
                    divCategoria.classList.add('evento-categoria');
                    divCategoria.textContent = quota.evento.categoria;
                    divQuota.appendChild(divCategoria);

                    const divDescrizione = document.createElement('div');
                    divDescrizione.classList.add('evento-descrizione');
                    divDescrizione.textContent = quota.evento.descrizione;
                    divQuota.appendChild(divDescrizione);

                    const divData = document.createElement('div');
                    divData.classList.add('evento-data');
                    divData.textContent = quota.evento.data;
                    divQuota.appendChild(divData);

                    const divNome = document.createElement('div');
                    divNome.classList.add('evento-nome');
                    divNome.textContent = quota.evento.nome;
                    divQuota.appendChild(divNome);

                    // Dati della quota
                    const divQuotaInfo = document.createElement('div');
                    divQuotaInfo.classList.add('quota-info');

                    const divQuotaCategoria = document.createElement('div');
                    divQuotaCategoria.classList.add('quota-categoria');
                    divQuotaCategoria.textContent = quota.categoria;
                    divQuotaInfo.appendChild(divQuotaCategoria);

                    const divQuotaEsito = document.createElement('div');
                    divQuotaEsito.classList.add('quota-esito');
                    divQuotaEsito.textContent = quota.esito;
                    divQuotaInfo.appendChild(divQuotaEsito);

                    const divQuotaMoltiplicatore = document.createElement('div');
                    divQuotaMoltiplicatore.classList.add('quota-moltiplicatore');
                    divQuotaMoltiplicatore.textContent = quota.moltiplicatore;
                    divQuotaMoltiplicatore.setAttribute('data-moltiplicatore', quota.moltiplicatore);
                    divQuotaInfo.appendChild(divQuotaMoltiplicatore);

                    divQuota.appendChild(divQuotaInfo);
                    container.appendChild(divQuota);
                });
            }

            // Dopo aver popolato la sezione, aggiorna lo stato delle quote-cell e il riepilogo
            updateQuoteCells(scontrino);
            updateRiepilogo();
        })
        .catch(error => console.error('Errore nella richiesta:', error));
}


// Funzioni per aggiungere e rimuovere una quota
function aggiungiQuota(idQuota) {
    fetch(`/api/scontrino/aggiungi/${idQuota}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include'
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(`Errore HTTP: ${response.status} - ${text}`); });
            }
            return response.json();
        })
        .then(() => updateScontrinoDOM())
        .catch(error => console.error('Errore nella richiesta:', error));
}

function rimuoviQuota(idQuota) {
    fetch(`/api/scontrino/rimuovi/${idQuota}`, {
        method: 'DELETE',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Errore HTTP! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(() => updateScontrinoDOM())
        .catch(error => console.error('Errore nella richiesta:', error));
}

// Gestione del toggle del popup dello scontrino (alternativa a classList)
document.addEventListener('DOMContentLoaded', () => {
    const popup = document.getElementById('scontrinoPopup');
    document.getElementById('toggleScontrino').addEventListener('click', () => {
        popup.style.display = (popup.style.display === 'none' || popup.style.display === '') ? 'block' : 'none';
    });

    // Aggiorna lo scontrino all'avvio
    updateScontrinoDOM();

    // Aggiorna il riepilogo quando l'importo viene modificato
    document.getElementById('importo').addEventListener('input', updateRiepilogo);

    // Eventuali listener per i pulsanti Prenota e Scommetti
    document.getElementById('btnPrenota').addEventListener('click', () => {
        // Implementa la chiamata AJAX per prenotare la scommessa
        // Esempio: fetch('/api/scontrino/prenota', { ... })
    });
    document.getElementById('btnScommetti').addEventListener('click', () => {
        // Implementa la chiamata AJAX per confermare la scommessa
        // Esempio: fetch('/api/scontrino/conferma', { ... })
    });
});

function loadPrenotazione(code) {
    // Chiama l'endpoint /api/prenotazioni/{codice}
    fetch(`/api/scontrino/svuota`, {
        method: 'GET',
        credentials: 'include'
    })
        .catch(err => {
            console.error('Errore:', err);
        })
        .then(date => {
            updateScontrinoDOM()
            updateQuoteCells()
            updateRiepilogo()
        });




    // Chiama l'endpoint /api/prenotazioni/{codice}
    fetch(`/api/prenotazioni/${code}`, {
        method: 'GET',
        credentials: 'include'
    })
        .then(response => {
            if (response.status === 404) {
                // Prenotazione inesistente: non fare nulla
                return [];
            } else if (!response.ok) {
                // Errore generico
                return response.text().then(text => {
                    throw new Error(`Errore HTTP: ${response.status} - ${text}`);
                });
            }
            // Prenotazione trovata: restituisce un array di Quote in JSON
            return response.json();
        })
        .then(quotes => {
            // Aggiunge ciascuna quota allo scontrino (una chiamata POST per ogni quota)
            quotes.forEach(q => aggiungiQuota(q.id_quota));
        })
        .catch(err => {
            console.error('Errore nella ricerca prenotazione:', err);
        });
}
