function mostraForm(tipo)
{
    const formProfissional = document.getElementById("form-profissional");
    const formEmpresa = document.getElementById("form-empresa");

    if (tipo === "profissional") {
        formProfissional.style.display = "block";
        formEmpresa.style.display = "none";
    } else {
        formProfissional.style.display = "none";
        formEmpresa.style.display = "block";
    }
}

function toggleEntrevistaFields(select) {
    var candidaturaId = select.id.split('-')[1];
    var fields = document.getElementById('entrevista-fields-' + candidaturaId);
    if (select.value === 'ENTREVISTA') {
        fields.style.display = 'block';
    } else {
        fields.style.display = 'none';
    }
}